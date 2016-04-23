package com.example.peng.graduationproject.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by peng on 2016/4/3.
 */
public class OrderListAdapter extends BaseAdapter{

    private final int ITEM_VIEW_COUNT = 2;

    private final int ITEM_TYPE_ADD = 1;
    private final int ITEM_TYPE_NORMAL = 2;

    private Context context;

    private ArrayList<Order> list = new ArrayList<Order>();

    public OrderListAdapter(Context context,ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            ItemHolder holder = null;
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_order, null);
                holder = new ItemHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ItemHolder)convertView.getTag();
            }
            //TODO set item data

            return convertView;
    }

    private class ItemHolder{

        public TextView commitdate;
        public TextView farmname;
        public TextView farm_address;
        public TextView order_state;
        public TextView start_time;
        public TextView end_time;
        public RelativeLayout started;

        public ItemHolder(View base){
            commitdate = (TextView)base.findViewById(R.id.commitdate);
            farmname = (TextView)base.findViewById(R.id.farmname);
            farm_address = (TextView)base.findViewById(R.id.farm_address);
            order_state = (TextView)base.findViewById(R.id.order_state);
            start_time = (TextView)base.findViewById(R.id.start_time);
            end_time = (TextView)base.findViewById(R.id.end_time);
            started = (RelativeLayout)base.findViewById(R.id.started);

        }

    }
}
