import { EventsRegistry } from './events/EventsRegistry';
import { Notification } from './DTO/Notification';
import { NotificationCategory } from './interfaces/NotificationCategory';
import { NotificationsIOS } from './NotificationsIOS';
import { NotificationsAndroid } from './NotificationsAndroid';
import { NotificationRequest } from './DTO/NotificationRequest';
export declare class NotificationsRoot {
    readonly _ios: NotificationsIOS;
    readonly _android: NotificationsAndroid;
    private readonly notificationFactory;
    private readonly nativeEventsReceiver;
    private readonly nativeCommandsSender;
    private readonly commands;
    private readonly eventsRegistry;
    private readonly eventsRegistryIOS;
    private readonly uniqueIdProvider;
    private readonly completionCallbackWrapper;
    constructor();
    /**
     * registerRemoteNotifications
     */
    registerRemoteNotifications(): void;
    /**
     * postLocalNotification
     */
    postLocalNotification(notification: Notification, id: number): void;
    /**
     * getInitialNotification
     */
    getInitialNotification(): Promise<Notification | undefined>;
    /**
     * setCategories
     */
    setCategories(categories: [NotificationCategory?]): void;
    /**
     * cancelLocalNotification
    */
    cancelLocalNotification(notificationId: string): void;
    /**
     * removeAllDeliveredNotifications
     */
    removeAllDeliveredNotifications(): void;
    /**
     * removeDeliveredNotifications
     * @param identifiers Array of notification identifiers
     */
    removeDeliveredNotifications(identifiers: Array<string>): void;
    /**
     * getScheduledLocalNotifications
     */
    getScheduledLocalNotifications(): Promise<NotificationRequest[]>;
    /**
     * getDeliveredNotifications
     */
    getDeliveredNotifications(): Promise<Notification[]>;
    /**
     * isRegisteredForRemoteNotifications
     */
    isRegisteredForRemoteNotifications(): Promise<boolean>;
    /**
     * Obtain the events registry instance
     */
    events(): EventsRegistry;
    /**
     * ios/android getters
     */
    readonly ios: NotificationsIOS;
    readonly android: NotificationsAndroid;
}
