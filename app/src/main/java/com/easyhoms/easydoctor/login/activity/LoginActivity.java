package com.easyhoms.easydoctor.login.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.ConstantValues;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.DescribeEditView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * 登录
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.login_phone_dev)
    private DescribeEditView mPhoneDev;
    @ViewInject(R.id.login_password_dev)
    private DescribeEditView mPasswordDev;
    @ViewInject(R.id.login_tv)
    private TextView mLoginTv;
    @ViewInject(R.id.login_register_tv)
    private TextView mRegisterTv;
    @ViewInject(R.id.login_forget_pass_tv)
    private TextView mForgetPassTv;
    private String mPhone;
    private String mPassword;

    //登录接口
    private NetCallback mCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {

            if (CommonUtils.isResultOK(result)) {
              //  UserManager.paraseUser(result,mPhone,mPassword);
                loginOk();
                //  ImManager.login(mContext, UserManager.getUser().userImId+"", mLoginCallback);
            } else {
                closeDialog();
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
       /* if (UserManager.getUser()!=null) {
            mPhone=UserManager.getUser().mobile!=null?UserManager.getUser().mobile:"";
        }

        mPhoneDev.getContentEt().setText(mPhone);*/

    }
    @Event(R.id.login_tv)
    private void login(View view) {
        mPhone =mPhoneDev.getContent();
        switch (CommonUtils.isRightMobile(mPhone)){
            case ConstantValues.ERROR_EMPTY:
                showToast(R.string.phone_empty);
                return;
            case ConstantValues.ERROR_PHONE_FORMAT:
                showToast(R.string.phone_error);
                return;
        }
        mPassword =mPasswordDev.getContent();
        switch (CommonUtils.isRightPassword(mPassword)){
            case ConstantValues.ERROR_PASSWORD_FORMAT:
            case ConstantValues.ERROR_PASSWORD_LENGTH_FORMAT:
            case ConstantValues.ERROR_PASSWORD_LENGTH:
                showToast(R.string.password_error);
                return;
        }
        showdialog();
        BaseManager.login(mPhone, mPassword,mCallback);

    }

    @Event(R.id.login_register_tv)
    private void register(View view) {
       // startActivity(new Intent(mContext, RegisterPhoneActivity.class));
    }

    @Event(R.id.login_forget_pass_tv)
    private void forget(View view) {
     //   startActivity(new Intent(mContext, ForgetPasswordActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPhone=getIntent().getStringExtra(ConstantValues.KEY_PHONE);

        if(mPhone!=null){
            mPhoneDev.getContentEt().setText(mPhone);
            mPasswordDev.getContentEt().setText("");
        }
    }

    //防止singleTask登录  intent收不到
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void loginOk(){
        closeDialog();
        //KeyBoardUtils.hideKeyboard(mPasswordDev.getContentEt());
        showToast(R.string.login_ok);
        //AppManager.getAppManager().finishActivity();
       // startActivity(new Intent(mContext, MainActivity.class));
    }
}
