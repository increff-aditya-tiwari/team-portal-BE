package com.increff.teamer.api;


import com.increff.teamer.dao.EventDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.pojo.EventPojo;
import com.increff.teamer.pojo.TeamPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventApi {

    @Autowired
    private EventDao eventDao;
    public List<EventPojo> findAll() throws CommonApiException{
        return eventDao.findAll();
    }

    public EventPojo saveEvent(EventPojo eventPojo) throws CommonApiException{
        validateCreateTeam(eventPojo);
        return eventDao.save(eventPojo);
    }
    public EventPojo isValidEvent(Long eventId) throws CommonApiException{
        EventPojo eventPojo = eventDao.findByEventId(eventId);
        if(eventPojo == null){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Event with Event Id "+ eventId + " is not Present");
        }
        return eventPojo;
    }

    private EventPojo validateCreateTeam(EventPojo eventPojo) throws CommonApiException{
        EventPojo existingEventByName = eventDao.findByEventName(eventPojo.getEventName());
        if(existingEventByName != null){
            throw new CommonApiException(HttpStatus.FOUND,"Event With this Name is Already Present");
        }
        return existingEventByName;
    }
}
