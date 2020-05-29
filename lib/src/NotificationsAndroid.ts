import { Commands } from './commands/Commands';
import { Platform } from 'react-native';
import { Notification } from './DTO/Notification';

export class NotificationsAndroid {
  constructor(private readonly commands: Commands) {
    return new Proxy(this, {
      get(target, name) {
        if (Platform.OS === 'android') {
          return (target as any)[name];
        } else {
          return () => { };
        }
      }
    });
  }

  /**
  * Refresh FCM token
  */
  public registerRemoteNotifications() {
    this.commands.refreshToken();
  }

  /**
   * scheduleLocalNotification
   */
  public scheduleLocalNotification(notification: Notification, id: number) {
    return this.commands.scheduleLocalNotification(notification, id);
  }
}
