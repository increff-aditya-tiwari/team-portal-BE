package com.increff.teamer.model.form;

import com.increff.teamer.model.constant.EventCategory;
import com.increff.teamer.model.constant.EventStatus;
import com.increff.teamer.model.constant.ParticipantType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateEventFrom {

    @NotEmpty
    @NotNull
    private EventCategory category;
    @NotEmpty
    @NotNull
    private EventStatus status;
    @NotEmpty
    @NotNull
    private String eventName;
    @NotEmpty
    @NotNull
    private String description;

    @Override
    public String toString() {
        return "CreateEventFrom{" +
                "category=" + category +
                ", status=" + status +
                ", eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventCategory getEventCategory() {
        return category;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.category = eventCategory;
    }

    public EventStatus getEventStatus() {
        return status;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.status = eventStatus;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

}
