package com.example.peng.graduationproject.common;

import android.os.Environment;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by peng on 2016/4/22.
 */
public class Constants {

    public static final ArrayList<String> FARM_TYPE = new ArrayList<String>(){{add("其他");add("旱地");add("水田");add("坡地");}};

    public static final ArrayList<String> MACHINE_TYPE = new ArrayList<String>(){
        {add("动力机械");add("种植施肥");add("耕整地");add("田间管理");add("收获机械");add("收获后处理");add("农产品加工");
            add("排灌");add("畜牧水产养殖");add("农机配套与配件");add("农田基本建设");add("设施农业设备");add("农业运输");add("其他机械");}
    };

    public static final ArrayList<String> ORDER_STATE = new ArrayList<String>(){
        {add("未接受");add("未支付");add("已支付");add("支付中");add("已撤销");add("已退款");add("无效");}
    };

    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度

    public static String FILE_DIRECTORY = Environment
            .getExternalStorageDirectory().toString()
            + "/womachine";
}
