package com.increff.teamer.util;

import com.increff.teamer.model.constant.NotificationConstant;
import org.springframework.stereotype.Component;

@Component
public class NotificationHelper {
    public String getNotificationDescription(NotificationConstant notificationType, NotificationConstant notificationRelation){
        String DEFAULT_NOTIFICATION_DESCRIPTION = "You have a Notification";
        return switch (notificationRelation) {
            case NotificationConstant.TEAM -> switch (notificationType) {
                case NotificationConstant.REQUEST -> "Someone Requested to Join Your Team";
                case NotificationConstant.INVITE -> "Someone Invited you to Join the Team";
                case NotificationConstant.REQUEST_ACCEPTED -> "Your Request to Join Team is Accepted";
                case NotificationConstant.REQUEST_REJECTED -> "Your Request to Join Team is Rejected";
                case NotificationConstant.INVITE_REJECTED -> "Your Invite to Join Team is Rejected";
                case NotificationConstant.INVITE_ACCEPTED -> "Your Invite to Join Team is Accepted";
                case NotificationConstant.NOTIFY_TEAM -> "Someone Notify you About the Team";
                default -> DEFAULT_NOTIFICATION_DESCRIPTION;
            };
            case NotificationConstant.EVENT -> switch (notificationType) {
                case NotificationConstant.REQUEST -> "Someone Requested to Join Your Event";
                case NotificationConstant.INVITE -> "Someone Invited you to Join the Event";
                case NotificationConstant.REQUEST_ACCEPTED -> "Your Request to Join Event is Accepted";
                case NotificationConstant.REQUEST_REJECTED -> "Your Request to Join Event is Rejected";
                case NotificationConstant.INVITE_REJECTED -> "Your Invite to Join Event is Rejected";
                case NotificationConstant.INVITE_ACCEPTED -> "Your Invite to Join Event is Accepted";
                case NotificationConstant.NOTIFY_TEAM -> "Someone Notify you About the Team";
                default -> DEFAULT_NOTIFICATION_DESCRIPTION;
            };
            case NotificationConstant.CLAIM -> switch (notificationType) {
                case NotificationConstant.CLAIM_APPROVAL -> "You have a Pending Claim Approval";
                case NotificationConstant.CLAIM_REJECTED -> "Your Claim Approval is Rejected";
                case NotificationConstant.CLAIM_APPROVED -> "YYour Claim Approval is Approved";
                default -> DEFAULT_NOTIFICATION_DESCRIPTION;
            };
            default -> null;
        };
    }

}
