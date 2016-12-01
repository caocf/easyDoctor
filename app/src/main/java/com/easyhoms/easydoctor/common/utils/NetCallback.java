package com.easyhoms.easydoctor.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.easyhoms.easydoctor.BaseApp;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.login.activity.GuideActivity;
import com.netease.nim.common.ui.dialog.DialogMaker;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Administrator on 2016/2/24.
 */
public abstract class NetCallback implements Callback.CommonCallback<String> {
    private Context mContext;
    private boolean mServerErrorToGuide = true;

    public NetCallback(Context context) {
        mContext = context;
    }

    public NetCallback(Context context, boolean turnToGuide) {
        mContext = context;
        mServerErrorToGuide = turnToGuide;
    }

    public Context getContext() {
        return mContext;
    }

    public void networkErrorToGuide() {

    }
    public void serverErrorToGuide() {
        if (mServerErrorToGuide) {
            Intent intent = new Intent(BaseApp.getApp(), GuideActivity.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            BaseApp.getApp().startActivity(intent);
            ((Activity)mContext).finish();
        }
    }

    @Override
    public void onSuccess(String result) {
        LogUtils.i(result);
        //如果是-10004  则直接到登录界面
        try {
            JSONObject object = new JSONObject(result.toString());
            if (object.getInt("code") == -10004||object.getInt("code") == 8006) {
                timeOut();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestOK(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

        // requestError(ex, isOnCallback);
        DialogMaker.dismissProgressDialog();
        CommonUtils.showToast(R.string.operate_later);
        timeOut();
        serverErrorToGuide();

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

    protected abstract void requestOK(String result);

    protected abstract void timeOut();

    //protected abstract void requestError(Throwable ex, boolean isOnCallback);
    protected void requestError(Throwable ex, boolean isOnCallback) {

    }
}
