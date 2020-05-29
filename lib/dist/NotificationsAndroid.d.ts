import { Commands } from './commands/Commands';
import { Notification } from './DTO/Notification';
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
}
