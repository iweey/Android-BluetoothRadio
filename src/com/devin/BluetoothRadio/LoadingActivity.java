
package com.devin.BluetoothRadio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends Activity {
    private static final String TAG = "LoadingActivity";
    private int TICK;
    private int TOTAL_MILL_SEC;
    private ProgressBar progressBar;
    private TextView versionText;

    public LoadingActivity() {
        TOTAL_MILL_SEC = 3000;
        TICK = 30;
    }

    private String getVersionName() {
        String s;
        try {
            s = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception exception) {
            s = "";
        }
        return s;
    }

    private boolean isDebugTimeOver() {
        boolean flag = false;
        SharedPreferences sharedpreferences = getSharedPreferences("DEBUG_INFO", 0);
        int i = sharedpreferences.getInt("RUN_TIMES", 0);
        if (i >= 30) {
            flag = true;
        } else {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("RUN_TIMES", i + 1);
            editor.commit();
        }
        return flag;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sys_loading);
        versionText = (TextView) findViewById(R.id.versionText);
        TextView textview = versionText;
        Object aobj[] = new Object[1];
        aobj[0] = getVersionName();
        textview.setText(getString(R.string.version, aobj));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        (new CountDownTimer(3000L, 30L) {

            public void onFinish() {
                if (isDebugTimeOver()) {
                    finish();
                } else {
                    Intent intent = new Intent(LoadingActivity.this, BluetoothDemo.class);
                    startActivity(intent);
                    finish();
                }
            }

            public void onTick(long l) {
                progressBar.setProgress(100 - (int) l / TICK);
            }

        }
        ).start();
    }



}
