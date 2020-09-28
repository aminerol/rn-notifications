package com.wix.reactnativenotifications.core.notificationdrawer;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.wix.reactnativenotifications.BuildConfig;
import com.wix.reactnativenotifications.core.helpers.JSONHelpers;
import com.wix.reactnativenotifications.core.helpers.ScheduleNotificationHelper;
import com.wix.reactnativenotifications.core.AppLaunchHelper;
import com.wix.reactnativenotifications.core.InitialNotificationHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.wix.reactnativenotifications.Defs.EXTRAS_NOTIFICATION_REQUEST_KEY;
import static com.wix.reactnativenotifications.Defs.LOGTAG;

public class PushNotificationsDrawer implements IPushNotificationsDrawer {

    final protected Context mContext;
    final protected AppLaunchHelper mAppLaunchHelper;

    public static IPushNotificationsDrawer get(Context context) {
        return PushNotificationsDrawer.get(context, new AppLaunchHelper());
    }

    public static IPushNotificationsDrawer get(Context context, AppLaunchHelper appLaunchHelper) {
        final Context appContext = context.getApplicationContext();
        if (appContext instanceof INotificationsDrawerApplication) {
            return ((INotificationsDrawerApplication) appContext).getPushNotificationsDrawer(context, appLaunchHelper);
        }

        return new PushNotificationsDrawer(context, appLaunchHelper);
    }

    protected PushNotificationsDrawer(Context context, AppLaunchHelper appLaunchHelper) {
        mContext = context;
        mAppLaunchHelper = appLaunchHelper;
    }

    @Override
    public void onAppInit() {
        clearAll();
    }

    @Override
    public void onAppVisible() {
        clearAll();
    }

    @Override
    public void onNewActivity(Activity activity) {
        boolean launchIntentsActivity = mAppLaunchHelper.isLaunchIntentsActivity(activity);
        boolean launchIntentOfNotification = mAppLaunchHelper.isLaunchIntentOfNotification(activity.getIntent());
        if (launchIntentsActivity && !launchIntentOfNotification) {
            InitialNotificationHolder.getInstance().clear();
        }
    }

    @Override
    public void onNotificationOpened() {
        clearAll();
    }

    @Override
    public void onNotificationClearRequest(String id) {
        cancelScheduledNotification(id);
    }

    @Override
    public void onNotificationClearRequest(ReadableArray notificationIds) {
        ArrayList<String> ids = Arguments.toList(notificationIds);
        for (String notificationId: ids) {
            cancelScheduledNotification(notificationId);
        }
    }

    @Override
    public void onAllNotificationsClearRequest() {
        cancelAllScheduledNotifications();
    }

    @Override
    public void onCancelAllLocalNotifications() {
        clearAll();
        cancelAllScheduledNotifications();
    }

    protected void clearAll() {
        final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @Override
    public WritableArray onGetScheduledLocalNotifications() {
        if(BuildConfig.DEBUG) Log.i(LOGTAG, "Get all scheduled notifications");
        WritableArray scheduled = Arguments.createArray();
        ScheduleNotificationHelper helper = ScheduleNotificationHelper.getInstance(mContext);
        for (Map.Entry<String, ?> entry : helper.getPreferencesValues()) {
            try {
                JSONObject json = new JSONObject(entry.getValue().toString());
                scheduled.pushMap(JSONHelpers.jsonToReact(json));
            } catch (JSONException e) {
                if(BuildConfig.DEBUG) Log.e(LOGTAG, e.getMessage());
            }
        }
        return scheduled;
    }

    @Override
    public WritableArray onGetDeliveredNotifications() {
        if(BuildConfig.DEBUG) Log.i(LOGTAG, "Get all delivered notifications");
        WritableArray delivered = Arguments.createArray();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return delivered;
        }
        final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        StatusBarNotification[] activeNotifications = notificationManager.getActiveNotifications();
        for (StatusBarNotification statusBarNotification : activeNotifications) {
            try {
                Bundle extra = statusBarNotification.getNotification().extras.getBundle(EXTRAS_NOTIFICATION_REQUEST_KEY);
                if(extra != null){
                    JSONObject json = JSONHelpers.BundleToJson(extra);
                    json.put("identifier", statusBarNotification.getId());
                    delivered.pushMap(JSONHelpers.jsonToReact(json));
                }
            }catch (JSONException e) {
                if(BuildConfig.DEBUG) Log.e(LOGTAG, e.getMessage());
            }
        }
        return delivered;
    }

    protected void cancelAllScheduledNotifications() {
        if(BuildConfig.DEBUG) Log.i(LOGTAG, "Cancelling all scheduled notifications");
        ScheduleNotificationHelper helper = ScheduleNotificationHelper.getInstance(mContext);

        for (String notificationId : helper.getPreferencesKeys()) {
            cancelScheduledNotification(notificationId);
        }
    }

    protected void cancelScheduledNotification(String notificationId) {
        if(BuildConfig.DEBUG) Log.i(LOGTAG, "Cancelling scheduled notification: " + notificationId);

        ScheduleNotificationHelper helper = ScheduleNotificationHelper.getInstance(mContext);

        // Remove it from the alarm manger schedule
        Bundle bundle = new Bundle();
        bundle.putString("id", notificationId);
        PendingIntent pendingIntent = helper.createPendingNotificationIntent(bundle, Integer.valueOf(notificationId));
        helper.cancelScheduledNotificationIntent(pendingIntent);

        helper.removePreference(notificationId);

        // Remove it from the notification center
        final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(Integer.parseInt(notificationId));
    }
}
