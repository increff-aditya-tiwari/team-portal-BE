package com.increff.teamer.api;

import com.increff.teamer.dao.NotificationDao;
import com.increff.teamer.dao.UserNotificationMappingDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.NotificationConstant;
import com.increff.teamer.pojo.NotificationPojo;
import com.increff.teamer.pojo.UserNotificationMappingPojo;
import com.increff.teamer.util.NotificationHelper;
import com.increff.teamer.util.WebSocketHandler;
import io.jsonwebtoken.io.CodecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationApi {

    @Autowired
    NotificationHelper notificationHelper;
    @Autowired
    NotificationDao notificationDao;
    @Autowired
    WebSocketHandler webSocketHandler;
    @Autowired
    UserNotificationMappingDao userNotificationMappingDao;

    public void generateNotification(List<String> usernameList, NotificationConstant notificationType, NotificationConstant notificationRelation, Long notificationRelationId) throws CommonApiException {
        NotificationPojo notificationPojo = new NotificationPojo(notificationRelation
                ,notificationRelationId
                ,notificationType
                ,notificationHelper.getNotificationDescription(notificationType,notificationRelation));
        notificationPojo = notificationDao.save(notificationPojo);
        System.out.println("we are sending request to " + usernameList.get(0));
        setUserNotificationMapping(usernameList,notificationPojo);
        webSocketHandler.sendMessageToUsers(usernameList,notificationPojo);
    }

    private void setUserNotificationMapping(List<String> usernameList,NotificationPojo notificationPojo) throws CommonApiException {
        List<UserNotificationMappingPojo> userNotificationMappingPojoList = new ArrayList<>();
        for(String username : usernameList){
            UserNotificationMappingPojo userNotificationMappingPojo = new UserNotificationMappingPojo(notificationPojo.getNotificationId(),username,Boolean.FALSE);
            if(validateUserNotification(userNotificationMappingPojo)){
                userNotificationMappingPojoList.add(userNotificationMappingPojo);
            }
        }
        userNotificationMappingDao.saveAll(userNotificationMappingPojoList);
    }

    public List<NotificationPojo> getAllNotificationForUser(String username) throws CommonApiException{
        List<UserNotificationMappingPojo> userNotificationMappingPojoList = userNotificationMappingDao.findByUsername(username);
        return notificationDao.findAllByNotificationIdIn(
                userNotificationMappingPojoList
                        .stream()
                        .map(UserNotificationMappingPojo::getNotificationId)
                        .collect(Collectors.toList()));
    }

    private Boolean validateUserNotification(UserNotificationMappingPojo userNotificationMappingPojo) throws CommonApiException{
        UserNotificationMappingPojo existingMap = userNotificationMappingDao.findByNotificationIdAndUsername(userNotificationMappingPojo.getNotificationId(),userNotificationMappingPojo.getUsername());
        return existingMap == null;
    }
}
