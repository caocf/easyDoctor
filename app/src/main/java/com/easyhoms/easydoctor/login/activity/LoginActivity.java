package com.easyhoms.easydoctor.login.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
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
import com.easyhoms.easydoctor.password.activity.ForgetPasswordActivity;
import com.easyhoms.easydoctor.register.activity.RegisterActivity;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;


/**
 * 登录
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_phone_dev)
    private DescribeEditView mPhoneDev;
    @BindView(R.id.login_password_dev)
    private DescribeEditView mPasswordDev;
    @BindView(R.id.login_tv)
    private TextView mLoginTv;
    @BindView(R.id.login_register_tv)
    private TextView mRegisterTv;
    @BindView(R.id.login_forget_pass_tv)
    private TextView mForgetPassTv;
    private String mPhone;
    private String mPassword;

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
    //登录接口
    private NetCallback mCallback = new NetCallback(this,false) {
        @Override
        protected void requestOK(String result) {

            if (CommonUtils.isResultOK(result)) {
                UserManager.paraseUser(result,mPhone,mPassword);
                ImManager.login(mContext, UserManager.getUser().staffImId, mLoginCallback);
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
        tintManager.setStatusBarTintResource(R.color.black);

    }
    @Event(R.id.login_tv)
    private void login(View view) {
        mPhone =mPhoneDev.getContent();
        switch (CommonUtils.isRightMobile(mPhone)){
            case Constants.ERROR_EMPTY:
                showToast(R.string.phone_empty);
                return;
            case Constants.ERROR_PHONE_FORMAT:
                showToast(R.string.phone_error);
                return;
        }
        mPassword =mPasswordDev.getContent();
        switch (CommonUtils.isRightPassword(mPassword)){
            case Constants.ERROR_PASSWORD_FORMAT:
            case Constants.ERROR_PASSWORD_LENGTH_FORMAT:
            case Constants.ERROR_PASSWORD_LENGTH:
                showToast(R.string.password_error);
                return;
        }
        showdialog();
        BaseManager.login(mPhone, mPassword,mCallback);

    }

    @Event(R.id.login_register_tv)
    private void register(View view) {
       startActivity(new Intent(mContext, RegisterActivity.class));
    }

    @Event(R.id.login_forget_pass_tv)
    private void forget(View view) {
        startActivity(new Intent(mContext, ForgetPasswordActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPhone=getIntent().getStringExtra(Constants.KEY_PHONE);

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
        KeyBoardUtils.hideKeyboard(mPasswordDev.getContentEt());
        showToast(R.string.login_ok);
        finish();
        startActivity(new Intent(mContext, MainActivity.class));
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
}
