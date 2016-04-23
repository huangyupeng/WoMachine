package com.example.peng.graduationproject.ui.global;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;
import com.example.peng.graduationproject.ui.adapter.HomeFragmentAdapter;
import com.example.peng.graduationproject.ui.discover.DiscoverFragment;
import com.example.peng.graduationproject.ui.message.MessageFragment;
import com.example.peng.graduationproject.ui.order.OrderFragment;
import com.example.peng.graduationproject.ui.personal.PersonalFragment;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    public boolean sync_succeed = false;

    RadioGroup tabRg;

    ViewPager fragmentContaioner;
    private HomeFragmentAdapter adapter;
    ArrayList<Fragment> list;

    Fragment mOrder;
    Fragment mDiscover;
    Fragment mMessage;
    Fragment mSetting;

    private int currentIndex; //貌似没有什么卵用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_home);

        initView();
        setDefaultValues();
        bindEvents();

        sync_succeed = syncData();
    }

    @Override
    protected void initView(){
        tabRg = (RadioGroup)findViewById(R.id.tabs_group);
        fragmentContaioner = (ViewPager)findViewById(R.id.fragment_contaioner);

        adapter = new HomeFragmentAdapter(this.getSupportFragmentManager());
        list = new ArrayList<>();

        mOrder = new OrderFragment();
        mDiscover =  new DiscoverFragment();
        mMessage = new MessageFragment();
        mSetting = new PersonalFragment();

        list.add(mOrder);
        list.add(mDiscover);
        list.add(mMessage);
        list.add(mSetting);

        adapter.setFragmentList(list);
        fragmentContaioner.setAdapter(adapter);

    }

    @Override
    protected void setDefaultValues(){
        currentIndex = 0;
        tabRg.check(R.id.tab_order);
        setTitleText(R.string.order);
        getBtn_add().setVisibility(View.VISIBLE);
    }

    @Override
    protected void bindEvents(){

        tabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId){
                    case R.id.tab_order:
                        fragmentContaioner.setCurrentItem(0);
                        setTitleText(R.string.order);
                        currentIndex=0;
                        getBtn_add().setVisibility(View.VISIBLE);
                        break;
                    case R.id.tab_discover:
                        fragmentContaioner.setCurrentItem(1);
                        setTitleText(R.string.discover);
                        currentIndex=1;
                        getBtn_add().setVisibility(View.INVISIBLE);
                        break;
                    case R.id.tab_message:
                        fragmentContaioner.setCurrentItem(2);
                        setTitleText(R.string.message);
                        currentIndex=2;
                        getBtn_add().setVisibility(View.INVISIBLE);
                        break;
                    case R.id.tab_setting:
                        fragmentContaioner.setCurrentItem(3);
                        setTitleText(R.string.setting);
                        currentIndex=3;
                        getBtn_add().setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }

            }
        });


        fragmentContaioner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabRg.check(R.id.tab_order);
                        currentIndex = 0;
                        setTitleText(R.string.order);
                        getBtn_add().setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tabRg.check(R.id.tab_discover);
                        currentIndex = 1;
                        setTitleText(R.string.discover);
                        getBtn_add().setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        tabRg.check(R.id.tab_message);
                        currentIndex = 2;
                        setTitleText(R.string.message);
                        getBtn_add().setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        tabRg.check(R.id.tab_setting);
                        currentIndex = 3;
                        setTitleText(R.string.setting);
                        getBtn_add().setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private boolean syncData(){

        return true;
    }

}
