package com.example.peng.graduationproject.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by peng on 2016/4/4.
 */
public class NetManager {
    private static volatile HttpClient httpclient;
    public final static String baseIP = "";
    private static Object monitor = new Object();


    private static volatile HttpURLConnection httpURLConnection;


    public static HttpClient getClient() {
        synchronized (monitor) {
            if (httpclient == null) {
                httpclient = new DefaultHttpClient();
            }
        }
        return httpclient;
    }


    public static HttpResponse doGet(String url) throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet(url);
        return NetManager.getClient().execute(httpGet);
    }

    public static HttpResponse doPost(String url,HashMap<String,String> paraMap) throws ClientProtocolException, IOException{
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // 添加要传递的参数
        if (paraMap != null) {
            Set<HashMap.Entry<String, String>> entrySet = paraMap.entrySet();
            for (HashMap.Entry<String, String> entry : entrySet) {
                params.add(new BasicNameValuePair(entry.getKey(), (String) entry
                        .getValue()));
            }
        }

        // 设置字符集
        final HttpEntity httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
        // 请求httpRequest
        httpPost.setEntity(httpEntity);
        return NetManager.getClient().execute(httpPost);
    }





    public static boolean isConnect(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isWifi(Context context){
        return getNetType(context)==ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取网络连接类型
     * {@link android.net.ConnectivityManager#TYPE_MOBILE},{@link android.net.ConnectivityManager#TYPE_WIFI}
     * @param context
     * @return -1 未连接网络
     */
    public static int getNetType(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getType()==ConnectivityManager.TYPE_MOBILE
                        ||info.getType()==ConnectivityManager.TYPE_WIFI) {
                    return info.getType();
                }
            }
        }
        return -1;
    }


}
