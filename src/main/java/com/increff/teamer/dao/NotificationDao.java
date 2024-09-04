package com.increff.teamer.dao;

import com.increff.teamer.pojo.NotificationPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDao extends JpaRepository<NotificationPojo,Long> {

}
