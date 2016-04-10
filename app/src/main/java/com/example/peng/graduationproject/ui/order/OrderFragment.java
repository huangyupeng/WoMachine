package com.example.peng.graduationproject.ui.order;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseFragment;

/**
 * Created by peng on 2016/4/2.
 */
public class OrderFragment extends BaseFragment{

    private SwipeRefreshLayout swipe_ly;
    private ListView order_list;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_order, container, false);

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
