package com.example.peng.graduationproject.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseFragment;
import com.example.peng.graduationproject.model.Order;
import com.example.peng.graduationproject.ui.adapter.OrderListAdapter;
import com.example.peng.graduationproject.ui.global.HomeActivity;

import java.util.ArrayList;

/**
 * Created by peng on 2016/4/2.
 */
public class OrderFragment extends BaseFragment{

    private final int EVENT_REFRESH = 11;

    private final int EVENT_UI_REFRESH = 12;

    private SwipeRefreshLayout swipe_ly;
    private ListView order_list;

    private ImageView btn_add;

    private OrderListAdapter adapter;

    private ArrayList<Order> datalist;


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

        swipe_ly = (SwipeRefreshLayout)view.findViewById(R.id.swipe_ly);
        order_list = (ListView)view.findViewById(R.id.order_list);

        btn_add = ((HomeActivity)getActivity()).getBtn_add();


    }

    @Override
    protected void setDefaultValues() {

        datalist = new ArrayList<Order>();

        adapter = new OrderListAdapter(getActivity(),datalist);
        order_list.setAdapter(adapter);

    }

    @Override
    protected void bindEvents() {

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderAddActivity.class);
                startActivity(intent);
            }
        });

        swipe_ly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProcHandler().sendEmptyMessage(EVENT_REFRESH);

            }
        });

        order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    startActivity(intent);
                }
        });

    }

    @Override
    protected boolean procHandlerCallBack(Message msg) {

        switch (msg.what){
            case EVENT_REFRESH:
                //TODO get data from server

                getUiHanler().sendEmptyMessage(EVENT_UI_REFRESH);

                //TODO write to database  // is this neccessary?
                break;
        }

        return super.procHandlerCallBack(msg);
    }

    @Override
    protected boolean uiHandlerCallback(Message msg) {
        switch (msg.what){
            case EVENT_UI_REFRESH:
                swipe_ly.setRefreshing(false);
                adapter.notifyDataSetChanged();
                break;
        }

        return super.uiHandlerCallback(msg);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
