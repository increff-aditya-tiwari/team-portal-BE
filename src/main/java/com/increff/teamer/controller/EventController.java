package com.increff.teamer.controller;


import com.increff.teamer.dto.EventDto;
import com.increff.teamer.dto.EventParticipantDto;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.data.EventData;
import com.increff.teamer.model.data.EventParticipantsData;
import com.increff.teamer.model.form.*;
import com.increff.teamer.pojo.EventPojo;
import com.increff.teamer.pojo.RequestDetailPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        allowCredentials = "true"
)
@RequestMapping(value = "event")
public class EventController {
    @Autowired
    private EventDto eventDto;

    @Autowired
    private EventParticipantDto eventParticipantDto;


    @PostMapping("/create")
    public EventPojo createEvent(@RequestBody CreateEventFrom createEventFrom) throws CommonApiException {
        return eventDto.createEvent(createEventFrom);
    }

    @GetMapping("/get-all")
    public List<EventData> getAllEvents() throws CommonApiException {
        return eventDto.getAllEvent();
    }

    @PostMapping("/invite-participant")
    public void inviteParticipants(@RequestBody EventParticipantsForm eventParticipantsForm) throws CommonApiException {
        eventParticipantDto.inviteParticipants(eventParticipantsForm);
    }

    @PostMapping("/join-request")
    public void eventJoinRequest(@RequestBody EventParticipantsForm eventParticipantsForm) throws  CommonApiException{
        eventParticipantDto.eventJoinRequest(eventParticipantsForm.getEventId());
    }

    @GetMapping("/get-all-requests/{eventId}")
    public List<RequestDetailPojo> getAllRequestsForEvent(@PathVariable("eventId") Long eventId) throws CommonApiException{
        return eventDto.getAllOpenRequestsForEvent(eventId);
    }

    @GetMapping("/get-all-invites/{eventId}")
    public List<RequestDetailPojo> getAllInvitesFromEvent(@PathVariable("eventId") Long eventId) throws CommonApiException{
        return eventDto.getAllOpenInvitesFromEvent(eventId);
    }

    @PostMapping("/join-request-update")
    public List<RequestDetailPojo> updateJoinEventRequest(@RequestBody UpdateRequestForm updateRequestForm) throws CommonApiException {
        return eventParticipantDto.updateJoinEventRequestInvite(updateRequestForm);
    }

    @GetMapping("/get-all-participants/{eventId}")
    public List<EventParticipantsData> getAllEventParticipants(@PathVariable("eventId") Long eventId)throws CommonApiException{
       return eventDto.getAllEventParticipants(eventId);
    }

    @PostMapping("/remove-participant")
    public void removeEventParticipant(@RequestBody DeleteEventParticipantFrom deleteEventParticipantFrom) throws CommonApiException {
        eventParticipantDto.removeEventParticipant(deleteEventParticipantFrom);
    }

    @GetMapping("/get-all-events-for-participants/{eventId}")
    public List<Long> getParticipantEventList(@PathVariable("eventId") Long eventId) throws CommonApiException{
        return eventParticipantDto.getEventByParticipantId(eventId);
    }

    @GetMapping("/get/{eventId}")
    public EventPojo getEventByEventId(@PathVariable("eventId") Long eventId) throws CommonApiException{
        return eventDto.getEventByEventId(eventId);
    }
}
