package com.increff.teamer.flowApi;

import com.increff.teamer.api.*;
import com.increff.teamer.dao.*;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.*;
import com.increff.teamer.model.data.*;
import com.increff.teamer.model.form.*;
import com.increff.teamer.pojo.*;
import com.increff.teamer.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventFlowApi {

    @Autowired
    private UserApi userApi;
    @Autowired
    private EventApi eventApi;
    @Autowired
    private TeamApi teamApi;
    @Autowired
    private EventParticipantApi eventParticipantApi;
    @Autowired
    private RequestDetailApi requestDetailApi;
    @Autowired
    private TeamUserMapApi teamUserMapApi;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private NotificationApi notificationApi;

    public EventPojo createEvent(EventPojo eventPojo) throws CommonApiException {
        eventPojo.setEventOrganiser(userApi.getCurrentUser().getUserId());
        eventPojo = eventApi.saveEvent(eventPojo);
        //Adding the current user who created the event into the event.
        EventParticipantsForm eventParticipantsForm = new EventParticipantsForm(eventPojo.getEventId(),ParticipantType.INDIVIDUAL,Collections.singletonList(eventPojo.getEventOrganiser()));
        eventParticipantApi.addEventParticipant(eventParticipantsForm);
        return eventPojo;
    }

    public void inviteParticipants(EventParticipantsForm eventParticipantsForm) throws CommonApiException{
        EventPojo eventPojo = eventApi.isValidEvent(eventParticipantsForm.getEventId());
        if(!Objects.equals(eventPojo.getEventOrganiser(), userApi.getCurrentUser().getUserId())){
            throw new CommonApiException(HttpStatus.UNAUTHORIZED,"Not Authorised to make changes in " + eventPojo.getEventName() + " event");
        }
        if(eventParticipantsForm.getParticipantType() == ParticipantType.TEAM){
            //Storing the no of members of the team we sent the invite
            Long totalInvitedMembersOfTeam = 0L;
            for(Long teamId : eventParticipantsForm.getParticipantIds()){
                TeamPojo teamPojo = teamApi.isTeamValid(teamId);
                if(eventParticipantApi.checkParticipant(ParticipantType.TEAM,teamId,eventPojo.getEventId()) == null){
                    Long memberInvited = inviteTeamParticipants(teamPojo,eventPojo);
                    totalInvitedMembersOfTeam = totalInvitedMembersOfTeam + memberInvited;
                }
            }
            if(Objects.equals(totalInvitedMembersOfTeam,0L)){
                throw new CommonApiException(HttpStatus.BAD_REQUEST,"All the Members of Team already Requested or Invited to join this Event"+ " Or Already Joined the Event");
            }
        }
        else {
            List<String> usernameList = new ArrayList<>();
            List<RequestDetailPojo> requestDetailPojoList = new ArrayList<>();
            for(Long userId : eventParticipantsForm.getParticipantIds()){
                UserPojo userPojo = userApi.isValidUser(userId);
                RequestDetailPojo requestDetailPojo = new RequestDetailPojo(RequestType.EVENT
                        ,eventPojo.getEventId()
                        ,RequestCategory.INVITE
                        ,userPojo.getUserId()
                        ,eventPojo.getEventOrganiser()
                        ,RequestStatus.PENDING);
                if(validateEventRequest(requestDetailPojo)){
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
                notificationApi.generateNotification(usernameList, NotificationConstant.INVITE, NotificationConstant.EVENT, eventPojo.getEventId());
            }else {
                throw new CommonApiException(HttpStatus.BAD_REQUEST,"Already Requested or Invited join this Event"+ " Or Already Joined the Event");
            }
        }
    }

    public Long inviteTeamParticipants(TeamPojo teamPojo,EventPojo eventPojo) throws CommonApiException{
        List<UserTeamMappingPojo> userTeamMappingPojoList = teamUserMapApi.findAllByTeamId(teamPojo.getTeamId());
        List<RequestDetailPojo> requestDetailPojoList = new ArrayList<>();
        List<String> usernameList = new ArrayList<>();
        for(UserTeamMappingPojo userTeamMappingPojo : userTeamMappingPojoList){
            UserPojo userPojo = userApi.isValidUser(userTeamMappingPojo.getUserId());
            RequestDetailPojo requestDetailPojo = new RequestDetailPojo(RequestType.EVENT
                    ,eventPojo.getEventId()
                    ,RequestCategory.INVITE
                    ,userPojo.getUserId()
                    ,eventPojo.getEventOrganiser()
                    ,RequestStatus.PENDING);
            if(validateEventRequest(requestDetailPojo)){
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
            notificationApi.generateNotification(usernameList, NotificationConstant.INVITE, NotificationConstant.EVENT, eventPojo.getEventId());
        }
        return (long) requestDetailPojoList.size();
    }

    public void eventJoinRequest(Long eventId) throws CommonApiException {
        EventPojo eventPojo = eventApi.isValidEvent(eventId);
        RequestDetailPojo requestDetailPojo = new RequestDetailPojo(RequestType.EVENT
                ,eventPojo.getEventId()
                , RequestCategory.REQUEST
                ,eventPojo.getEventOrganiser()
                ,userApi.getCurrentUser().getUserId()
                , RequestStatus.PENDING);
        if(validateEventRequest(requestDetailPojo)){
            RequestDetailPojo existingRequestDetailPojo = requestDetailApi.validateRequestToAddRequest(requestDetailPojo);
            if(existingRequestDetailPojo != null && existingRequestDetailPojo.getRequestStatus() == RequestStatus.PENDING){
                throw new CommonApiException(HttpStatus.BAD_REQUEST,"Already Requested or Invited to join this Event");
            }
            if(existingRequestDetailPojo != null){
                existingRequestDetailPojo.setRequestStatus(requestDetailPojo.getRequestStatus());
            }
            requestDetailApi.saveAll(Collections.singletonList(existingRequestDetailPojo == null ? requestDetailPojo : existingRequestDetailPojo ));
            notificationApi.generateNotification(
                    Collections.singletonList(userApi.isValidUser(eventPojo.getEventOrganiser()).getUsername()),
                    NotificationConstant.REQUEST,
                    NotificationConstant.EVENT,
                    eventPojo.getEventId()
            );
        }else {
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Already Joined the Event" + " or Already Requested or Invited to join this Event");
        }
    }

    private Boolean validateEventRequest(RequestDetailPojo requestDetailPojo) throws CommonApiException{
        return requestDetailApi.validateRequestForRequestType(requestDetailPojo)
                && eventParticipantApi.validateEventRequest(requestDetailPojo);
    }


    public List<RequestDetailPojo> updateJoinEventRequestInvite(UpdateRequestForm updateRequestForm) throws CommonApiException {
        RequestDetailPojo requestDetailPojo = validateTeamRequestUpdate(updateRequestForm);
        requestDetailPojo.setRequestStatus(updateRequestForm.getRequestStatus());
        requestDetailApi.saveAll(Collections.singletonList(requestDetailPojo));
        NotificationConstant notificationConstant = null;

        if(updateRequestForm.getRequestStatus() == RequestStatus.ACCEPTED){
            Long participantId = null;
            if(requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST){
                participantId = requestDetailPojo.getRequestBy();
                notificationConstant = NotificationConstant.REQUEST_ACCEPTED;
            } else if (requestDetailPojo.getRequestCategory() == RequestCategory.INVITE) {
                participantId = requestDetailPojo.getRequestFor();
                notificationConstant = NotificationConstant.INVITE_ACCEPTED;
            }
            EventParticipantsForm eventParticipantsForm = new EventParticipantsForm(updateRequestForm.getRequestId()
                    ,ParticipantType.INDIVIDUAL
                    ,Collections.singletonList(participantId));
            eventParticipantApi.addEventParticipant(eventParticipantsForm);

            // we have to implement the method to add the team mapping also if any invited team member is accepted the invite.
        }else {
            if(requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST){
                notificationConstant = NotificationConstant.REQUEST_REJECTED;
            } else if (requestDetailPojo.getRequestCategory() == RequestCategory.INVITE) {
                notificationConstant = NotificationConstant.INVITE_REJECTED;
            }
        }
        notificationApi.generateNotification(Collections
                .singletonList(userApi.isValidUser(requestDetailPojo.getRequestBy()).getUsername())
                , notificationConstant, NotificationConstant.EVENT,requestDetailPojo.getRequestId());
        return Collections.singletonList(requestDetailPojo);
    }

    private RequestDetailPojo validateTeamRequestUpdate(UpdateRequestForm updateRequestForm) throws CommonApiException{
        if(updateRequestForm.getRequestStatus() != RequestStatus.ACCEPTED && updateRequestForm.getRequestStatus() != RequestStatus.REJECTED){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Request Status is not valid");
        }
        EventPojo eventPojo = eventApi.isValidEvent(updateRequestForm.getRequestId());
        RequestDetailPojo requestDetailPojo = requestDetailApi.isValidRequest(updateRequestForm.getRequestDetailId());
        if(requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST){
            if(!Objects.equals(eventPojo.getEventOrganiser(), userApi.getCurrentUser().getUserId())
                    || !Objects.equals(eventPojo.getEventOrganiser(),requestDetailPojo.getRequestFor())
                    || !Objects.equals(eventPojo.getEventId(),requestDetailPojo.getRequestId())){
                throw new CommonApiException(HttpStatus.BAD_REQUEST,"Not Authorised to make changes in " + eventPojo.getEventName() + " Team");
            }
        }else if(requestDetailPojo.getRequestCategory() == RequestCategory.INVITE) {
            if(!Objects.equals(requestDetailPojo.getRequestFor(), userApi.getCurrentUser().getUserId())
                    || !Objects.equals(eventPojo.getEventId(),requestDetailPojo.getRequestId())
                    || !Objects.equals(eventPojo.getEventOrganiser(),requestDetailPojo.getRequestBy())){
                throw new CommonApiException(HttpStatus.BAD_REQUEST,"Not Authorised to make changes in " + eventPojo.getEventName() + " Team");
            }
        }
        return requestDetailPojo;
    }




//    public void joinEvent(EventParticipantOldForm eventParticipantOldForm) throws Exception{
//        EventPojo eventPojo = eventDao.findByEventId(eventParticipantOldForm.getEventId());
//        if(eventPojo == null){
//            throw new Exception("Can not able to find the event with event id = "+ eventParticipantOldForm.getEventId());
//        }
//        for(ParticipantsInfoData participantsInfoData : eventParticipantOldForm.getParticipantsInfoDataList()){
////            System.out.println(participantsInfoData.toString());
//            if(participantsInfoData.getParticipantType() == ParticipantType.TEAM){
//                TeamPojo teamPojo = teamDao.findByTeamId(participantsInfoData.getParticipantId());
//                if(teamPojo == null){
//                    throw new Exception("Team with team id "+ participantsInfoData.getParticipantId() +" not present");
//                }
//            }else {
//                UserPojo userPojo = userDao.findByUserId(participantsInfoData.getParticipantId());
//                if(userPojo == null){
//                    throw new Exception("User with user id "+ participantsInfoData.getParticipantId() +" not present");
//                }
//            }
////        EventParticipantPojo eventParticipantPojo = convertUtil.convertDataToPojo(eventParticipantOldForm, EventParticipantPojo.class);
//            EventParticipantPojo eventParticipantPojo = new EventParticipantPojo(participantsInfoData.getParticipantId(), eventParticipantOldForm.getEventId(), participantsInfoData.getParticipantType());
//            try {
//                eventParticipantDao.save(eventParticipantPojo);
//            }catch (Exception e){
//                throw new Exception(e.getMessage());
//            }
//        }
//    }





    public List<RequestDetailPojo> getAllOpenRequestsForEvent(Long requestId) throws CommonApiException{
        eventApi.isValidEvent(requestId);
        return requestDetailApi.getAllOpenRequestsForEvent(requestId);
    }

    public List<RequestDetailPojo> getAllOpenInvitesFromEvent(Long requestId) throws CommonApiException{
        EventPojo eventPojo = eventApi.isValidEvent(requestId);
        return requestDetailApi.getAllOpenInvitesForEvent(requestId,userApi.getCurrentUser().getUserId(),eventPojo.getEventOrganiser());
    }

    public List<EventData> getAllEvent() throws CommonApiException {
        Long currentUser = userApi.getCurrentUser().getUserId();
        List<EventPojo> eventPojoList = eventApi.findAll();
        List<EventData> eventDataList = new ArrayList<>();
        for(EventPojo eventPojo: eventPojoList){
            eventDataList.add(new EventData(eventPojo,
                    getAllOpenRequestsForEvent(eventPojo.getEventId()),
                    (!Objects.equals(eventPojo.getEventOrganiser(), currentUser)) ?
                            getAllOpenInvitesFromEvent(eventPojo.getEventId()):
                            new ArrayList<>()));
        }
        return eventDataList;
    }





    public List<EventParticipantsData> getAllEventParticipants(Long eventId) throws CommonApiException{
        eventApi.isValidEvent(eventId);
        List<EventParticipantPojo> eventParticipantPojoList = eventParticipantApi.getAllEventParticipantsByEventId(eventId);
        List<EventParticipantsData> eventParticipantsDataList = new ArrayList<>();
        for(EventParticipantPojo eventParticipantPojo : eventParticipantPojoList){
            if(eventParticipantPojo.getParticipantType() == ParticipantType.TEAM){
                TeamPojo teamPojo = teamApi.isTeamValid(eventParticipantPojo.getParticipantId());
                eventParticipantsDataList.add(new EventParticipantsData(teamPojo.getTeamId(),ParticipantType.TEAM,teamPojo.getTeamName(),teamPojo.getCreatedBy()));
            }else {
                UserPojo userPojo = userApi.isValidUser(eventParticipantPojo.getParticipantId());
                eventParticipantsDataList.add(new EventParticipantsData(userPojo.getUserId(),ParticipantType.INDIVIDUAL,userPojo.getUsername()));
            }
        }
        return eventParticipantsDataList;
    }

    public void removeEventParticipant(DeleteEventParticipantFrom deleteEventParticipantFrom) throws CommonApiException{
        EventPojo eventPojo = eventApi.isValidEvent(deleteEventParticipantFrom.getEventId());
        if(!Objects.equals(eventPojo.getEventOrganiser(), userApi.getCurrentUser().getUserId())){
            throw new CommonApiException(HttpStatus.UNAUTHORIZED,"Not Authorised to change "+ eventPojo.getEventName() +" Event details");
        }
        EventParticipantPojo eventParticipantPojo = eventParticipantApi.checkParticipant(deleteEventParticipantFrom.getParticipantType(),deleteEventParticipantFrom.getParticipantId(),deleteEventParticipantFrom.getEventId());
        if(eventParticipantPojo != null){
            eventParticipantApi.removeEventParticipant(eventParticipantPojo);
        }
    }

    public List<Long> getEventParticipants(Long participantId) throws CommonApiException {
        return eventParticipantApi.getEventsByParticipant(participantId, ParticipantType.INDIVIDUAL)
                .stream()
                .map(EventParticipantPojo::getEventId)
                .collect(Collectors.toList());
    }

    public EventPojo getEventByEventId(Long eventId) throws CommonApiException{
        return eventApi.isValidEvent(eventId);
    }

}
