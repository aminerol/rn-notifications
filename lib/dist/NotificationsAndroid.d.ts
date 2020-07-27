import { Commands } from './commands/Commands';
import { Notification } from './DTO/Notification';
import { NotificationRequest } from './DTO/NotificationRequest';
export declare class NotificationsAndroid {
    private readonly commands;
    constructor(commands: Commands);
    /**
    * Refresh FCM token
    */
    registerRemoteNotifications(): void;
    /**
     * scheduleLocalNotification
     */
    scheduleLocalNotification(notification: Notification, id: number): void;
    /**
     * getScheduledLocalNotifications
     */
    getScheduledLocalNotifications(): Promise<NotificationRequest[]>;
    /**
     * getDeliveredNotifications
     */
    getDeliveredNotifications(): Promise<Notification[]>;
}
