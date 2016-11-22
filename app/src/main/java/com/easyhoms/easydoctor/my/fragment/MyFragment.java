package com.easyhoms.easydoctor.my.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.fragment.BaseFragment;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.my.activity.password.UpdatePasswordFirstActivity;
import com.easyhoms.easydoctor.my.activity.personal.MyDataActivity;
import com.easyhoms.easydoctor.my.activity.phone.UpdatePhoneFirstActivity;
import com.easyhoms.easydoctor.my.activity.setting.SettingActivity;
import com.netease.nim.common.ui.imageview.HeadImageView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;


@ContentView(R.layout.fragment_my)
public class MyFragment extends BaseFragment {

    @BindView(R.id.my_setting_tv)
    RelativeLayout mMySettingTv;
    @BindView(R.id.head_hiv)
    HeadImageView mHeadHiv;
    @BindView(R.id.mobile_tv)
    TextView mMobileTv;
    @BindView(R.id.update_mobile_ll)
    LinearLayout mUpdateMobileLl;
    @BindView(R.id.update_password_ll)
    LinearLayout mUpdatePasswordLl;

    StringBuilder mMobile;

    public MyFragment() {
    }

    @Override
    protected void initView() {
        mMobile= new StringBuilder(UserManager.getUser().mobile);
        mMobileTv.setText(mMobile.replace(3,7,"XXXX"));
    }

    @Override
    protected void initListener() {

    }

    @Event(R.id.my_setting_tv)
    private void setting(View view) {
        Intent intent = new Intent(mContext, SettingActivity.class);
        startActivity(intent);
    }

    @Event(R.id.head_hiv)
    private void startMyDataActivity(View view) {
        Intent intent = new Intent(mContext, MyDataActivity.class);
        startActivity(intent);
    }

    @Event(R.id.update_mobile_ll)
    private void updatePhone(View view) {
        Intent intent=new Intent(mContext, UpdatePhoneFirstActivity.class);
        startActivity(intent);
    }

    @Event(R.id.update_password_ll)
    private void updatePassword(View view) {
        Intent intent=new Intent(mContext, UpdatePasswordFirstActivity.class);
        startActivity(intent);
    }

}