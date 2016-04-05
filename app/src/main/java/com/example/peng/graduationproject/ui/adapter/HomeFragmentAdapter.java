package com.example.peng.graduationproject.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by peng on 2016/4/2.
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragmentList;

    public HomeFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void setFragmentList(ArrayList list){
        this.fragmentList = list;
    }
}
