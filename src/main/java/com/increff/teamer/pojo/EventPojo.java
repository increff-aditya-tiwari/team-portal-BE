package com.increff.teamer.pojo;

import com.increff.teamer.model.constant.EventCategory;
import com.increff.teamer.model.constant.EventStatus;
import com.increff.teamer.model.constant.ParticipantType;
import jakarta.persistence.*;

@Entity
@Table(name = "events",
        uniqueConstraints = @UniqueConstraint(name = "event",columnNames = {"eventName"}))
public class EventPojo extends AbstractVersionedPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long eventId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategory category;
    @Column(nullable = false)
    private String eventName;
    @Column(nullable = false)
    private Long eventOrganiser;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    @Column(length = 5000)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public EventCategory getEventCategory() {
        return category;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.category = eventCategory;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getEventOrganiser() {
        return eventOrganiser;
    }

    public void setEventOrganiser(Long eventOrganiser) {
        this.eventOrganiser = eventOrganiser;
    }

    public EventStatus getEventStatus() {
        return status;
    }

    public void setEventStatus(EventStatus status) {
        this.status = status;
    }
}
