package com.increff.teamer.model.form;

import com.increff.teamer.model.data.ParticipantsInfoData;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class EventParticipantOldForm {

    @NotNull
    @NotEmpty
    private Long eventId;
    @NotNull
    @NotEmpty
    private List<ParticipantsInfoData> participantsInfoDataList;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<ParticipantsInfoData> getParticipantsInfoDataList() {
        return participantsInfoDataList;
    }

    public void setParticipantsInfoDataList(List<ParticipantsInfoData> participantsInfoDataList) {
        this.participantsInfoDataList = participantsInfoDataList;
    }
    public EventParticipantOldForm(){

    }
    public EventParticipantOldForm(Long eventId, List<ParticipantsInfoData> participantsInfoDataList) {
        this.eventId = eventId;
        this.participantsInfoDataList = participantsInfoDataList;
    }
}
