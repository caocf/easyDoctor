package com.easyhoms.easydoctor.password.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.DescribeEditView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;



/**
 * 忘记密码
 */
@ContentView(R.layout.activity_forget_password)
public class ForgetPasswordActivity extends BaseActivity implements DescribeEditView.SendAuthCallback {
    @BindView(R.id.forget_phone_dev)
    private DescribeEditView mPhoneDev;
    @BindView(R.id.forget_auth_dev)
    private DescribeEditView mAuthDev;
    @BindView(R.id.forget_next_tv)
    private TextView mNextTv;

    //发送验证码的回调函数
    private NetCallback mGetAuthCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            //验证码发送成功  更新时间
            if (CommonUtils.isResultOK(result)) {

                mAuthDev.getContentEt().requestFocus();
            }else{
                showToast(CommonUtils.getMsg(result));
                mAuthDev.resetAuth();
            }
        }

        @Override
        protected void timeOut() {

        }
    };

    //发送确认的回调函数
    private NetCallback mVerfityCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Intent intent = new Intent(mContext, ResetPasswordActivity.class);
                intent.putExtra(Constants.KEY_PHONE, getPhone());
                intent.putExtra(Constants.KEY_TITLE,getString(R.string.forget_reset_new_password));
                startActivity(intent);
            }else{
                showToast(CommonUtils.getMsg(result));
            }
        }

        @Override
        protected void timeOut() {

        }
    };

    @Override
    protected void initListener() {

    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initView() {
        setStatusBarColor(R.color.black);
        mAuthDev.setAuthCallback(this);
    }


    @Override
    public void clickAuthOk() {
        mAuthDev.updateAuthTime();
        BaseManager.forgetPasswordSendSms(getPhone(), mGetAuthCallback);
    }

    @Override
    public String getPhone() {
        return mPhoneDev.getContent();
    }

    @Event(R.id.forget_next_tv)
    private void send_for_sure(View view) {

        String phone=mPhoneDev.getContent();
        if (TextUtils.isEmpty(phone)) {
            showToast(R.string.phone_empty);
            return;
        }
        if (!CommonUtils.isMobile(phone)) {
            showToast(R.string.phone_error);
            return;
        }
        String auth=mAuthDev.getContent();
        if (TextUtils.isEmpty(auth)) {
            showToast(R.string.auth_empty);
            return;
        }
        if (auth.length()!=6) {
            showToast(R.string.auth_error);
            return;
        }

        showdialog();
        BaseManager.forgetPasswordSendSmsVerify(phone,auth,mVerfityCallback);
    }


}
