package com.github.zane;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import static com.github.zane.PrefsHelper.KEY_IS_RTL;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    private PrefsHelper prefsHelper;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CustomContextThemeWrapper(newBase, 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefsHelper = PrefsHelper.getInstance(this);
        prefsHelper.getAppPrefs().registerOnSharedPreferenceChangeListener(this);

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefsHelper.setIsRtl(!prefsHelper.isRtl());
            }
        });
    }

    @Override
    protected void onDestroy() {
        prefsHelper.getAppPrefs().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.app_bar_switch);
//        SwitchCompat switchRtl = (SwitchCompat) item.getActionView().findViewById(R.id.switch_rtl);
//        switchRtl.setChecked(prefsHelper.isRtl());
//        switchRtl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                prefsHelper.setIsRtl(isChecked);
//            }
//        });
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (KEY_IS_RTL.equals(key)) {
            ActivityCompat.recreate(this);
        }
    }
}