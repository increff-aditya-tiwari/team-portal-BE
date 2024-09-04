package com.increff.teamer.model.data;

import com.increff.teamer.model.constant.ParticipantType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ParticipantsInfoData {
    @NotNull
    @NotEmpty
    private ParticipantType participantType;
    @NotNull
    @NotEmpty
    private Long participantId;

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }
    public ParticipantsInfoData(){

    }
    public ParticipantsInfoData(ParticipantType participantType, Long participantId) {
        this.participantType = participantType;
        this.participantId = participantId;
    }

    @Override
    public String toString() {
        return "ParticipantsInfoData{" +
                "participantType=" + participantType +
                ", participantId=" + participantId +
                '}';
    }
}
