package com.example.peng.graduationproject.ui.global;

/**
 * Created by cuinaitian on 2016/3/30 0030.
 */
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peng.graduationproject.common.NetManager;
import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class getPasswordActivity extends BaseActivity implements OnClickListener{   //重置密码的activity 用Mob的短信验证码验证

    //发送验证码最小时间间隔 60s
    private final long CodeSendMinTime = 60000;

    private EditText get_edit;
    private Button send;
    private Button getcode;
    private EditText new_password;
    private EditText code;

    private long mCodeSendTime;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_getpassword);

        initView();
        setDefaultValues();
        bindEvents();

    }

    @Override
    protected void initView() {
        get_edit=(EditText)findViewById(R.id.getpd_name);
        send=(Button)findViewById(R.id.get_send);
        getcode=(Button)findViewById(R.id.getpd_getcode);
        code=(EditText)findViewById(R.id.getpd_code);
        new_password=(EditText)findViewById(R.id.getpd_password);



    }

    @Override
    protected void setDefaultValues() {

        setTitleText(R.string.getpassword);
        SMSSDK.initSDK(this, "115c1798156e7", "4864103a3c76a4d061449910cce32041");//smssdk的app key和app secret

        mCodeSendTime = -1;

    }

    @Override
    protected void bindEvents() {
        send.setOnClickListener(this);
        getcode.setOnClickListener(this);
        SMSSDK.registerEventHandler(eh);

    }


        @Override
        public void onClick(View arg0) {

            switch(arg0.getId()){
                case R.id.get_send:

                    if(get_edit.getText().toString().length()!=11)
                        showToast("手机号码位数不对");
                    else if(new_password.getText().toString().length()<6)
                        showToast("密码至少需要6位");
                    else if((code.getText().toString().equals("")))
                        showToast("未输入验证码");
                    else if(NetManager.isConnect(getPasswordActivity.this)) {
                        SMSSDK.submitVerificationCode("86", get_edit.getText().toString(), code.getText().toString());
                    }
                    else{
                        showToast("网络未连接，请连接后重试");
                    }


                    break;
                case R.id.getpd_getcode:
                    if (mCodeSendTime!=-1 && (SystemClock.uptimeMillis() - mCodeSendTime) < CodeSendMinTime){
                        showToast("验证码已发送，60秒内请不要重复发送验证码");
                    }else if(get_edit.getText().toString().length()!=11)
                        showToast("手机号码位数不对");
                    else if(NetManager.isConnect(getPasswordActivity.this)) {
                        SMSSDK.getVerificationCode("86",get_edit.getText().toString());
                        mCodeSendTime = SystemClock.uptimeMillis();
                    }
                    else{
                        showToast("网络未连接，请连接后重试");
                    }
            }

        }

    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println(data);

                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    System.out.println(data);
                    if(NetManager.isConnect(getPasswordActivity.this))
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
                    // TODO Auto-generated catch block
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
                        Toast.makeText(getApplicationContext(), "密码更改失败", Toast.LENGTH_SHORT).show();
                    }
                    else if(code.equals("100")){
                        Toast.makeText(getApplicationContext(), "密码更改成功", Toast.LENGTH_SHORT).show();
                        getPasswordActivity.this.finish();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "服务器出问题了~ 你可以重试一下", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
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
                    Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_SHORT).show();
            }
        }

    };
    Runnable runnable=new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                //File f=new File(path);
                HttpClient client=new DefaultHttpClient();
                HttpPost post=new HttpPost(ip);
                //	JSONObject jo=new JSONObject();
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                BasicNameValuePair param =new BasicNameValuePair("user_id", get_edit.getText().toString());
                paramList.add(param);
                param =new BasicNameValuePair("new_password", new_password.getText().toString());
                paramList.add(param);

                post.setEntity(new UrlEncodedFormEntity(paramList,"utf-8"));
                HttpResponse response=client.execute(post);
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

                    msg.what=0;
                    msg.obj=result;
                    msg.sendToTarget();


                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };*/

    @Override
    protected void onDestroy(){
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}