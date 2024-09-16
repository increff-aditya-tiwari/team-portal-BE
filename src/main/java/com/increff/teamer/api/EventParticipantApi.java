package com.increff.teamer.api;

import com.increff.teamer.dao.EventParticipantDao;
import com.increff.teamer.dao.RequestDetailDao;
import com.increff.teamer.dao.TeamDao;
import com.increff.teamer.dao.UserDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.ParticipantType;
import com.increff.teamer.model.constant.RequestCategory;
import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.model.constant.RequestType;
import com.increff.teamer.model.form.EventParticipantsForm;
import com.increff.teamer.model.form.TeamUserMapForm;
import com.increff.teamer.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventParticipantApi {
    @Autowired
    private EventParticipantDao eventParticipantDao;
    @Autowired
    private RequestDetailDao requestDetailDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TeamDao teamDao;

    public Boolean validateEventRequest(RequestDetailPojo requestDetailPojo) throws CommonApiException{
        EventParticipantPojo eventParticipantPojo = null;
        if(requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST){
            eventParticipantPojo = eventParticipantDao.findByParticipantIdAndParticipantTypeAndEventId(requestDetailPojo.getRequestBy(),ParticipantType.INDIVIDUAL,requestDetailPojo.getRequestId());
        } else if (requestDetailPojo.getRequestCategory() == RequestCategory.INVITE) {
            eventParticipantPojo = eventParticipantDao.findByParticipantIdAndParticipantTypeAndEventId(requestDetailPojo.getRequestFor(),ParticipantType.INDIVIDUAL,requestDetailPojo.getRequestId());
        }
        return eventParticipantPojo == null;
    }

    public void addEventParticipant(EventParticipantsForm eventParticipantsForm) throws CommonApiException {
        List<EventParticipantPojo> eventParticipantPojoList = validateAddEventParticipant(eventParticipantsForm);
        eventParticipantDao.saveAll(eventParticipantPojoList);
    }

    public EventParticipantPojo checkParticipant(ParticipantType participantType,Long participantId,Long eventId) throws CommonApiException{
        return  eventParticipantDao.findByEventIdAndParticipantIdAndParticipantType(eventId,participantId,participantType);
    }

    public void removeEventParticipant(EventParticipantPojo eventParticipantPojo) throws CommonApiException{
        eventParticipantDao.delete(eventParticipantPojo);
    }

    public List<EventParticipantPojo> getEventsByParticipant(Long participantId,ParticipantType participantType) throws CommonApiException{
        return eventParticipantDao.findByParticipantIdAndParticipantType(participantId,participantType);
    }

    public List<EventParticipantPojo> getAllEventParticipantsByEventId(Long eventId) throws CommonApiException{
        return eventParticipantDao.findAllByEventId(eventId);
    }

    private List<EventParticipantPojo> validateAddEventParticipant(EventParticipantsForm eventParticipantsForm) throws CommonApiException{
        List<EventParticipantPojo> eventParticipantPojoList = new ArrayList<>();
        for(Long participantId : eventParticipantsForm.getParticipantIds()){
            if(eventParticipantsForm.getParticipantType() == ParticipantType.INDIVIDUAL){
                UserPojo userPojo = userDao.findByUserId(participantId);
                if(userPojo == null) {
                    throw new CommonApiException(HttpStatus.BAD_REQUEST,"Some users are Invalid");
                }
            }else {
                TeamPojo teamPojo = teamDao.findByTeamId(participantId);
                if(teamPojo == null) {
                    throw new CommonApiException(HttpStatus.BAD_REQUEST,"Some teams are Invalid");
                }
            }
            EventParticipantPojo eventParticipantPojo = eventParticipantDao.findByEventIdAndParticipantIdAndParticipantType(eventParticipantsForm.getEventId(),participantId,eventParticipantsForm.getParticipantType());
            if(eventParticipantPojo == null){
                eventParticipantPojo = new EventParticipantPojo(participantId,eventParticipantsForm.getEventId(),eventParticipantsForm.getParticipantType());
                eventParticipantPojoList.add(eventParticipantPojo);
            }
        }
        return eventParticipantPojoList;
    }
}
