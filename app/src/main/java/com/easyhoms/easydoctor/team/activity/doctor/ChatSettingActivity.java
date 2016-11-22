package com.easyhoms.easydoctor.team.activity.doctor;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.easyhoms.easydoctor.ConstantValues;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.response.BaseResp;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.message.activity.TransforMemberActivity;
import com.easyhoms.easydoctor.my.reponse.UserDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.common.ui.imageview.HeadImageView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.lang.reflect.Type;


@ContentView(R.layout.activity_chat_setting)
public class ChatSettingActivity extends BaseActivity {

    @BindView(R.id.user_head_hiv)
    HeadImageView mHeadHiv;
    @BindView(R.id.user_store_img)
    ImageView mStoreImg;
    @BindView(R.id.transfor_rl)
    RelativeLayout mTransforRl;

    private boolean mIsStored=false;
    private String mUserImId;
    private long mGroupId;
    private String mYxTeamId;
    private UserDetail mUser;
    private NetCallback mCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                mIsStored=!mIsStored;
                mStoreImg.setSelected(mIsStored);
                if(mIsStored){
                    showToast(R.string.store_user_ok);
                }else{
                    showToast(R.string.cancel_store_user_ok);
                }
            } else {

            }
        }
        @Override
        protected void timeOut() {

        }
    };

    private NetCallback mStoreCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseResp<Boolean>>() {
                }.getType();
                BaseResp<Boolean> res = new Gson().fromJson(result, objectType);
                mIsStored=res.content;
                mStoreImg.setSelected(mIsStored);
            } else {

            }
        }
        @Override
        protected void timeOut() {

        }
    };

    @Override
    protected void initView() {
        mUser=getIntent().getParcelableExtra(ConstantValues.KEY_DATA);
        mUserImId=getIntent().getStringExtra(ConstantValues.KEY_USER_ID);
        mYxTeamId=getIntent().getStringExtra(ConstantValues.KEY_YX_TEAM_ID);
        CommonUtils.loadImg(ConstantValues.HOST_HEAD+"/"+mUser.image_path,mHeadHiv);
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        showdialog();
        BaseManager.getFavoriteStatus(mUserImId,mStoreCallback);
    }

    @Event(R.id.user_store_img)
    private void userStore(View view) {
        showdialog();
        if(mIsStored){
            BaseManager.cancelFavorite(mUserImId,mCallback);
        }else{
            BaseManager.addFavorite(mUserImId,mCallback);
        }
    }

    @Event(R.id.transfor_rl)
    private void transforMember(View view) {
        Intent intent=new Intent(mContext, TransforMemberActivity.class);
       // intent.putExtra(ConstantValues.KEY_GROUP_ID,mGroupId);
        intent.putExtra(ConstantValues.KEY_YX_TEAM_ID,mYxTeamId);
        startActivity(intent);
    }

}
