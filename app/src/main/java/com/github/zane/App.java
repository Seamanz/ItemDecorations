package com.github.zane;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(new CustomContextThemeWrapper(base, 0));
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
