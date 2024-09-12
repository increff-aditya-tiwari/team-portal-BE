package com.increff.teamer.dao;

import com.increff.teamer.pojo.NotificationPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationDao extends JpaRepository<NotificationPojo,Long> {
    public List<NotificationPojo> findAllByNotificationIdIn(List<Long> notificationIds);
}
