package com.example.peng.graduationproject.ui.order;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseFragment;
import com.example.peng.graduationproject.ui.adapter.OrderListAdapter;

/**
 * Created by peng on 2016/4/2.
 */
public class OrderFragment extends BaseFragment{

    private final int EVENT_REFRESH = 11;

    private SwipeRefreshLayout swipe_ly;
    private ListView order_list;

    private OrderListAdapter adapter;


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

        getProcHandler().obtainMessage(EVENT_REFRESH);
        return view;
    }

    @Override
    protected void initView(View view){

        swipe_ly = (SwipeRefreshLayout)view.findViewById(R.id.swipe_ly);
        order_list = (ListView)view.findViewById(R.id.order_list);

        adapter = new OrderListAdapter(getActivity());
        order_list.setAdapter(adapter);

    }

    @Override
    protected void setDefaultValues() {

    }

    @Override
    protected void bindEvents() {

        swipe_ly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getProcHandler().obtainMessage(EVENT_REFRESH);

            }
        });

    }

    @Override
    protected boolean procHandlerCallBack(Message msg) {

        switch (msg.what){
            case EVENT_REFRESH:
                //TODO get data from server
                //TODO write to database
                break;
        }

        return super.procHandlerCallBack(msg);
    }

    @Override
    protected boolean uiHandlerCallback(Message msg) {
        return super.uiHandlerCallback(msg);
    }
}
