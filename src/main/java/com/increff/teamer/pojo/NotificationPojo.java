package com.increff.teamer.pojo;

import com.increff.teamer.model.constant.NotificationConstant;
import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class NotificationPojo {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationConstant notificationRelation;
    @Column(nullable = false)
    private Long notificationRelationId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationConstant notificationType;
    @Column(nullable = false)
    private String notificationDescription;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public NotificationConstant getNotificationRelation() {
        return notificationRelation;
    }

    public void setNotificationRelation(NotificationConstant notificationRelation) {
        this.notificationRelation = notificationRelation;
    }

    public Long getNotificationRelationId() {
        return notificationRelationId;
    }

    public void setNotificationRelationId(Long notificationRelationId) {
        this.notificationRelationId = notificationRelationId;
    }

    public NotificationConstant getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationConstant notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public void setNotificationDescription(String notificationDescription) {
        this.notificationDescription = notificationDescription;
    }


    public NotificationPojo(){

    }
    public NotificationPojo(NotificationConstant notificationRelation, Long notificationRelationId, NotificationConstant notificationType, String notificationDescription) {
        this.notificationRelation = notificationRelation;
        this.notificationRelationId = notificationRelationId;
        this.notificationType = notificationType;
        this.notificationDescription = notificationDescription;
    }
}
