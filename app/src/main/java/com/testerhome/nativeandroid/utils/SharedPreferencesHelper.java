
package com.testerhome.nativeandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by zuoxian on 15/9/7.
 */
public class SharedPreferencesHelper {

    private static final String APP_NAME = "com.testerhome.nativeandroid";
    public static SharedPreferencesHelper instance;
    protected Context mContext;
    protected SharedPreferences sharedPreferences;


    public SharedPreferencesHelper(Context context) {
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(APP_NAME, 0);
    }

    public static synchronized SharedPreferencesHelper getInstance(Context context) {
        if (null == instance)
            instance = new SharedPreferencesHelper(context.getApplicationContext());
        return instance;
    }

    public String getValue(String key) {
        return sharedPreferences.getString(key, "");
    }

    public Boolean getBoolValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public int getIntValue(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void setValue(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void setValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public Set<String> getSaveKeys() {
        return sharedPreferences.getAll().keySet();
    }
}
