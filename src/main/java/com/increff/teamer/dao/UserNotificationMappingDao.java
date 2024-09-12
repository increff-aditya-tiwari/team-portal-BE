package com.increff.teamer.dao;

import com.increff.teamer.pojo.UserNotificationMappingPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationMappingDao extends JpaRepository<UserNotificationMappingPojo,Long> {
    public UserNotificationMappingPojo findByNotificationIdAndUsername(Long notificationId,String username);
    public List<UserNotificationMappingPojo> findByUsername(String username);
}
