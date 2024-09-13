package com.increff.teamer.flowApi;

import com.increff.teamer.api.NotificationApi;
import com.increff.teamer.api.UserApi;
import com.increff.teamer.dao.NotificationDao;
import com.increff.teamer.dao.UserNotificationMappingDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.NotificationConstant;
import com.increff.teamer.pojo.NotificationPojo;
import com.increff.teamer.pojo.UserNotificationMappingPojo;
import com.increff.teamer.pojo.UserPojo;
import com.increff.teamer.util.NotificationHelper;
import com.increff.teamer.util.WebSocketHandler;
import io.jsonwebtoken.io.CodecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationFlowApi {

    @Autowired
    private UserApi userApi;
    @Autowired
    private NotificationApi notificationApi;


    public List<NotificationPojo> getAllNotificationForUser(Long userId) throws CommonApiException {
        UserPojo userPojo = userApi.isValidUser(userId);
        return notificationApi.getAllNotificationForUser(userPojo.getUsername());
    }

    public void markNotificationAsSeen(Long notificationId){
        //Here I have to write the code to delete/mark seen the notification;
    }
    public void markAllNotificationAsSeen(List<Long> notificationIdList){
        //Here I have to write the code to delete/mark seen all the  notification;
    }
}