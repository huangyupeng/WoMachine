package com.example.peng.graduationproject.ui.order;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;
import com.example.peng.graduationproject.common.Constants;
import com.example.peng.graduationproject.common.NetManager;
import com.example.peng.graduationproject.model.UserInfoManager;
import com.example.peng.graduationproject.ui.dialog.ListOptionsActivity;
import com.example.peng.graduationproject.ui.global.HomeActivity;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by peng on 2016/4/19.
 */
public class OrderAddActivity extends BaseActivity implements View.OnClickListener{

    private final int SELECT_ADDRESS_REQUEST_CODE = 1;

    private final int SELECT_FARM_TYPE = 2;

    private final int SELECT_MACHINE_TYPE = 3;

    private TextView txt_confirm;
    private RelativeLayout choose_address;
    private TextView current_address;
    private RelativeLayout farm_type;
    private TextView current_type;
    private EditText edit_square;
    private RelativeLayout machine_type;
    private TextView machine_current_type;
    private RelativeLayout start_time;
    private RelativeLayout end_time;
    private TextView current_start_time;
    private TextView current_end_time;

    private DatePickerDialog datePicker;

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_order_add);
        setTitleText(R.string.item_order_add);
        getBtn_back().setVisibility(View.VISIBLE);

        initView();
        setDefaultValues();
        bindEvents();
    }

    @Override
    protected void initView() {
        txt_confirm = getTxt_right();

        choose_address = (RelativeLayout)findViewById(R.id.choose_address);
        current_address =(TextView)findViewById(R.id.current_address);
        farm_type = (RelativeLayout)findViewById(R.id.farm_type);
        current_type = (TextView)findViewById(R.id.current_type);
        edit_square = (EditText)findViewById(R.id.edit_square);
        machine_type = (RelativeLayout)findViewById(R.id.machine_type);
        machine_current_type = (TextView)findViewById(R.id.machine_current_type);
        start_time = (RelativeLayout)findViewById(R.id.start_time);
        end_time = (RelativeLayout)findViewById(R.id.end_time);
        current_start_time = (TextView)findViewById(R.id.current_start_time);
        current_end_time = (TextView)findViewById(R.id.current_end_time);

    }

    @Override
    protected void setDefaultValues() {
        txt_confirm.setVisibility(View.VISIBLE);
        txt_confirm.setText("确定");

        latitude = 0;
        longitude = 0;




    }

    @Override
    protected void bindEvents() {
        txt_confirm.setOnClickListener(this);

        choose_address.setOnClickListener(this);
        farm_type.setOnClickListener(this);
        machine_type.setOnClickListener(this);
        start_time.setOnClickListener(this);
        end_time.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Calendar c = Calendar.getInstance();
        switch (v.getId()){
            case R.id.txt_right:

                if (current_address.getText().toString().equals("")){
                    showToast("请选择地址");
                }else if (current_type.getText().toString().equals("")){
                    showToast("请选择农田类型");
                }else if (machine_current_type.getText().toString().equals("")){
                    showToast("请选择农机类型");
                }else if (current_start_time.getText().toString().equals("")){
                    showToast("请选择起始时间");
                }else if (current_end_time.getText().toString().equals("")){
                    showToast("请选择结束时间");
                }else{
                    //TODO send message to server
                    try {
                        JSONObject params = new JSONObject("");


                        JSONObject res = NetManager.doPostJson(NetManager.NET_INTERFACE_LOGIN, params);

                        if (res != null){

                            finish();

                        }else{
                            Log.e("myerror", "httpres = null");
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
                break;
            case R.id.choose_address:
                intent = new Intent(OrderAddActivity.this,SelectAdressActivity.class);
                startActivityForResult(intent, SELECT_ADDRESS_REQUEST_CODE);
                break;
            case R.id.farm_type:
                intent = new Intent(OrderAddActivity.this, ListOptionsActivity.class);
                intent.putExtra("title", "选择耕地类型");
                intent.putExtra("options", Constants.FARM_TYPE);
                startActivityForResult(intent, SELECT_FARM_TYPE);
                break;
            case R.id.machine_type:
                intent = new Intent(OrderAddActivity.this, ListOptionsActivity.class);
                intent.putExtra("title", "选择农机类型");
                intent.putExtra("options", Constants.MACHINE_TYPE);
                startActivityForResult(intent, SELECT_MACHINE_TYPE);
                break;
            case R.id.start_time:
                datePicker = new DatePickerDialog(OrderAddActivity.this, AlertDialog.THEME_HOLO_LIGHT, startTimeSetListener,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePicker.show();

                break;
            case R.id.end_time:
                datePicker = new DatePickerDialog(OrderAddActivity.this, AlertDialog.THEME_HOLO_LIGHT, endTimeSetListener,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
                break;
            default:
                break;
        }
    }

    DatePickerDialog.OnDateSetListener startTimeSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String date =year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
            current_start_time.setText(date);
        }
    };

    DatePickerDialog.OnDateSetListener endTimeSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
            current_end_time.setText(date);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case SELECT_ADDRESS_REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    latitude = data.getDoubleArrayExtra("point")[0];
                    longitude = data.getDoubleArrayExtra("point")[1];
                    current_address.setText(data.getStringExtra("address"));
                }
                break;
            case SELECT_FARM_TYPE:
                if (resultCode == RESULT_OK){
                    current_type.setText(data.getStringExtra("choice"));
                }
                break;
            case SELECT_MACHINE_TYPE:
                if (resultCode == RESULT_OK){
                    machine_current_type.setText(data.getStringExtra("choice"));
                }
                break;
        }
    }
}
