package com.increff.teamer.dto;

import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.flowApi.EventFlowApi;
import com.increff.teamer.model.data.EventParticipantsData;
import com.increff.teamer.model.form.CreateEventFrom;
import com.increff.teamer.model.form.UpdateRequestForm;
import com.increff.teamer.pojo.EventPojo;
import com.increff.teamer.pojo.RequestDetailPojo;
import com.increff.teamer.util.ConvertUtil;
import com.increff.teamer.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventDto {

    @Autowired
    private EventFlowApi eventFlowApi;
    @Autowired
    private ValidationUtil validationUtil;
    @Autowired
    private ConvertUtil convertUtil;


    public EventPojo createEvent(CreateEventFrom createEventFrom) throws CommonApiException{
        validationUtil.validateForm(createEventFrom);
        EventPojo eventPojo = convertUtil.convertDataToPojo(createEventFrom,EventPojo.class);
        return eventFlowApi.createEvent(eventPojo);
    }

    public List<EventPojo> getAllEvent() throws CommonApiException {
        return eventFlowApi.getAllEvent();
    }

    public List<RequestDetailPojo> getAllOpenRequestsForEvent(Long eventId) throws CommonApiException{
        return eventFlowApi.getAllOpenRequestsForEvent(eventId);
    }

    public List<RequestDetailPojo> getAllOpenInvitesFromEvent(Long eventId) throws CommonApiException{
        return eventFlowApi.getAllOpenInvitesFromEvent(eventId);
    }



    public List<EventParticipantsData> getAllEventParticipants(Long eventId) throws CommonApiException{
        return eventFlowApi.getAllEventParticipants(eventId);
    }

    public EventPojo getEventByEventId(Long eventId) throws CommonApiException{
        return eventFlowApi.getEventByEventId(eventId);
    }


}
