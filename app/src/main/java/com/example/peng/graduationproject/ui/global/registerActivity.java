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

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class registerActivity extends BaseActivity implements OnClickListener{   //注册activity
    private EditText register_number,register_code,register_name,register_password;
    private Button register_getcode,register;


    private long mCodeSendTime;
    private final long CodeSendMinTime = 60000;

    private String type;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_register);

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

                    }
                    else
                    {
                        //网络连接失败
                    }
                    //提交验证码成功
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){


                    //获取验证码成功
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

/*
     Handler h=new Handler()
    {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0)
            {
                System.out.println(msg.obj.toString());
                JSONObject jo;
                try {
                    jo = new JSONObject(msg.obj.toString());
                    String code=jo.get("code").toString();
                    if(code.equals("200"))
                    {
                        Toast.makeText(getApplicationContext(), "手机号码已经注册,请换一个", Toast.LENGTH_SHORT).show();
                    }
                    else if(code.equals("100")){
                        Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                        registerActivity.this.finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "服务器出问题了~ 你可以重试一下", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

                //Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
                //logRegisterActivity.this.finish();
            }
            else if(msg.what==1)
            {
                if(msg.obj.equals("EVENT_GET_VERIFICATION_CODE"))
                    Toast.makeText(getApplicationContext(), "验证码已发送 有效期是两分钟,请稍等", Toast.LENGTH_SHORT).show();
                else if(msg.obj.equals("VERIFICATION_FALSE"))
                    Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                else if(msg.obj.equals("ONT_ONLINE"))
                    Toast.makeText(getApplicationContext(), "网络未连接，请连接后重试", Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==2){
                try {
                    JSONObject jo=new JSONObject(msg.obj.toString());
                    if(jo.getString("code").equals("100"))
                    {
                        SMSSDK.getVerificationCode("86", register_number.getText().toString());
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "手机号已经注册,请换一个", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }

    };


    Runnable runnable=new Runnable() {

        @Override
        public void run() {

            try {
                //File f=new File(path);
                HttpClient client=new DefaultHttpClient();
                HttpPost post=null;
                List<NameValuePair> paramList = new ArrayList<NameValuePair>(); ;
                if(type.equals("register"))
                {
                    post=new HttpPost(ip);
                    BasicNameValuePair param =new BasicNameValuePair("phonenumber",register_number.getText().toString());
                    paramList.add(param);
                    param = new BasicNameValuePair("password",register_password.getText().toString());
                    paramList.add(param);
                    param = new BasicNameValuePair("name", register_name.getText().toString());
                    paramList.add(param);
                    post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
                    HttpResponse response=client.execute(post);
                    if(response.getStatusLine().getStatusCode() == 200){
                        //获取返回结果
                        System.out.println("in handler");
                        String result = EntityUtils.toString(response.getEntity()); //服务器返回的信息，转换成了String

                        if(result.equals(""))
                            System.out.println("反馈失败");
                        System.out.println(result);
                        String res=new String(result.getBytes(), "utf-8");
                        System.out.println(res);
                        Message msg=new Message();    //建个message包含服务器的反馈 发送给handler在handler里处理
                        msg.setTarget(h);
                        msg.what=0;
                        msg.obj=result;
                        msg.sendToTarget();
                    }
                }
                else if(type.equals("check")){
                    HttpGet get=new HttpGet(IP.ip+"?phonenumber="+register_number.getText().toString());
                    HttpResponse response=client.execute(get);
                    if(response.getStatusLine().getStatusCode() == 200){
                        //获取返回结果

                        String result = EntityUtils.toString(response.getEntity()); //服务器返回的信息，转换成了String

                        if(result.equals(""))
                            System.out.println("反馈失败");
                        System.out.println(result);
                        String res=new String(result.getBytes(), "utf-8");
                        System.out.println(res);
                        Message msg=new Message();    //建个message包含服务器的反馈 发送给handler在handler里处理
                        msg.setTarget(h);
                        msg.what=2;
                        msg.obj=result;
                        msg.sendToTarget();
                    }
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };*/

}
