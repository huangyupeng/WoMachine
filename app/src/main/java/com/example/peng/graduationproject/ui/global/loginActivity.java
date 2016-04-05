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
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
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

                    //TODO login


                    try {
                        HttpResponse httpResponse = NetManager.doPost(NetManager.URL, params);

                        if(httpResponse.getStatusLine().getStatusCode() == 200){
                            String result = EntityUtils.toString(httpResponse.getEntity());

                            if(result.equals(""))
                                System.out.println("反馈失败");

                            //TODO handle result


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

}