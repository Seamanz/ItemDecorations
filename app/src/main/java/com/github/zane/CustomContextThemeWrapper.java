package com.github.zane;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.appcompat.view.ContextThemeWrapper;

import java.util.Locale;

public class CustomContextThemeWrapper extends ContextThemeWrapper {

    public CustomContextThemeWrapper(Context base, int themeResId) {
        super(base, themeResId);
    }

    public CustomContextThemeWrapper(Context base, Resources.Theme theme) {
        super(base, theme);
    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            boolean isRtl = PrefsHelper.getInstance(this).isRtl();
            Locale newLocale = isRtl ? new Locale("ar") : Locale.ENGLISH;
            Locale.setDefault(newLocale);
            overrideConfiguration.setLocale(newLocale);
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }
}
