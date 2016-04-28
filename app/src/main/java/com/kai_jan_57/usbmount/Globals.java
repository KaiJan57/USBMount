package com.kai_jan_57.usbmount;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Globals {
    public static Context AppContext;

    public static class SharedPrefs {
        public static SharedPreferences sharedPreferences;

        public static String getString(Context context, String key, String defaultValue) {
            if (sharedPreferences == null) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            }
            return sharedPreferences.getString(key, defaultValue);
        }

        public static void setString(Context context, String key, String value) {
            if (sharedPreferences == null) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            }
            sharedPreferences.edit().putString(key, value).apply();
        }
    }
}
