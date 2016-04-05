package com.example.peng.graduationproject.ui.global;

/**
 * Created by cuinaitian on 2016/3/30 0030.
 */
import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;


public class getPasswordActivity extends BaseActivity {   //重置密码的activity 用Mob的短信验证码验证
    private Thread t=null;                               //关于mob的验证码服务 详情见mob官网的文档
    private EditText get_edit;
    private Button send;
    private TextView back;
    private Button getcode;
    private EditText new_password;
    private EditText code;
    private TextView title;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getpassword);
        SMSSDK.initSDK(this,"1119188c18b84", "8d7431c3a5e67a7ced10710309003f91"); //短信验证码的校验信息
        Typeface face = Typeface.createFromAsset (getAssets() , "fonts/Arial.TTF" );

        get_edit=(EditText)findViewById(R.id.getpd_name);
        send=(Button)findViewById(R.id.get_send);
        getcode=(Button)findViewById(R.id.getpd_getcode);
        code=(EditText)findViewById(R.id.getpd_code);
        new_password=(EditText)findViewById(R.id.getpd_password);
        get_edit.setTypeface(face);
        code.setTypeface(face);
        new_password.setTypeface(face);
        back=(TextView)findViewById(R.id.back);
        back.setOnClickListener(onclick);
        send.setOnClickListener(onclick);
        getcode.setOnClickListener(onclick);
        SMSSDK.registerEventHandler(eh);
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

    OnClickListener onclick=new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            switch(arg0.getId()){
                case R.id.back:
                    SMSSDK.unregisterEventHandler(eh);
                    if(t!=null&&t.isAlive())
                        t.destroy();
                    getPasswordActivity.this.finish();
                    break;
                case R.id.get_send:

                    if(get_edit.getText().toString().length()!=11)
                        Toast.makeText(getApplicationContext(), "手机号码位数不对哦", Toast.LENGTH_SHORT).show();
                    else if(new_password.getText().toString().length()<6)
                        Toast.makeText(getApplicationContext(), "密码位数要大于6位", Toast.LENGTH_SHORT).show();
                    else if((code.getText().toString().equals("")))
                        Toast.makeText(getApplicationContext(), "未输入验证码", Toast.LENGTH_SHORT).show();
                    else if(Network.isNetworkConnected(getApplicationContext()))
                        SMSSDK.submitVerificationCode("86", get_edit.getText().toString(), code.getText().toString());

                    else Toast.makeText(getApplicationContext(), "网络未连接，请连接后重试", Toast.LENGTH_SHORT).show();


                    break;
                case R.id.getpd_getcode:
                    System.out.println(get_edit.getText().toString());
                    if(get_edit.getText().toString().length()!=11)
                        Toast.makeText(getApplicationContext(), "手机号码位数不对哦", Toast.LENGTH_SHORT).show();
                    else if(Network.isNetworkConnected(getApplicationContext()))
                    {SMSSDK.getVerificationCode("86",get_edit.getText().toString());}
                    else Toast.makeText(getApplicationContext(), "网络未连接，请连接后重试", Toast.LENGTH_SHORT).show();


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
                    if(Network.isNetworkConnected(getApplicationContext()))
                    {
                        if(t!=null&&t.isAlive())
                            t.destroy();
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
    };
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            SMSSDK.unregisterEventHandler(eh);
            if(t!=null&&t.isAlive())
                t.interrupt();
            t=null;
            getPasswordActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}