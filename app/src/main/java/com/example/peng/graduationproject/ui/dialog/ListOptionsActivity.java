package com.example.peng.graduationproject.ui.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;

import java.util.ArrayList;

/**
 * Created by peng on 2016/4/23.
 */
public class ListOptionsActivity extends BaseActivity{

    private ArrayList<String> options;

    private ArrayAdapter adapter;

    private ListView optionsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_options_list);

        getBtn_back().setVisibility(View.VISIBLE);

        initView();
        setDefaultValues();
        bindEvents();
    }

    @Override
    protected void initView() {
        optionsList = (ListView)findViewById(R.id.listview);

    }

    @Override
    protected void setDefaultValues() {

        Intent intent = getIntent();                       //intent 包含options，title
        options = intent.getStringArrayListExtra("options");
        setTitleText(intent.getStringExtra("title"));

        adapter = new ArrayAdapter(this, R.layout.item_options, R.id.options_item, options);
        optionsList.setAdapter(adapter);

    }

    @Override
    protected void bindEvents() {

        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent result = new Intent();
                result.putExtra("choice",options.get(position));
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }
}
