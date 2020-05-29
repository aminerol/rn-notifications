package com.wix.reactnativenotifications.core.notificationdrawer;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.wix.reactnativenotifications.BuildConfig;
import com.wix.reactnativenotifications.core.helpers.ScheduleNotificationHelper;
import com.wix.reactnativenotifications.core.AppLaunchHelper;
import com.wix.reactnativenotifications.core.InitialNotificationHolder;

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
//        final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(id);
    }

    @Override
    public void onNotificationClearRequest(String tag, int id) {
        final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(tag, id);
    }

    @Override
    public void onAllNotificationsClearRequest() {
        cancelAllScheduledNotifications();
//        final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancelAll();
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
