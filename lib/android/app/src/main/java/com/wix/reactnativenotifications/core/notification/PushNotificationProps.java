package com.wix.reactnativenotifications.core.notification;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.wix.reactnativenotifications.core.helpers.JSONHelpers;
import com.wix.reactnativenotifications.BuildConfig;

public class PushNotificationProps {

    protected Bundle mBundle;

    public PushNotificationProps(Bundle bundle) {
        mBundle = bundle;
    }

    public String getTitle() {
        return mBundle.getString("title");
    }

    public String getBody() {
        return mBundle.getString("body");
    }

    public Bundle asBundle() {
        return (Bundle) mBundle.clone();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public String toString() {
        return JSONHelpers.BundleToJson(mBundle).toString();
    }

    protected PushNotificationProps copy() {
        return new PushNotificationProps((Bundle) mBundle.clone());
    }
}
