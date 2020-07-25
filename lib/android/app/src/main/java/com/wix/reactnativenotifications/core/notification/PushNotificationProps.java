package com.wix.reactnativenotifications.core.notification;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

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
        JSONObject json = new JSONObject();
        for (String key : mBundle.keySet()) {
            try {
                json.put(key, JSONObject.wrap(mBundle.get(key)));
            } catch(JSONException e) {
            }
        }
        return json.toString();
    }

    protected PushNotificationProps copy() {
        return new PushNotificationProps((Bundle) mBundle.clone());
    }
}
