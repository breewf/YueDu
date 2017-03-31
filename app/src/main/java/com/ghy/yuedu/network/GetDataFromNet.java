package com.ghy.yuedu.network;

import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by GHY on 2015/5/18.
 */
public class GetDataFromNet {

    public interface JsonCallBack{
        void getJson(String getJson);
    }

    public static void GetDataFromNet(String url, final JsonCallBack callback){

        HttpUtils httpUtils = new HttpUtils(10000);//设置超时10秒
        httpUtils.configCurrentHttpCacheExpiry(1000 * 10);// 设置缓存10秒，10秒内直接返回上次成功结果
        httpUtils.send(HttpRequest.HttpMethod.GET,url,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {

                Log.i("HttpUtils", "请求数据成功-->>");
                callback.getJson(result.result);
                //保存数据到本地
//                FileUtil.writeDataToFile("Cache", "JsonCache1.txt", result.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("HttpUtils","请求数据失败-->>"+s);
                callback.getJson("requestError-->>"+s);
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
            }
        });
    }
}
