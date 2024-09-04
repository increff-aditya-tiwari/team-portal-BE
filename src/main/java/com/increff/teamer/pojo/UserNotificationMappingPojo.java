package com.increff.teamer.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "user_notification_mapping",uniqueConstraints = @UniqueConstraint(name = "user_notification_unique_constraint",columnNames = {"notificationId","userName"}))
public class UserNotificationMappingPojo extends AbstractVersionedPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private Long notificationId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private Boolean isUnseen;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getUnseen() {
        return isUnseen;
    }

    public void setUnseen(Boolean unseen) {
        isUnseen = unseen;
    }

    public UserNotificationMappingPojo(Long notificationId, String username, Boolean isUnseen) {
        this.notificationId = notificationId;
        this.username = username;
        this.isUnseen = isUnseen;
    }
}
