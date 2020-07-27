"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const react_native_1 = require("react-native");
class NotificationsAndroid {
    constructor(commands) {
        this.commands = commands;
        return new Proxy(this, {
            get(target, name) {
                if (react_native_1.Platform.OS === 'android') {
                    return target[name];
                }
                else {
                    return () => { };
                }
            }
        });
    }
    /**
    * Refresh FCM token
    */
    registerRemoteNotifications() {
        this.commands.refreshToken();
    }
    /**
     * scheduleLocalNotification
     */
    scheduleLocalNotification(notification, id) {
        return this.commands.scheduleLocalNotification(notification, id);
    }
    /**
     * getScheduledLocalNotifications
     */
    getScheduledLocalNotifications() {
        return this.commands.getScheduledLocalNotifications();
    }
    /**
     * getDeliveredNotifications
     */
    getDeliveredNotifications() {
        return this.commands.getDeliveredNotifications();
    }
    /**
     * removeDeliveredNotifications
     * @param identifiers Array of notification identifiers
     */
    removeDeliveredNotifications(identifiers) {
        return this.commands.removeDeliveredNotifications(identifiers);
    }
    /**
     * removeAllDeliveredNotifications
     */
    removeAllDeliveredNotifications() {
        return this.commands.removeAllDeliveredNotifications();
    }
}
exports.NotificationsAndroid = NotificationsAndroid;
