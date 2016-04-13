package com.example.peng.graduationproject.ui.global;

/**
 * Created by cuinaitian on 2016/3/30 0030.
 */

import java.io.IOException;
import java.util.HashMap;

import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;
import com.example.peng.graduationproject.common.NetManager;


public class loginActivity extends BaseActivity implements OnClickListener{

    private static final int EVENT_LOGIN = 1;

    private SharedPreferences mPref;

    private String preUserId;
    private String prePassword;

    private Button login;
    private TextView get_password;
    private TextView register;

    private EditText log_name;
    private EditText log_password;

    HashMap<String, String>params;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_login);
        setNoTitle(true);

        initView();
        setDefaultValues();
        bindEvents();


    }

    @Override
    protected void initView(){
        login=(Button)findViewById(R.id.log_login);
        register=(TextView)findViewById(R.id.log_register);
        get_password=(TextView)findViewById(R.id.get_password);
        log_name=(EditText)findViewById(R.id.log_name);
        log_password=(EditText)findViewById(R.id.log_password);
    }

    @Override
    protected void setDefaultValues(){

        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        preUserId = mPref.getString("userid", "");
        prePassword = mPref.getString("userpassword", "");

        log_name.setText(preUserId);
        log_password.setText(prePassword);

    }

    @Override
    protected void bindEvents(){

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        get_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.log_login:
                if(log_name.getText().toString().equals(""))
                {
                    showToast("用户名不能为空");
                }
                else if(log_password.getText().toString().equals(""))
                {
                    showToast("密码不能为空");
                }
                else if(!NetManager.isConnect(this))
                {
                    showToast("网络未连接");
                }
                else
                {
                    params=new HashMap<String, String>();

                    //TODO add login params


                    try {
                        HttpResponse httpResponse = NetManager.doPost(NetManager.baseIP + NetManager.NET_INTERFACE_LOGIN, params);

                        if(httpResponse.getStatusLine().getStatusCode() == 200){


                            getUiHanler().obtainMessage(EVENT_LOGIN,result);

                            //TODO handle result   save name and id


                        }

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.log_register:
                Intent m=new Intent(getApplicationContext(),registerActivity.class);
                startActivity(m);
                break;
            case R.id.get_password:

                Intent i=new Intent(getApplicationContext(),getPasswordActivity.class);
                startActivity(i);
                break;
        }
    }


    @Override
    protected boolean uiHandlerCallback(Message msg) {

        switch(msg.what){
            case EVENT_LOGIN:
                //TODO login callback

                break;
            default:
                break;

        }

        return super.uiHandlerCallback(msg);
    }
}