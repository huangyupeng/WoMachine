package com.example.peng.graduationproject.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;

import org.w3c.dom.Text;

/**
 * Created by peng on 2016/4/19.
 */
public class OrderAddActivity extends BaseActivity implements View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_order_add);
        setTitleText(R.string.item_order_add);

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
        txt_confirm.setText("确定");

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
        switch (v.getId()){
            case R.id.txt_right:
                //TODO complete add order
                break;
            case R.id.choose_address:
                break;
            case R.id.farm_type:
                break;
            case R.id.machine_type:
                break;
            case R.id.start_time:
                break;
            case R.id.end_time:
                break;
            default:
                break;
        }
    }
}
