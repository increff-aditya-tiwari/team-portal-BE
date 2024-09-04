package com.increff.teamer.dao;

import com.increff.teamer.model.constant.ParticipantType;
import com.increff.teamer.pojo.EventParticipantPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventParticipantDao extends JpaRepository<EventParticipantPojo,Long> {
    public List<EventParticipantPojo> findAllByEventId(Long eventId);
    public EventParticipantPojo findByEventIdAndParticipantIdAndParticipantType(Long eventId, Long participantId, ParticipantType participantType);
    public List<EventParticipantPojo> findByParticipantIdAndParticipantType(Long participantId,ParticipantType participantType);
    public EventParticipantPojo findByParticipantIdAndParticipantTypeAndEventId(Long participantId,ParticipantType participantType,Long eventId);
}
