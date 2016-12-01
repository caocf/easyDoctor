package com.easyhoms.easydoctor.team.activity.user;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.response.BaseResp;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.utils.ToastUtils;
import com.easyhoms.easydoctor.common.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.lang.reflect.Type;

@ContentView(R.layout.activity_user_more)
public class UserMoreActivity extends BaseActivity {

    @BindView(R.id.user_more_remark)
    private MenuItem mMoreRemark;
    @BindView(R.id.user_more_switch)
    private ImageView mMoreSwitch;

    private String mYxId;

    private static final int REMARK_CODE = 100;

    private boolean isFavorite ;

    private NetCallback mStatusCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            if (CommonUtils.isResultOK(result)){
                Type objectType = new TypeToken<BaseResp<Boolean>>() {}.getType();
                BaseResp<Boolean> res = new Gson().fromJson(result, objectType);
                isFavorite = res.content;
                mMoreSwitch.setSelected(isFavorite);
            }else {
                ToastUtils.showToast(mContext,CommonUtils.getMsg(result));
            }
        }

        @Override
        protected void timeOut() {

        }
    };

    private NetCallback mChangeCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            if (CommonUtils.isResultOK(result)){
                isFavorite = !isFavorite;
                mMoreSwitch.setSelected(isFavorite);
                if (isFavorite){
                    ToastUtils.showToast(mContext,getString(R.string.store_user_ok));
                }else {
                    ToastUtils.showToast(mContext,getString(R.string.cancel_store_user_ok));
                }
            }else {
                ToastUtils.showToast(mContext,CommonUtils.getMsg(result));
            }
        }

        @Override
        protected void timeOut() {

        }
    };

    @Override
    protected void initView() {
        mYxId = getIntent().getStringExtra("yxId");
        mMoreSwitch.setSelected(isFavorite);
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Event(R.id.user_more_remark)
    private void setRemark(View view){
        if (isFavorite){
            Intent intent = new Intent(UserMoreActivity.this,UserNoteActivity.class);
            intent.putExtra("yxId",mYxId);
            startActivityForResult(intent,REMARK_CODE);
        }else {
            ToastUtils.showToast(mContext,getString(R.string.no_collection_toast));
        }
    }

    @Event(R.id.user_more_switch)
    private void setFavoriteStatus(View view){
        if (isFavorite){
            BaseManager.cancelFavorite(mYxId, mChangeCallback);
        }else {
            BaseManager.addFavorite(mYxId, mChangeCallback);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseManager.getFavoriteStatus(mYxId,mStatusCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            switch (requestCode){
                case REMARK_CODE:
                    mMoreRemark.setRightText(data.getStringExtra("remark"));
                    break;
            }
        }
    }
}
