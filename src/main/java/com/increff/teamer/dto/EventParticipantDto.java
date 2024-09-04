package com.increff.teamer.dto;

import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.flowApi.EventFlowApi;
import com.increff.teamer.model.form.DeleteEventParticipantFrom;
import com.increff.teamer.model.form.EventParticipantOldForm;
import com.increff.teamer.model.form.EventParticipantsForm;
import com.increff.teamer.model.form.UpdateRequestForm;
import com.increff.teamer.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventParticipantDto {

    @Autowired
    ValidationUtil validationUtil;
    @Autowired
    EventFlowApi eventFlowApi;



    public void inviteParticipants(EventParticipantsForm eventParticipantsForm) throws CommonApiException {
        validationUtil.validateForm(eventParticipantsForm);
        eventFlowApi.inviteParticipants(eventParticipantsForm);
    }

    public void eventJoinRequest(Long evenId) throws CommonApiException{
        eventFlowApi.eventJoinRequest(evenId);
    }

    public void updateJoinEventRequestInvite(UpdateRequestForm updateRequestForm) throws CommonApiException{
        validationUtil.validateForm(updateRequestForm);
        eventFlowApi.updateJoinEventRequestInvite(updateRequestForm);
    }

    public void removeEventParticipant(DeleteEventParticipantFrom deleteEventParticipantFrom) throws CommonApiException{
        validationUtil.validateForm(deleteEventParticipantFrom);
        eventFlowApi.removeEventParticipant(deleteEventParticipantFrom);
    }
    public List<Long> getEventByParticipantId(Long participantId) throws CommonApiException{
        return eventFlowApi.getEventParticipants(participantId);
    }

}
