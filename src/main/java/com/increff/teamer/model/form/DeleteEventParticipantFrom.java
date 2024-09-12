package com.increff.teamer.model.form;

import com.increff.teamer.model.constant.ParticipantType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class DeleteEventParticipantFrom {
    @NotNull
    private Long participantId;
    @NotNull
    private ParticipantType participantType;
    @NotNull
    private Long eventId;

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
