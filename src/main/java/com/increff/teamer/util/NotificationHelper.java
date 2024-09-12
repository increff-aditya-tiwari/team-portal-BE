package com.increff.teamer.util;

import com.increff.teamer.model.constant.NotificationConstant;
import org.springframework.stereotype.Component;

@Component
public class NotificationHelper {

    public String getNotificationDescription(NotificationConstant notificationType, NotificationConstant notificationRelation){
        String fruit = "Apple";
        String description = switch (notificationRelation) {
            case NotificationConstant.TEAM -> switch (notificationType) {
                case NotificationConstant.REQUEST -> "Someone Requested to Join Your Team";
                case NotificationConstant.INVITE -> "Someone Invited you to Join the Team";
                case NotificationConstant.REQUEST_ACCEPTED -> "Your Request to Join Team is Accepted";
                case NotificationConstant.REQUEST_REJECTED -> "Your Request to Join Team is Rejected";
                case NotificationConstant.INVITE_REJECTED -> "Your Invite to Join Team is Rejected";
                case NotificationConstant.INVITE_ACCEPTED -> "Your Invite to Join Team is Accepted";
                case NotificationConstant.NOTIFY_TEAM -> "Someone Notify you About the Team";
                default -> "You have a Notification";
            };
            case NotificationConstant.EVENT -> switch (notificationType) {
                case NotificationConstant.REQUEST -> "Someone Requested to Join Your Event";
                case NotificationConstant.INVITE -> "Someone Invited you to Join the Event";
                case NotificationConstant.REQUEST_ACCEPTED -> "Your Request to Join Event is Accepted";
                case NotificationConstant.REQUEST_REJECTED -> "Your Request to Join Event is Rejected";
                case NotificationConstant.INVITE_REJECTED -> "Your Invite to Join Event is Rejected";
                case NotificationConstant.INVITE_ACCEPTED -> "Your Invite to Join Event is Accepted";
                case NotificationConstant.NOTIFY_TEAM -> "Someone Notify you About the Team";
                default -> "You have a Notification";
            };
            default -> null;
        };

        return description;
    }

}
