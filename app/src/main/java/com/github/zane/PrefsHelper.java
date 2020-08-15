package com.github.zane;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsHelper {
    private static final String PREFS_NAME = "app";
    public static final String KEY_IS_RTL = "KEY_IS_RTL";
    private static PrefsHelper prefsHelper;
    private SharedPreferences appPrefs;

    private PrefsHelper(Context context) {
        appPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static PrefsHelper getInstance(Context context) {
        if (prefsHelper == null) {
            prefsHelper = new PrefsHelper(context);
        }
        return prefsHelper;
    }

    public SharedPreferences getAppPrefs() {
        return appPrefs;
    }

    public boolean isRtl() {
        return appPrefs.getBoolean(KEY_IS_RTL, false);
    }

    public void setIsRtl(boolean isRtl) {
        appPrefs.edit().putBoolean(KEY_IS_RTL, isRtl).apply();
    }
}
