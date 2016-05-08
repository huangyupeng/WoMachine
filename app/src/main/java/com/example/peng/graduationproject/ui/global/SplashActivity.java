package com.example.peng.graduationproject.ui.global;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;
import com.example.peng.graduationproject.common.Constants;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/8.
 */
public class SplashActivity extends BaseActivity{

    private SharedPreferences mPref;

    private final long MIN_WAIT_TIME = 2000;

    private long mStartTime;
    private long mEndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_splash);
        setNoTitle(true);

        initView();
        setDefaultValues();
        bindEvents();

        getProcHandler().post(mProcRunnable);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setDefaultValues() {

        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        mStartTime = SystemClock.elapsedRealtime();

    }

    @Override
    protected void bindEvents() {

    }

    private final Runnable mProcRunnable = new Runnable() {
        @Override
        public void run() {
            loadConfig();

            autoLogin();

            syncData();

            getUiHanler().post(mUiRunnable);

        }
    };

    private void autoLogin(){

    }

    private void loadConfig(){
        try {
            File nomedia = new File(Constants.FILE_DIRECTORY, ".nomedia");
            if (!nomedia.exists())
                nomedia.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void syncData(){

    }

    private final Runnable mUiRunnable = new Runnable() {
        @Override
        public void run() {

            mEndTime = SystemClock.elapsedRealtime();
            long timeout = mEndTime - mStartTime - MIN_WAIT_TIME;
            long delayMillis = 0;
            if (timeout < 0)
            {
                // 延时timeout毫秒才进入.
                delayMillis = Math.abs(timeout);
            }

            new Handler().postDelayed(new Runnable()
            {
                public void run()
                {
                    Intent intent = new Intent(SplashActivity.this, loginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, delayMillis);

        }
    };
}
