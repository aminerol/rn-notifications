package com.wix.reactnativenotifications.core.notificationdrawer;

import android.app.Activity;

import com.facebook.react.bridge.WritableArray;

public interface IPushNotificationsDrawer {
    void onAppInit();
    void onAppVisible();
    void onNewActivity(Activity activity);

    void onNotificationOpened();
    void onNotificationClearRequest(String id);
    void onCancelAllLocalNotifications();
    void onNotificationClearRequest(String tag, int id);
    void onAllNotificationsClearRequest();
    WritableArray onGetScheduledLocalNotifications();
    WritableArray onGetDeliveredNotifications();
}
