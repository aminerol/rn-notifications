package com.wix.reactnativenotifications.core.notificationdrawer;

import android.app.Activity;

import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.ReadableArray;

import java.util.List;

public interface IPushNotificationsDrawer {
    void onAppInit();
    void onAppVisible();
    void onNewActivity(Activity activity);

    void onNotificationOpened();
    void onNotificationClearRequest(String id);
    void onCancelAllLocalNotifications();
    void onNotificationClearRequest(ReadableArray notificationIds);
    void onAllNotificationsClearRequest();
    WritableArray onGetScheduledLocalNotifications();
    WritableArray onGetDeliveredNotifications();
}
