package com.example.peng.graduationproject.common;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.example.peng.graduationproject.R;

/**
 * Created by peng on 2016/3/26.
 */
public abstract class BaseActivity extends FragmentActivity{

    private FrameLayout content_layout;
    private ImageView btn_back;
    private TextView txt_title;
    private TextView txt_right;
    private ImageView btn_filter;
    private ImageView btn_add;

    private RelativeLayout title_bar;

    private Toast mToast;

    private Handler mUiHandler;
    private HandlerThread mProcThread;
    private Handler mProcHandler;

    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        content_layout = (FrameLayout)findViewById(R.id.content_layout);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        txt_title = (TextView)findViewById(R.id.txt_title);
        txt_right = (TextView)findViewById(R.id.txt_right);
        btn_filter = (ImageView)findViewById(R.id.btn_filter);
        btn_add = (ImageView)findViewById(R.id.btn_add);
        title_bar = (RelativeLayout)findViewById(R.id.title_bar);


        btn_back.setVisibility(View.GONE);
        txt_right.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        btn_filter.setVisibility(View.GONE);

        mProcThread = new HandlerThread(this.getClass().getName());
        mProcThread.start();

        mUiHandler = new Handler(this.getMainLooper(),mUiHandlerCallback);
        mProcHandler = new Handler(mProcThread.getLooper(),mProcHandlerCallBack);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionBarBackClick();
            }
        });

    }

    protected void setContentLayout(int resId){

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(resId,null);
        if (content_layout!=null){
            content_layout.addView(contentView);
        }
    }

    private android.os.Handler.Callback mUiHandlerCallback = new android.os.Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg){
            return uiHandlerCallback(msg);
        }
    };

    private Handler.Callback mProcHandlerCallBack = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return procHandlerCallBack(msg);
        }
    };


    //子类重载方法实现handlerCallback
    protected boolean uiHandlerCallback(Message msg){
        return false;
    }
    protected boolean procHandlerCallBack(Message msg){
        return false;
    }


    public Handler getUiHanler(){
        return mUiHandler;
    }

    public Handler getProcHandler(){
        return mProcHandler;
    }



    public ImageView getBtn_back(){
        return btn_back;
    }

    public TextView getTxt_right(){
        return txt_right;
    }

    public ImageView getBtn_filter(){
        return btn_filter;
    }

    public ImageView getBtn_add(){
        return btn_add;
    }


    protected void setTitleText(int resId){
        txt_title.setText(getResources().getString(resId));
    }

    protected void setNoTitle(boolean notitle){
        if (notitle){
            title_bar.setVisibility(View.GONE);
        }
    }

    //默认返回按钮执行动作
    protected void onActionBarBackClick(){
        finish();
    }


    /*
    protected void setBtn_backVisibility(int visibility){
        btn_back.setVisibility(visibility);
    }

    protected void setBtn_backListener(View.OnClickListener listener){
        if (listener!=null){
            btn_back.setOnClickListener(listener);
        }
    }

    protected void setBtn_filterVisibility(int visibility){
        btn_filter.setVisibility(visibility);
    }

    protected void setBtn_filterListener(View.OnClickListener l){
        if (l!=null){
            btn_filter.setOnClickListener(l);
        }
    }

    protected void setTxt_right(String txt,int visibility){
        if (txt!=null) {
            txt_right.setText(txt);
        }
        txt_right.setVisibility(visibility);
    }

    protected void setTxt_rightListener(View.OnClickListener l){
        if (l!=null){
            txt_right.setOnClickListener(l);
        }
    }

    protected void setBtn_addVisibility(int visibility){
        btn_add.setVisibility(visibility);
    }

    protected void setBtn_addListener(View.OnClickListener l){
        if (l!=null){
            btn_add.setOnClickListener(l);
        }
    }*/


    protected abstract void initView();

    protected abstract void setDefaultValues();

    protected abstract void bindEvents();

    public void showToast(String info){
        if (mToast==null){
            mToast = Toast.makeText(this,info,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(info);
        }
        mToast.show();
    }

    public void showToastOnUiThread(final String info)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(info);
            }
        });
    }

    public void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected  void onResume(){
        super.onResume();
    }

    @Override
    protected  void onRestart(){
        super.onRestart();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        if (mProcThread!=null){
            mProcThread.quit();
            mProcThread=null;
        }

        super.onDestroy();
    }
}
