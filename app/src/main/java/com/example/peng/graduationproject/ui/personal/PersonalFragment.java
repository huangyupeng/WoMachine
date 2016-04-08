package com.example.peng.graduationproject.ui.personal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseFragment;

/**
 * Created by peng on 2016/4/3.
 */
public class PersonalFragment extends BaseFragment{

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

    }

    @Override
    protected void setDefaultValues() {

    }

    @Override
    protected void bindEvents() {

    }
}
