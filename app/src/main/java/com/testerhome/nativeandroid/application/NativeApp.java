package com.testerhome.nativeandroid.application;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Bin Li on 2015/9/15.
 */
public class NativeApp extends Application {

    public static RefWatcher getRefWatcher(Context context) {
        NativeApp application = (NativeApp) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }
}
