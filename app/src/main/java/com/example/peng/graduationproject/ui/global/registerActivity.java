package com.example.peng.graduationproject.ui.global;

/**
 * Created by cuinaitian on 2016/3/30 0030.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


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


public class registerActivity extends Activity {   //注册activity
    private EditText register_number,register_code,register_name,register_password;
    private Button ask_code,register;
    private TextView back;
    private String ip= IP.ip+"register";
    private TextView title;
    private	Thread t=null;
    private String type;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SMSSDK.initSDK(this,"1119188c18b84", "8d7431c3a5e67a7ced10710309003f91");   //mob短信验证码的启动方法 需要验证用户信息 详情见mob官网的短信验证码模块
        Typeface face = Typeface.createFromAsset (getAssets() , "fonts/Arial.TTF" );

        SMSSDK.registerEventHandler(eh); //注册短信回调
        //SMSSDK.getSupportedCountries();
        register=(Button)findViewById(R.id.register_send);
        ask_code=(Button)findViewById(R.id.register_getcode);
        register_number=(EditText)findViewById(R.id.register_number);
        register_code=(EditText)findViewById(R.id.register_code);
        register_name=(EditText)findViewById(R.id.register_name);
        register_password=(EditText)findViewById(R.id.register_password);
        back=(TextView)findViewById(R.id.back);


        register_number.setTypeface(face);
        register_code.setTypeface(face);
        register_name.setTypeface(face);
        register_password.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SMSSDK.unregisterEventHandler(eh);
                if(t!=null&&t.isAlive())
                    t.interrupt();
                t=null;
                registerActivity.this.finish();
            }
        });
        ask_code.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                System.out.println(register_number.getText().toString());
                if(register_number.getText().toString().length()!=11)
                    Toast.makeText(getApplicationContext(), "手机号码位数不对哦", Toast.LENGTH_SHORT).show();
                else if(isNetworkConnected(getApplicationContext()))
                {

                   /* type="check";
                    new Thread(runnable).start();*/
                    try {
                        JSONObject j=new JSONObject();
                        j.put("code",100);
                        Message msg=new Message();    //建个message包含服务器的反馈 发送给handler在handler里处理
                        msg.setTarget(h);
                        msg.what=2;
                        msg.obj=j.toString();
                        msg.sendToTarget();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else Toast.makeText(getApplicationContext(), "网络未连接，请连接后重试", Toast.LENGTH_SHORT).show();
            }
        });
        register.setOnClickListener(ok);
    }
    OnClickListener ok=new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if(register_number.getText().toString().length()!=11)
                Toast.makeText(getApplicationContext(), "手机号码位数不对哦", Toast.LENGTH_SHORT).show();
            else if(register_password.getText().toString().length()<6)
                Toast.makeText(getApplicationContext(), "密码位数要大于6位", Toast.LENGTH_SHORT).show();
            else if(register_name.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "名字不能为空", Toast.LENGTH_SHORT).show();

            else if(isNetworkConnected(getApplicationContext()))
                SMSSDK.submitVerificationCode("86", register_number.getText().toString(), register_code.getText().toString());

            else Toast.makeText(getApplicationContext(), "网络未连接，请连接后重试", Toast.LENGTH_SHORT).show();
        }
    };
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
                    Toast.makeText(getApplicationContext(), "网络未连接，请连接后重试", Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==2){
                try {
                    JSONObject jo=new JSONObject(msg.obj.toString());
                    if(jo.getString("code").equals("100"))
                    {
                        SMSSDK.getVerificationCode("86",register_number.getText().toString());
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "手机号已经注册,请换一个", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

    };
    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println(data);

                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    System.out.println(data);
                    if(isNetworkConnected(getApplicationContext()))
                    {
                        if(t!=null&&t.isAlive())
                            t.destroy();
                        type="register";
                        System.out.println("registering~");
                        t= new Thread(runnable);
                        t.start();
                    }
                    else
                    {
                        Message msg=new Message();
                        msg.what=1;
                        msg.obj="NOT_ONLINE";
                        msg.setTarget(h);
                        msg.sendToTarget();
                    }
                    //提交验证码成功
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    Message msg=new Message();
                    msg.what=1;
                    msg.obj="EVENT_GET_VERIFICATION_CODE";
                    msg.setTarget(h);
                    msg.sendToTarget();
                    //获取验证码成功
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表

                /*	ArrayList<HashMap<String,Object>> ob=(ArrayList<HashMap<String,Object>>)data;
                	//System.out.println(ob.get(0).toString());
                	System.out.println("收到列表");
                	for(int i=0;i<ob.size();i++)
                	{
                		System.out.print(i+" ");
                		HashMap<String, Object> hp=ob.get(i);
                		System.out.println(hp.toString());
                	}*/
                }
            }else{

                Throwable ta= (Throwable)data;
                String ss= ta.getMessage();
                System.out.println("ss="+ss);
                try {
                    JSONObject jo=new JSONObject(ss);
                    if(jo.getInt("status")==520||jo.getInt("status")==468)
                    {
                        Message msg=new Message();
                        msg.what=1;
                        msg.obj="VERIFICATION_FALSE";
                        msg.setTarget(h);
                        msg.sendToTarget();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                ((Throwable)data).printStackTrace();
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
                    post.setEntity(new UrlEncodedFormEntity(paramList,HTTP.UTF_8));
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
    };
    public boolean isNetworkConnected(Context context) {  //判断网络是否连接
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            SMSSDK.unregisterEventHandler(eh);
            if(t!=null&&t.isAlive())
                t.interrupt();
            t=null;
            registerActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    protected void OnDestory()
    {

        if(t!=null&&t.isAlive())
            t.interrupt();
        t=null;
        super.onDestroy();
    }
}
