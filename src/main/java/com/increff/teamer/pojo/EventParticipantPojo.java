package com.increff.teamer.pojo;

import com.increff.teamer.model.constant.ParticipantType;
import jakarta.persistence.*;

@Entity
@Table(name = "event_participants",
        uniqueConstraints = @UniqueConstraint(name = "event_participation",columnNames = {"participantId","eventId","participantType"}))
public class EventParticipantPojo extends AbstractVersionedPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private Long participantId;
    @Column(nullable = false)
    private Long eventId;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ParticipantType participantType;

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
    public EventParticipantPojo(){

    }
    public EventParticipantPojo(Long participantId, Long eventId, ParticipantType participantType) {
        this.participantId = participantId;
        this.eventId = eventId;
        this.participantType = participantType;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
