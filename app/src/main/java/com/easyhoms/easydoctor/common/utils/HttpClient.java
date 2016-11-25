package com.easyhoms.easydoctor.common.utils;


import android.content.Intent;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.login.activity.LoginActivity;
import com.netease.nim.DemoCache;

import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/3/2.
 */
public  class HttpClient {
    public static void post(RequestParams params,NetCallback callback){
        log("post",params);
        if (NetworkUtil.isNetAvailable(DemoCache.getContext())) {
            x.http().post(params, callback);
        }else{
            callback.networkErrorToGuide();
            CommonUtils.showToast(R.string.net_error);
        }
    }

    private static void log(String method,RequestParams params) {
        LogUtils.i(params.getUri());
        LogUtils.i("method:"+method);
        List<RequestParams.Header> headParams=params.getHeaders();
        for (KeyValue bodyParam : headParams) {
            LogUtils.i(bodyParam.key + "  " + bodyParam.value);
        }

        List<KeyValue> bodyParams=params.getBodyParams();
        for (KeyValue bodyParam : bodyParams) {
            LogUtils.i(bodyParam.key + "  " + bodyParam.value);
        }

        List<KeyValue> fileParams=params.getFileParams();
        for (KeyValue bodyParam : fileParams) {
            LogUtils.i(bodyParam.key+"   "+bodyParam.value);
        }
        List<KeyValue> queryParams=params.getQueryStringParams();
        for (KeyValue bodyParam : queryParams) {
            LogUtils.i(bodyParam.key+"   "+bodyParam.value);
        }
    }

    public static void get(RequestParams params,NetCallback callback){
        log("get",params);
        if (NetworkUtil.isNetAvailable(DemoCache.getContext())) {
            x.http().get(params, callback);
        }else{
            CommonUtils.showToast(R.string.net_error);
            callback.getContext().startActivity(new Intent(callback.getContext(), LoginActivity.class));
        }

    }
}
