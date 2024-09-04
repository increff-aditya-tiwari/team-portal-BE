package com.increff.teamer.dao;

import com.increff.teamer.pojo.EventPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDao extends JpaRepository<EventPojo,Long> {
    public EventPojo findByEventId(Long eventId);
    public EventPojo findByEventName(String eventName);
}
