package com.example.peng.graduationproject.common;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peng.graduationproject.R;

/**
 * Created by peng on 2016/3/28.
 */
public abstract class BaseFragment extends Fragment {
    private Toast mToast;

    private Handler mUiHandler;
    private HandlerThread mProcThread;
    private Handler mProcHandler;

    private View contentView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mProcThread = new HandlerThread(this.getClass().getName());
        mProcThread.start();

        mUiHandler = new Handler(getActivity().getMainLooper(),mUiHandlerCallback);
        mProcHandler = new Handler(mProcThread.getLooper(),mProcHandlerCallBack);

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

    protected abstract void initView(View view);

    protected abstract void setDefaultValues();

    protected abstract void bindEvents();

    public void showToast(String info){
        if (mToast==null){
            mToast = Toast.makeText(getActivity(),info,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(info);
        }
        mToast.show();
    }

    public void showToastOnUiThread(final String info)
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(info);
            }
        });
    }

    @Override
    public  void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        if (mProcThread!=null){
            mProcThread.quit();
            mProcThread=null;
        }

        super.onDestroy();

    }
}
