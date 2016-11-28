package com.easyhoms.easydoctor.my.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.bean.User;
import com.easyhoms.easydoctor.common.fragment.BaseFragment;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.response.BaseResp;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.MenuItem;
import com.easyhoms.easydoctor.my.activity.chooseHospital.MyChooseHospitalActivity;
import com.easyhoms.easydoctor.my.activity.password.UpdatePasswordFirstActivity;
import com.easyhoms.easydoctor.my.activity.personal.MyDataActivity;
import com.easyhoms.easydoctor.my.activity.phone.UpdatePhoneFirstActivity;
import com.easyhoms.easydoctor.my.activity.setting.SettingActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.common.ui.imageview.HeadImageView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.lang.reflect.Type;


@ContentView(R.layout.fragment_my)
public class MyFragment extends BaseFragment {

    @BindView(R.id.my_setting_tv)
    MenuItem mMySettingTv;
    @BindView(R.id.update_mobile_ll)
    MenuItem mUpdateMobileLl;
    @BindView(R.id.update_password_ll)
    MenuItem mUpdatePasswordLl;
    @BindView(R.id.unbind_tv)
    TextView mUnBindTv;
    @BindView(R.id.head_hiv)
    HeadImageView mHeadHiv;

    @BindView(R.id.apply_approve_img)
    ImageView mApplyApproveImg;
    @BindView(R.id.doctor_name_tv)
    TextView mDoctorNameTv;
    @BindView(R.id.doctor_job_tv)
    TextView mDoctorJobTv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.icon_iv)
    ImageView mIconIv;
    @BindView(R.id.hospital_name_tv)
    TextView mHospitalNameTv;
    @BindView(R.id.hospita_describe_tv)
    TextView mHospitaDescribeTv;
    @BindView(R.id.menu_title_tv)
    TextView mMenuTitleTv;
    @BindView(R.id.next_img)
    ImageView mNextImg;

    StringBuilder mMobile;
    private User mUser;
    private int mAuthState=4;

    private NetCallback mCallback = new NetCallback(getActivity()) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseResp<Integer>>() {
                }.getType();
                BaseResp<Integer> res = new Gson().fromJson(result, objectType);
                mAuthState=res.content;
                switch (mAuthState){
                    case Constants.AUTH_UNAUTHED:
                       mApplyApproveImg.setImageResource(R.drawable.icon_apply_approve);
                        break;
                    case Constants.AUTH_AUTHING:
                       mApplyApproveImg.setImageResource(R.drawable.icon_approving);
                        break;
                    case Constants.AUTH_FAIL:
                       mApplyApproveImg.setImageResource(R.drawable.icon_approve_fail);
                        break;
                    case Constants.AUTH_SUCCESS:
                       mApplyApproveImg.setImageResource(R.drawable.icon_approve_ok);
                        break;
                }

                UserManager.setUser("auth",mAuthState+"");
            } else {

            }
        }
        @Override
        protected void timeOut() {

        }
    };
    public MyFragment() {
    }

    @Override
    protected void initView() {

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
        Intent intent = new Intent(mContext, UpdatePhoneFirstActivity.class);
        startActivity(intent);
    }

    @Event(R.id.update_password_ll)
    private void updatePassword(View view) {
        Intent intent = new Intent(mContext, UpdatePasswordFirstActivity.class);
        startActivity(intent);
    }

    @Event(R.id.unbind_tv)
    private void toBindHospital(View view) {
        Intent intent = new Intent(mContext, MyChooseHospitalActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUser=UserManager.getUser();

        mDoctorNameTv.setText(mUser.name);
        mDoctorJobTv.setText("职称??");
        CommonUtils.loadImg(Constants.HOST_HEAD+"/"+mUser.imagePath,mHeadHiv);

        //获取认证信息
        BaseManager.getAuthStatus(mCallback);
    }
}