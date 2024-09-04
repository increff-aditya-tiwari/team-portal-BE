package com.increff.teamer.flowApi;

import com.increff.teamer.api.*;
import com.increff.teamer.dao.*;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.*;
import com.increff.teamer.model.data.EventParticipantsData;
import com.increff.teamer.model.data.ParticipantsInfoData;
import com.increff.teamer.model.data.UserData;
import com.increff.teamer.model.form.*;
import com.increff.teamer.pojo.*;
import com.increff.teamer.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventFlowApi {

    @Autowired
    private TeamDao teamDao;
    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private EventParticipantDao eventParticipantDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RequestDetailDao requestDetailDao;
    @Autowired
    UserFlowApi userService;
    @Autowired
    NotificationFlowApi notificationFlowApi;

    @Autowired
    UserApi userApi;
    @Autowired
    EventApi eventApi;
    @Autowired
    TeamApi teamApi;
    @Autowired
    EventParticipantApi eventParticipantApi;
    @Autowired
    RequestDetailApi requestDetailApi;
    @Autowired
    TeamUserMapApi teamUserMapApi;
    @Autowired
    EventDao eventDao;
    @Autowired
    NotificationApi notificationApi;

    public EventPojo createEvent(EventPojo eventPojo) throws CommonApiException {
        eventApi.isValidEvent(eventPojo.getEventId());
        eventPojo.setEventOrganiser(userApi.getCurrentUser().getUserId());
        eventPojo = eventApi.saveEvent(eventPojo);
        List<Long> participantIds = new ArrayList<>();
        participantIds.add(eventPojo.getEventOrganiser());
        EventParticipantsForm eventParticipantsForm = new EventParticipantsForm(eventPojo.getEventId(),ParticipantType.INDIVIDUAL,participantIds);
        eventParticipantApi.addEventParticipant(eventParticipantsForm);
        return eventPojo;
    }

    public void inviteParticipants(EventParticipantsForm eventParticipantsForm) throws CommonApiException{
        EventPojo eventPojo = eventApi.isValidEvent(eventParticipantsForm.getEventId());
        if(!Objects.equals(eventPojo.getEventOrganiser(), userApi.getCurrentUser().getUserId())){
            throw new CommonApiException(HttpStatus.UNAUTHORIZED,"Not Authorised to make changes in " + eventPojo.getEventName() + " event");
        }
        if(eventParticipantsForm.getParticipantType() == ParticipantType.TEAM){
            for(Long teamId : eventParticipantsForm.getParticipantIds()){
                TeamPojo teamPojo = teamApi.isTeamValid(teamId);
                if(eventParticipantApi.checkParticipant(ParticipantType.TEAM,teamId,eventPojo.getEventId()) == null){
                    inviteTeamParticipants(teamPojo,eventPojo);
                }
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
                if(eventParticipantApi.validateEventRequest(requestDetailPojo) && requestDetailApi.validateRequestForRequestType(requestDetailPojo)){
                    requestDetailPojoList.add(requestDetailPojo);
                    usernameList.add(userPojo.getUsername());
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

    public void inviteTeamParticipants(TeamPojo teamPojo,EventPojo eventPojo) throws CommonApiException{
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
            if(eventParticipantApi.validateEventRequest(requestDetailPojo) && requestDetailApi.validateRequestForRequestType(requestDetailPojo)){
                requestDetailPojoList.add(requestDetailPojo);
                usernameList.add(userPojo.getUsername());
            }
        }
        if(!requestDetailPojoList.isEmpty()){
            requestDetailApi.saveAll(requestDetailPojoList);
            notificationApi.generateNotification(usernameList, NotificationConstant.INVITE, NotificationConstant.EVENT, eventPojo.getEventId());
        }
    }

    public void eventJoinRequest(Long eventId) throws CommonApiException {
        EventPojo eventPojo = eventApi.isValidEvent(eventId);
        RequestDetailPojo requestDetailPojo = new RequestDetailPojo(RequestType.EVENT
                ,eventPojo.getEventId()
                , RequestCategory.REQUEST
                ,eventPojo.getEventOrganiser()
                ,userApi.getCurrentUser().getUserId()
                , RequestStatus.PENDING);
        if(eventParticipantApi.validateEventRequest(requestDetailPojo) && requestDetailApi.validateRequestForRequestType(requestDetailPojo)){
            List<RequestDetailPojo> requestDetailPojoList = new ArrayList<>();
            requestDetailPojoList.add(requestDetailPojo);
            requestDetailApi.saveAll(requestDetailPojoList);
            List<String> usernameList = new ArrayList<>();
            usernameList.add(userApi.isValidUser(eventPojo.getEventOrganiser()).getUsername());
            notificationApi.generateNotification(usernameList, NotificationConstant.REQUEST, NotificationConstant.EVENT, eventPojo.getEventId());
        }else {
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Already Requested or Invited join this Event"+ " Or Already Joined the Event");
        }
    }


    public void updateJoinEventRequestInvite(UpdateRequestForm updateRequestForm) throws CommonApiException {
        eventApi.isValidEvent(updateRequestForm.getRequestId());
        RequestDetailPojo requestDetailPojo = requestDetailApi.isValidRequest(updateRequestForm.getRequestDetailId());
        List<RequestDetailPojo> requestDetailPojoList = new ArrayList<>();
        requestDetailPojoList.add(requestDetailPojo);
        requestDetailApi.saveAll(requestDetailPojoList);
        List<String > usernameList =new ArrayList<>();
        usernameList.add(userApi.isValidUser(requestDetailPojo.getRequestBy()).getUsername());
        NotificationConstant notificationConstant = null;

        if(updateRequestForm.getRequestStatus() == RequestStatus.ACCEPTED){
            List<Long> participantIds = new ArrayList<>();
            if(requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST){
                participantIds.add(requestDetailPojo.getRequestBy());
                notificationConstant = NotificationConstant.REQUEST_ACCEPTED;
            } else if (requestDetailPojo.getRequestCategory() == RequestCategory.INVITE) {
                participantIds.add(requestDetailPojo.getRequestFor());
                notificationConstant = NotificationConstant.INVITE_ACCEPTED;
            }
            EventParticipantsForm eventParticipantsForm = new EventParticipantsForm(updateRequestForm.getRequestId(),ParticipantType.INDIVIDUAL,participantIds);
            eventParticipantApi.addEventParticipant(eventParticipantsForm);
            notificationApi.generateNotification(usernameList, notificationConstant, NotificationConstant.EVENT,requestDetailPojo.getRequestId());

            // we have to implement the method to ad the team mapping also if any invited team member is accepted the invite.
        }else {
            if(requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST){
                notificationConstant = NotificationConstant.REQUEST_REJECTED;
            } else if (requestDetailPojo.getRequestCategory() == RequestCategory.INVITE) {
                notificationConstant = NotificationConstant.INVITE_REJECTED;
            }
            notificationApi.generateNotification(usernameList, notificationConstant, NotificationConstant.TEAM,requestDetailPojo.getRequestId());
        }
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
        return requestDetailApi.getAllOpenRequestsForEvent(requestId);
    }

    public List<EventPojo> getAllEvent() throws CommonApiException {
        UserData user = userApi.getCurrentUser();
        userApi.isValidUser(user.getUserId());
        return eventApi.findAll();
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

}
