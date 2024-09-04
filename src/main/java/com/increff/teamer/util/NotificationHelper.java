package com.increff.teamer.util;

import com.increff.teamer.model.constant.NotificationConstant;
import org.springframework.stereotype.Component;

@Component
public class NotificationHelper {

    public String getNotificationDescription(NotificationConstant notificationType, NotificationConstant notificationRelation){
        String fruit = "Apple";
        String description = null;

        switch (notificationRelation) {
            case  NotificationConstant.TEAM:
                switch (notificationType) {
                    case NotificationConstant.REQUEST :
                        description = "Someone Requested to Join Your Team";
                        break;
                    case NotificationConstant.INVITE:
                        description="Someone Invited you to Join the Team";
                        break;
                    case NotificationConstant.REQUEST_ACCEPTED:
                        description = "Your Request to Join Team is Accepted";
                        break;
                    case NotificationConstant.REQUEST_REJECTED:
                        description = "Your Request to Join Team is Rejected";
                        break;
                    case NotificationConstant.INVITE_REJECTED:
                        description = "Your Invite to Join Team is Rejected";
                        break;
                    case NotificationConstant.INVITE_ACCEPTED:
                        description = "Your Invite to Join Team is Accepted";
                        break;
                    case NotificationConstant.NOTIFY_TEAM:
                        description = "Someone Notify you About the Team";
                        break;
                    default:
                        description = "You have a Notification";
                        break;
                }
                break;
            case NotificationConstant.EVENT:
                switch (notificationType) {
                    case NotificationConstant.REQUEST :
                        description = "Someone Requested to Join Your Event";
                        break;
                    case NotificationConstant.INVITE:
                        description="Someone Invited you to Join the Event";
                        break;
                    case NotificationConstant.REQUEST_ACCEPTED:
                        description = "Your Request to Join Event is Accepted";
                        break;
                    case NotificationConstant.REQUEST_REJECTED:
                        description = "Your Request to Join Event is Rejected";
                        break;
                    case NotificationConstant.INVITE_REJECTED:
                        description = "Your Invite to Join Event is Rejected";
                        break;
                    case NotificationConstant.INVITE_ACCEPTED:
                        description = "Your Invite to Join Event is Accepted";
                        break;
                    case NotificationConstant.NOTIFY_TEAM:
                        description = "Someone Notify you About the Team";
                        break;
                    default:
                        description = "You have a Notification";
                        break;
                }
                break;
        }
        return description;
    }

}
