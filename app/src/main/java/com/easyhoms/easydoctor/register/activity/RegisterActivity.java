package com.easyhoms.easydoctor.register.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.ImManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.KeyBoardUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.DescribeEditView;
import com.easyhoms.easydoctor.main.activity.MainActivity;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;


@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity implements DescribeEditView.SendAuthCallback {

    @BindView(R.id.register_name_dev)
    private DescribeEditView mRegisterNameDev;
    @BindView(R.id.register_phone_dev)
    private DescribeEditView mRegisterPhoneDev;
    @BindView(R.id.register_auth_dev)
    private DescribeEditView mRegisterAuthDev;
    @BindView(R.id.register_password_dev)
    private DescribeEditView mRegisterPasswordDev;
    @BindView(R.id.register_register_tv)
    private TextView mRegisterRegisterTv;
    @BindView(R.id.register_protocol_tv)
    private TextView mRegisterProtocolTv;
    @BindView(R.id.activity_register)
    private LinearLayout mActivityRegister;

    //登录云信回调
    private ImManager.ImLoginCallback mLoginCallback = new ImManager.ImLoginCallback() {
        @Override
        public void success() {
            loginOk();
        }

        @Override
        public void fail() {
            closeDialog();
            showToast(R.string.operate_later);
        }
    };
    private NetCallback mRegisterCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                UserManager.paraseUser(result,mPhone,mPassword);
                ImManager.login(mContext,UserManager.getUser().staffImId,mLoginCallback);
            } else {

            }
        }
        @Override
        protected void timeOut() {

        }
    };

    private NetCallback mGetAuthCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            //验证码发送成功  更新时间
            if (CommonUtils.isResultOK(result)) {

            }else{
                showToast(CommonUtils.getMsg(result));
                mRegisterAuthDev.resetAuth();
            }
        }

        @Override
        protected void timeOut() {

        }
    };
    private String mPassword;
    private String mPhone;

    @Override
    protected void initView() {
        setStatusBarColor(R.color.black);
        mRegisterAuthDev.setAuthCallback(this);
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Event(R.id.register_register_tv)
    private void register(View view) {
        String nickname=mRegisterNameDev.getContent();
        if (TextUtils.isEmpty(nickname)) {
            showToast(R.string.name_empty);
            return;
        }
        if(nickname.length()>6){
            showToast(R.string.nick_name_length_error);
            return;
        }
        mPhone = mRegisterPhoneDev.getContent();
        if (TextUtils.isEmpty(mPhone)) {
            showToast(R.string.phone_empty);
            return;
        }
        if (!CommonUtils.isMobile(mPhone)) {
            showToast(R.string.phone_error);
            return;
        }
        String auth=mRegisterAuthDev.getContent();
        if (TextUtils.isEmpty(auth)) {
            showToast(R.string.auth_empty);
            return;
        }
        if (auth.length()!=6) {
            showToast(R.string.auth_error);
            return;
        }

        boolean passwrodIsTrue = true;
        mPassword = mRegisterPasswordDev.getContent();
        switch (CommonUtils.isRightPassword(mPassword)) {
            case Constants.ERROR_PASSWORD_FORMAT:
                showToast(R.string.password_error_format);
                return;
            case Constants.ERROR_PASSWORD_LENGTH_FORMAT:
                showToast(R.string.password_error_format_length);
                return;
            case Constants.ERROR_PASSWORD_LENGTH:
                showToast(R.string.password_error_length);
                return;
        }

        showdialog();
        BaseManager.register("1", mPhone, nickname, mPassword,auth,mRegisterCallback);
    }


    @Override
    public void clickAuthOk() {
        //获取验证码
        mRegisterAuthDev.updateAuthTime();
        BaseManager.sendSms("1",mRegisterPhoneDev.getContent(), mGetAuthCallback);
    }

    @Override
    public String getPhone() {
        return mRegisterPhoneDev.getContent();
    }

    private void loginOk() {
        closeDialog();
        KeyBoardUtils.hideKeyboard(mRegisterPasswordDev.getContentEt());
        showToast(R.string.login_ok);
        finish();
        startActivity(new Intent(mContext, MainActivity.class));
    }
}
