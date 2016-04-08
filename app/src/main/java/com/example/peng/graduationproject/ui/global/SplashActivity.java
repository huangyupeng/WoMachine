package com.example.peng.graduationproject.ui.global;

import android.os.Bundle;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;

/**
 * Created by Administrator on 2016/4/8.
 */
public class SplashActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_splash);

        initView();
        setDefaultValues();
        bindEvents();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setDefaultValues() {

    }

    @Override
    protected void bindEvents() {

    }
}
