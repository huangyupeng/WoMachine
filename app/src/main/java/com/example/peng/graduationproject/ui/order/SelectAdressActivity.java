package com.example.peng.graduationproject.ui.order;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;

/**
 * Created by peng on 2016/4/21.
 */
public class SelectAdressActivity extends BaseActivity implements AMap.OnMapClickListener,GeocodeSearch.OnGeocodeSearchListener {

    private MapView mapView;
    private AMap aMap;

    private TextView txtRight;

    private TextView currentAddress;

    private MarkerOptions markerOptions;
    private Marker selectedMarker;
    private LatLng currentpoint;

    private GeocodeSearch geocoderSearch;

    private String txtAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_selectaddress);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        setTitleText("选择作业地址");
        getBtn_back().setVisibility(View.VISIBLE);

        initView();
        setDefaultValues();
        bindEvents();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initView() {

        txtRight = getTxt_right();
        txtRight.setText(R.string.sure);
        txtRight.setVisibility(View.VISIBLE);

        if (aMap == null) {
            aMap = mapView.getMap();
        }
        currentAddress = (TextView)findViewById(R.id.txt_address);

    }

    @Override
    protected void setDefaultValues() {

        currentpoint = null;

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

        selectedMarker = aMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));

    }

    @Override
    protected void bindEvents() {

        txtRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressResult = new Intent();
                if (currentpoint == null){
                    showToast("未选择地点");
                    return;
                }
                addressResult.putExtra("address",txtAddress);
                addressResult.putExtra("point",new double[]{currentpoint.latitude,currentpoint.longitude});
                setResult(RESULT_OK, addressResult);
                finish();
            }
        });
        aMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        currentpoint = latLng;
        selectedMarker.setPosition(currentpoint);
        getAddress(new LatLonPoint(currentpoint.latitude,currentpoint.longitude));

    }

    /**
     * 响应地理编码
     */
    public void getLatlon(final String name) {

        GeocodeQuery query = new GeocodeQuery(name, "010");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {

        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {

        if (rCode == 1000) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);


            } else {
                Log.e("myerror","code: "+rCode);

            }
        } else {
            Log.e("myerror","code: "+rCode);

        }
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                txtAddress = result.getRegeocodeAddress().getFormatAddress();
                currentAddress.setText(txtAddress);

            }else {
                Log.e("myerror","result: "+result);
            }
        }else{
            Log.e("myerror","code: "+rCode);
        }
    }
}
