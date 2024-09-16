package com.increff.teamer.flowApi;

import com.increff.teamer.api.*;
import com.increff.teamer.dao.*;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.NotificationConstant;
import com.increff.teamer.model.constant.RequestCategory;
import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.model.constant.RequestType;
import com.increff.teamer.model.data.TeamData;
import com.increff.teamer.model.data.UserUiData;
import com.increff.teamer.model.form.*;
import com.increff.teamer.pojo.*;
import com.increff.teamer.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TeamFlowApi {
    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private UserApi userApi;
    @Autowired
    private TeamApi teamApi;
    @Autowired
    private TeamUserMapApi teamUserMapApi;
    @Autowired
    private RequestDetailApi requestDetailApi;
    @Autowired
    private NotificationApi notificationApi;


//    private ConcurrentHashMap<RequestStatus,List<RequestStatus>> requestStatusValidation = new ConcurrentHashMap<>();


    public TeamPojo createTeam(TeamPojo teamPojo) throws CommonApiException {
        teamPojo.setCreatedBy(userApi.getCurrentUser().getUserId());
        teamPojo = teamApi.saveTeam(teamPojo);
        //Adding the current User Who created the team to the team
        TeamUserMapForm teamUserMapForm = new TeamUserMapForm(teamPojo.getTeamId(),Collections.singletonList(userApi.getCurrentUser().getUserId()));
        teamUserMapApi.addTeamMember(teamUserMapForm);
        return teamPojo;
    }


    public void teamJoinRequest(Long teamId) throws CommonApiException {
        TeamPojo teamPojo = teamApi.isTeamValid(teamId);
        RequestDetailPojo requestDetailPojo = new RequestDetailPojo(RequestType.TEAM
                ,teamPojo.getTeamId()
                ,RequestCategory.REQUEST
                ,teamPojo.getCreatedBy()
                ,userApi.getCurrentUser().getUserId()
                ,RequestStatus.PENDING);
        //Checking for existing request/invite for that user and already added in the team
        if(validateTeamRequest(requestDetailPojo)){
            //Here handling the case when someone get the request to join the team and then if he leaved/removed from the team then again he can make request
            //because if we add new request then it will be error because we have already on request for that mapping
            // so in that case we just reopen the old request with new status.
            RequestDetailPojo existingRequestPojo =  requestDetailApi.validateRequestToAddRequest(requestDetailPojo);
            if(existingRequestPojo != null && existingRequestPojo.getRequestStatus() == RequestStatus.PENDING){
                throw new CommonApiException(HttpStatus.BAD_REQUEST,"Already Requested or Invited to join this team");
            }
            if(existingRequestPojo != null){
                existingRequestPojo.setRequestStatus(requestDetailPojo.getRequestStatus());
            }
            requestDetailApi.saveAll(Collections.singletonList(existingRequestPojo == null ? requestDetailPojo : existingRequestPojo));
            //Generating the notification for the team owner
            notificationApi.generateNotification(
                    Collections.singletonList(userApi.isValidUser(teamPojo.getCreatedBy()).getUsername()),
                    NotificationConstant.REQUEST,
                    NotificationConstant.TEAM,
                    teamPojo.getTeamId()
            );
        }else {
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Already Joined the Team" + " or Already Requested or Invited to join this Team");
        }
    }
    public void teamJoinInvite(TeamUserMapForm teamUserMapForm) throws CommonApiException {
        TeamPojo teamPojo = teamApi.isTeamValid(teamUserMapForm.getTeamId());
        if(!Objects.equals(teamPojo.getCreatedBy(), userApi.getCurrentUser().getUserId())){
            throw new CommonApiException(HttpStatus.UNAUTHORIZED,"Not Authorised to make changes in " + teamPojo.getTeamName() + " Team");
        }
        List<RequestDetailPojo> requestDetailPojoList = new ArrayList<>();
        List<String> usernameList = new ArrayList<>();
        for(Long userId : teamUserMapForm.getUserIds()){
            UserPojo userPojo = userApi.isValidUser(userId);
            RequestDetailPojo requestDetailPojo = new RequestDetailPojo(RequestType.TEAM
                    ,teamPojo.getTeamId()
                    ,RequestCategory.INVITE
                    ,userPojo.getUserId()
                    ,teamPojo.getCreatedBy()
                    ,RequestStatus.PENDING);
            //Checking for existing request/invite for that user and already added in the team
            if(validateTeamRequest(requestDetailPojo)){
                //Refer the teamJoinRequest comment for that
                RequestDetailPojo existingRequestDetailPojo = requestDetailApi.validateRequestToAddRequest(requestDetailPojo);
                if(existingRequestDetailPojo == null){
                    requestDetailPojoList.add(requestDetailPojo);
                    usernameList.add(userPojo.getUsername());
                }else if(existingRequestDetailPojo.getRequestStatus() != RequestStatus.PENDING) {
                    existingRequestDetailPojo.setRequestStatus(requestDetailPojo.getRequestStatus());
                    requestDetailPojoList.add(existingRequestDetailPojo);
                    usernameList.add(userPojo.getUsername());
                }
            }
        }
        if(!requestDetailPojoList.isEmpty()){
            requestDetailApi.saveAll(requestDetailPojoList);
            //Generating the notification for all the users who got invited in the team
            notificationApi.generateNotification(usernameList, NotificationConstant.INVITE, NotificationConstant.TEAM, teamPojo.getTeamId());
        }else {
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Already Requested or Invited to join this Team"+ " Or Already Joined the team");
        }
    }


    public void updateTeamJoinRequestInvite(UpdateRequestForm updateRequestForm) throws CommonApiException {
        RequestDetailPojo requestDetailPojo = validateTeamRequestUpdate(updateRequestForm);
        requestDetailPojo.setRequestStatus(updateRequestForm.getRequestStatus());
        requestDetailApi.saveAll(Collections.singletonList(requestDetailPojo));
        NotificationConstant notificationConstant = null;
        if(updateRequestForm.getRequestStatus() == RequestStatus.ACCEPTED){
            Long userId = null;
            //Generating the notification data.
            if(requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST){
                userId = requestDetailPojo.getRequestBy();
                notificationConstant = NotificationConstant.REQUEST_ACCEPTED;
            } else if (requestDetailPojo.getRequestCategory() == RequestCategory.INVITE) {
                userId = requestDetailPojo.getRequestFor();
                notificationConstant = NotificationConstant.INVITE_ACCEPTED;
            }
            // Adding the team member in the team
            TeamUserMapForm teamUserMapForm = new TeamUserMapForm(updateRequestForm.getRequestId()
                    ,Collections.singletonList(userId));
            teamUserMapApi.addTeamMember(teamUserMapForm);
        }else {
            if(requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST){
                notificationConstant = NotificationConstant.REQUEST_REJECTED;
            } else if (requestDetailPojo.getRequestCategory() == RequestCategory.INVITE) {
                notificationConstant = NotificationConstant.INVITE_REJECTED;
            }
        }
        notificationApi.generateNotification(Collections
                        .singletonList(userApi.isValidUser(requestDetailPojo.getRequestBy()).getUsername())
                ,notificationConstant, NotificationConstant.TEAM,requestDetailPojo.getRequestId());
    }

    private Boolean validateTeamRequest(RequestDetailPojo requestDetailPojo) throws CommonApiException{
        return requestDetailApi.validateRequestForRequestType(requestDetailPojo)
                && teamUserMapApi.validateRequest(requestDetailPojo);
    }

    private RequestDetailPojo validateTeamRequestUpdate(UpdateRequestForm updateRequestForm) throws CommonApiException{
        if(updateRequestForm.getRequestStatus() != RequestStatus.ACCEPTED && updateRequestForm.getRequestStatus() != RequestStatus.REJECTED){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Request Status is not valid");
        }
        TeamPojo teamPojo = teamApi.isTeamValid(updateRequestForm.getRequestId());
        RequestDetailPojo requestDetailPojo = requestDetailApi.isValidRequest(updateRequestForm.getRequestDetailId());
        if(requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST){
            if(!Objects.equals(teamPojo.getCreatedBy(), userApi.getCurrentUser().getUserId())
                    || !Objects.equals(teamPojo.getCreatedBy(),requestDetailPojo.getRequestFor())
                    || !Objects.equals(teamPojo.getTeamId(),requestDetailPojo.getRequestId())){
                throw new CommonApiException(HttpStatus.BAD_REQUEST,"Not Authorised to make changes in " + teamPojo.getTeamName() + " Team");
            }
        }else if(requestDetailPojo.getRequestCategory() == RequestCategory.INVITE) {
            if(!Objects.equals(requestDetailPojo.getRequestFor(), userApi.getCurrentUser().getUserId())
                    || !Objects.equals(teamPojo.getTeamId(),requestDetailPojo.getRequestId())
                    || !Objects.equals(teamPojo.getCreatedBy(),requestDetailPojo.getRequestBy())){
                throw new CommonApiException(HttpStatus.BAD_REQUEST,"Not Authorised to make changes in " + teamPojo.getTeamName() + " Team");
            }
        }
        return requestDetailPojo;
    }

    public List<RequestDetailPojo> getAllOpenRequestsForTeam(Long requestId) throws CommonApiException{
        teamApi.isTeamValid(requestId);
        return requestDetailApi.getAllOpenRequestsForTeam(requestId);
    }


    public List<RequestDetailPojo> getAllOpenInvitesForTeam(Long requestId) throws CommonApiException{
        TeamPojo teamPojo = teamApi.isTeamValid(requestId);
        return requestDetailApi.getAllOpenInvitesForTeam(requestId,userApi.getCurrentUser().getUserId(),teamPojo.getCreatedBy());
//        return requestDetailDao.findAllByRequestIdAndRequestTypeAndRequestStatusAndRequestCategory(requestId,RequestType.TEAM,RequestStatus.PENDING,RequestCategory.INVITE);
    }


    public List<UserUiData> getAllTeamMembers(Long teamId) throws CommonApiException{
        teamApi.isTeamValid(teamId);
        List<UserTeamMappingPojo> userTeamMappingPojoList = teamUserMapApi.findAllByTeamId(teamId);
        List<UserUiData> userUiDataList = new ArrayList<>();
        for(UserTeamMappingPojo userTeamMappingPojo : userTeamMappingPojoList){
            UserPojo localUser = userApi.isValidUser(userTeamMappingPojo.getUserId());
            userUiDataList.add(convertUtil.convertPojoToData(localUser, UserUiData.class));
        }
        return userUiDataList;
    }

    public void removeTeamMember(DeleteTeamMemberForm deleteTeamMemberForm) throws CommonApiException{
//        System.out.println("this is team ID "+deleteTeamMemberForm.getTeamId()+"this is userId"+deleteTeamMemberForm.getUserId());
        TeamPojo teamPojo = teamApi.isTeamValid(deleteTeamMemberForm.getTeamId());
        if(!Objects.equals(teamPojo.getCreatedBy(), userApi.getCurrentUser().getUserId())){
            throw new CommonApiException(HttpStatus.UNAUTHORIZED,"Not Authorised to change "+ teamPojo.getTeamName() +" team details");
        }
        UserTeamMappingPojo userTeamMappingPojo = teamUserMapApi.findByUserIdAndTeamId(deleteTeamMemberForm.getUserId(),deleteTeamMemberForm.getTeamId());
        if(userTeamMappingPojo != null){
            teamUserMapApi.delete(userTeamMappingPojo);
        }
    }
    public List<TeamData> getAllTeamData() throws CommonApiException{
        Long currentUser = userApi.getCurrentUser().getUserId();
        List<TeamPojo> teamPojoList = teamApi.getAllTeam();
        List<TeamData> teamDataList = new ArrayList<>();
        for(TeamPojo teamPojo: teamPojoList){
            teamDataList.add(new TeamData(teamPojo,
                    getAllOpenRequestsForTeam(teamPojo.getTeamId()),
                    (!Objects.equals(teamPojo.getCreatedBy(), currentUser)) ?
                            getAllOpenInvitesForTeam(teamPojo.getTeamId()):
                            new ArrayList<>()));
        }
        return teamDataList;
    }

    public List<Long> getTeamByUserId(Long userId) throws CommonApiException{
        userApi.isValidUser(userId);
        List<UserTeamMappingPojo> userTeamMappingPojoList = null;
        userTeamMappingPojoList = teamUserMapApi.findAllByUserId(userId);
        List<Long> teamIdList = new ArrayList<>();
        for(UserTeamMappingPojo userTeamMappingPojo : userTeamMappingPojoList){
            teamIdList.add(userTeamMappingPojo.getTeamId());
        }
        return teamIdList;
    }

    public TeamPojo getTeamByTeamId(Long teamId) throws CommonApiException{
        return teamApi.isTeamValid(teamId);
    }
}
