package com.example.peng.graduationproject.ui.global;

/**
 * Created by cuinaitian on 2016/3/30 0030.
 */


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;
import com.example.peng.graduationproject.common.NetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class registerActivity extends BaseActivity implements OnClickListener{   //注册activity

    private static final int EVENT_REGISTER = 3;


    private EditText register_number,register_code,register_name,register_password;
    private Button register_getcode,register;


    private long mCodeSendTime;
    private final long CodeSendMinTime = 60000;

    private HashMap<String,String> params;

    private String type;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_register);

        setTitleText("注册账号");
        getBtn_back().setVisibility(View.VISIBLE);

        initView();
        setDefaultValues();
        bindEvents();



    }

    @Override
    protected void initView() {

        register=(Button)findViewById(R.id.register_send);
        register_getcode=(Button)findViewById(R.id.register_getcode);
        register_number=(EditText)findViewById(R.id.register_number);
        register_code=(EditText)findViewById(R.id.register_code);
        register_name=(EditText)findViewById(R.id.register_name);
        register_password=(EditText)findViewById(R.id.register_password);

    }

    @Override
    protected void setDefaultValues() {

        SMSSDK.initSDK(this, "115c1798156e7", "4864103a3c76a4d061449910cce32041");//smssdk的app key和app secret
    }

    @Override
    protected void bindEvents() {

        SMSSDK.registerEventHandler(eh); //注册短信回调


        register_getcode.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {

        switch(arg0.getId()){
            case R.id.register_send:

                if(register_number.getText().toString().length()!=11)
                    showToast("手机号码位数不对");
                else if(register_password.getText().toString().length()<6)
                    showToast("密码至少需要6位");
                else if((register_code.getText().toString().equals("")))
                    showToast("未输入验证码");
                else if(NetManager.isConnect(registerActivity.this)) {
                    SMSSDK.submitVerificationCode("86", register_number.getText().toString(), register_code.getText().toString());
                }
                else{
                    showToast("网络未连接，请连接后重试");
                }


                break;
            case R.id.register_getcode:
                if (mCodeSendTime!=-1 && (SystemClock.uptimeMillis() - mCodeSendTime) < CodeSendMinTime){
                    showToast("验证码已发送，60秒内请不要重复发送验证码");
                }else if(register_number.getText().toString().length()!=11)
                    showToast("手机号码位数不对");
                else if(NetManager.isConnect(registerActivity.this)) {

                    //TODO consider the number is exist

                    SMSSDK.getVerificationCode("86",register_number.getText().toString());
                    mCodeSendTime = SystemClock.uptimeMillis();
                }
                else{
                    showToast("网络未连接，请连接后重试");
                }
        }
    }

    @Override
    protected void onDestroy(){
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    @Override
    protected boolean uiHandlerCallback(Message msg) {

        switch(msg.what){
            case EVENT_REGISTER:

                //TODO register
                break;
            default:
                break;
        }

        return super.uiHandlerCallback(msg);
    }

    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println(data);

                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    System.out.println(data);
                    if(NetManager.isConnect(registerActivity.this))
                    {
                        //
                        params=new HashMap<String, String>();

                        //TODO add params


                        try {
                            HttpResponse httpResponse = NetManager.doPost(NetManager.baseIP + NetManager.NET_INTERFACE_GETPASSWORD, params);

                            if(httpResponse.getStatusLine().getStatusCode() == 200){
                                String httpresult = EntityUtils.toString(httpResponse.getEntity());

                                if(httpresult.equals(""))
                                    System.out.println("反馈失败");

                                Message msg = getUiHanler().obtainMessage(EVENT_REGISTER,httpresult);
                                getUiHanler().sendMessage(msg);

                                //TODO handle result


                            }

                        }catch (IOException e){
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        showToastOnUiThread("网络连接失败");
                    }
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){


                    showToastOnUiThread("验证码已发送至手机");
                }
            }else{

                //失败
                Throwable ta= (Throwable)data;
                String ss= ta.getMessage();
                try {
                    JSONObject object=new JSONObject(ss);

                    String des = object.optString("detail");//错误描述
                    int status = object.optInt("status");//错误代码
                    if (status > 0 && !TextUtils.isEmpty(des)) {
                        showToast(des);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

                ((Throwable)data).printStackTrace();
            }
        }
    };

}
