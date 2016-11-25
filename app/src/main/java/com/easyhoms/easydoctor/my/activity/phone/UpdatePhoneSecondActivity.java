package com.easyhoms.easydoctor.my.activity.phone;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.LogoutHelper;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.AppManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.DescribeEditView;
import com.easyhoms.easydoctor.login.activity.LoginActivity;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;



/**
 * Created by 德医互联 on 2016/8/22.
 */
@ContentView(R.layout.activity_update_phone_second)
public class UpdatePhoneSecondActivity extends BaseActivity implements DescribeEditView.SendAuthCallback {
    @BindView(R.id.phone_remind_tv)
    private TextView mRemingTv;
    @BindView(R.id.my_phone_auth_dev)
    private DescribeEditView mPhoneAuthDev;
    @BindView(R.id.my_phone_auth_sure_tv)
    private TextView mSureTv;
    private String mPhoneNumber;

    //发送短信的回掉
    private NetCallback mGetAuthCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {

            //验证码发送成功  更新时间
            if (CommonUtils.isResultOK(result)) {
                mPhoneAuthDev.getContentEt().requestFocus();
            }else{
                showToast(CommonUtils.getMsg(result));
                //发送验证码失败  重置
                mPhoneAuthDev.resetAuth();
            }
        }

        @Override
        protected void timeOut() {

        }
    };

    //确认更换手机号的回调
    private NetCallback mSureCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            if (CommonUtils.isResultOK(result)) {
                showToast(R.string.success);
                LogoutHelper.logout();
                UserManager.deleteUser();
                startActivity(new Intent(mContext, LoginActivity.class));
                AppManager.getAppManager().finishAllActivity();
            }else{
                showToast(CommonUtils.getMsg(result));

            }
        }

        @Override
        protected void timeOut() {

        }
    };



    @Override
    protected void initView() {
        mPhoneAuthDev.setAuthCallback(this);
        mPhoneNumber = getIntent().getStringExtra(Constants.KEY_PHONE);
    }


    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void clickAuthOk() {
        mRemingTv.setText("请输入" + mPhoneNumber +"手机收到的短信验证");
        mRemingTv.setVisibility(View.VISIBLE);
        mPhoneAuthDev.updateAuthTime();
        BaseManager.changePhoneSendSms(mPhoneNumber,mGetAuthCallback);
    }

    @Override
    public String getPhone() {
        return mPhoneNumber;
    }

    @Event(R.id.my_phone_auth_sure_tv)
    private void makesure(View view){
        String auth = mPhoneAuthDev.getContent();
        if (TextUtils.isEmpty(auth)){
            showToast(R.string.auth_empty);
            return;
        }
        if (auth.length()!=6) {
            showToast(R.string.auth_error);
            return;
        }
     //   BaseManager.changePhone(mPhoneNumber,auth,mSureCallback);


    }


}
