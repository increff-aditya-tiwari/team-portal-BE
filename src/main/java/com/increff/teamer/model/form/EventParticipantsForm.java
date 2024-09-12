package com.increff.teamer.model.form;

import com.increff.teamer.model.constant.ParticipantType;
import com.increff.teamer.model.data.ParticipantsInfoData;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class EventParticipantsForm {
    @NotNull
    private Long eventId;
    @NotNull
    private ParticipantType participantType;
    @NotNull
    @NotEmpty
    private List<Long> participantIds;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }


    public EventParticipantsForm(Long eventId, ParticipantType participantType, List<Long> participantIds) {
        this.eventId = eventId;
        this.participantType = participantType;
        this.participantIds = participantIds;
    }
}
