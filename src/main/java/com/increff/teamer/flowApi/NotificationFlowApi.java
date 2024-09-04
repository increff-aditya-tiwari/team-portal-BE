package com.increff.teamer.flowApi;

import com.increff.teamer.dao.NotificationDao;
import com.increff.teamer.dao.UserNotificationMappingDao;
import com.increff.teamer.model.constant.NotificationConstant;
import com.increff.teamer.pojo.NotificationPojo;
import com.increff.teamer.pojo.UserNotificationMappingPojo;
import com.increff.teamer.util.NotificationHelper;
import com.increff.teamer.util.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationFlowApi {

    @Autowired
    NotificationHelper notificationDescription;
    @Autowired
    NotificationDao notificationDao;
    @Autowired
    WebSocketHandler webSocketHandler;
    @Autowired
    UserNotificationMappingDao userNotificationMappingDao;


    public void generateNotification(List<String> usernameList, NotificationConstant notificationType, NotificationConstant notificationRelation, Long notificationRelationId) throws Exception {
        NotificationPojo notificationPojo = new NotificationPojo(notificationRelation
                ,notificationRelationId
                ,notificationType
                ,notificationDescription.getNotificationDescription(notificationType,notificationRelation));
        notificationPojo = notificationDao.save(notificationPojo);
        System.out.println("we are sending request to " + usernameList.get(0));
        setUserNotificationMapping(usernameList,notificationPojo);
        webSocketHandler.sendMessageToUsers(usernameList,notificationPojo);
    }

    private void setUserNotificationMapping(List<String> usernameList,NotificationPojo notificationPojo) throws Exception {
        List<UserNotificationMappingPojo> userNotificationMappingPojoList = new ArrayList<>();
        for(String username : usernameList){
            UserNotificationMappingPojo userNotificationMappingPojo = new UserNotificationMappingPojo(notificationPojo.getNotificationId(),username,Boolean.FALSE);
            userNotificationMappingPojoList.add(userNotificationMappingPojo);
        }
        try {
            userNotificationMappingDao.saveAll(userNotificationMappingPojoList);
        }catch (Error e){
            throw new Exception("Something went wrong "+e.getMessage());
        }
    }
    public List<NotificationPojo> getAllNotificationForUser(){
        List<NotificationPojo> notificationPojoList = new ArrayList<>();
        // here I have to write the code for getting notification for user
        return notificationPojoList;
    }

    public void markNotificationAsSeen(Long notificationId){
        //Here I have to write the code to delete/mark seen the notification;
    }
    public void markAllNotificationAsSeen(List<Long> notificationIdList){
        //Here I have to write the code to delete/mark seen all the  notification;
    }
}