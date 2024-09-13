package com.increff.teamer.dto;

import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.flowApi.NotificationFlowApi;
import com.increff.teamer.pojo.NotificationPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationDto {
    @Autowired
    private NotificationFlowApi notificationFlowApi;
    public List<NotificationPojo> getAllNotification(Long userId) throws CommonApiException{
        return notificationFlowApi.getAllNotificationForUser(userId);
    }
}
