package com.kevinhuang.lovingweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class CoverActivity extends AppCompatActivity {
    private static final String TAG = "ITS 2.0";
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    private boolean isFirstIn = false;

    private Handler handler = new Handler();
    private DelayThread dt = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);

        if(dt == null) {
            dt = new DelayThread();
            dt.start();
        }
    }

    class DelayThread extends Thread {
        @Override
        public void run(){
            Log.d(TAG, "DelayThread starts, " + Thread.currentThread().getName() + " @CoverActivity");
            try {
                Thread.sleep(3 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Runnable starts, " + Thread.currentThread().getName() + " @CoverActivity");

                    SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);
                    // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
                    isFirstIn = preferences.getBoolean("isFirstIn", true);

                    // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
                    Intent intent = new Intent();
                    if (!isFirstIn) {
                        intent.setClass(CoverActivity.this, GuideActivity.class);
                    } else {
                        intent.setClass(CoverActivity.this, GuideActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }
            };

            handler.post(runnable);
        }
    }
}
