package com.increff.teamer.model.data;

import com.increff.teamer.model.constant.ParticipantType;

public class EventParticipantsData {
    private Long participantId;
    private ParticipantType participantType;
    private String participantName;

    private Long participantCreatedBy;

    public Long getParticipantCreatedBy() {
        return participantCreatedBy;
    }

    public EventParticipantsData(Long participantId, ParticipantType participantType, String participantName, Long participantCreatedBy) {
        this.participantId = participantId;
        this.participantType = participantType;
        this.participantName = participantName;
        this.participantCreatedBy = participantCreatedBy;
    }

    public void setParticipantCreatedBy(Long participantCreatedBy) {
        this.participantCreatedBy = participantCreatedBy;
    }

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

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        participantName = participantName;
    }

    public EventParticipantsData(){

    }

    public EventParticipantsData(Long participantId, ParticipantType participantType, String participantName) {
        this.participantId = participantId;
        this.participantType = participantType;
        this.participantName = participantName;
    }
}
