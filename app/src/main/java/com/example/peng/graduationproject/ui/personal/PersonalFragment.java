package com.example.peng.graduationproject.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseFragment;
import com.example.peng.graduationproject.model.DatabaseHelper;
import com.example.peng.graduationproject.model.UserInfoManager;
import com.example.peng.graduationproject.ui.global.loginActivity;
import com.example.peng.graduationproject.ui.view.RoundImageView;

import org.w3c.dom.Text;

/**
 * Created by peng on 2016/4/3.
 */
public class PersonalFragment extends BaseFragment implements View.OnClickListener{

    private SwipeRefreshLayout swipe_layout;

    private RoundImageView face;
    private TextView name;
    private LinearLayout personal_info;
    private Button add_farmland;

    private RelativeLayout advice;
    private RelativeLayout update;
    private RelativeLayout about;
    private Button signout;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        initView(view);
        setDefaultValues();
        bindEvents();
        return view;
    }

    @Override
    protected void initView(View view){

        face =(RoundImageView)view.findViewById(R.id.face);
        name = (TextView)view.findViewById(R.id.name);
        personal_info = (LinearLayout)view.findViewById(R.id.personal_info);
        add_farmland = (Button)view.findViewById(R.id.add_farmland);
        advice = (RelativeLayout)view.findViewById(R.id.advice);
        update = (RelativeLayout)view.findViewById(R.id.update);
        about = (RelativeLayout)view.findViewById(R.id.about);
        signout = (Button)view.findViewById(R.id.signout);
        swipe_layout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_layout);

    }

    @Override
    protected void setDefaultValues() {
        if (UserInfoManager.getCurrentUser() == null){
            showToast("连接失败，请尝试重新登录");
            return ;
        }else{
            name.setText(UserInfoManager.getCurrentUser().getName());
        }

    }

    @Override
    protected void bindEvents() {

        add_farmland.setOnClickListener(this);
        advice.setOnClickListener(this);
        update.setOnClickListener(this);
        about.setOnClickListener(this);
        signout.setOnClickListener(this);

        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){


            case R.id.add_farmland:
                intent = new Intent(getActivity(), FarmlandEditActivity.class);
                startActivity(intent);
                break;
            case R.id.advice:
                intent = new Intent(getActivity(), AdviceActivity.class);
                startActivity(intent);
                break;
            case R.id.update:
                if (showUpdateConfirmDialog()){
                    showUpdateDownloadProgress();
                }

                break;
            case R.id.about:
                intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.signout:
                intent = new Intent(getActivity(), loginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    private boolean showUpdateConfirmDialog(){
        return false;
    }

    private boolean showUpdateDownloadProgress(){
        return true;
    }


}
