package com.easyhoms.easydoctor.my.activity.password;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.LogUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.DescribeEditView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;


@ContentView(R.layout.activity_update_password_first)
public class UpdatePasswordFirstActivity extends BaseActivity implements DescribeEditView.SendAuthCallback {
    @BindView(R.id.my_passwd_auth_dev)
    private DescribeEditView mAuthDev;
    @BindView(R.id.my_passwd_auth_sure_tv)
    private TextView mSureTv;
    @BindView(R.id.my_phone_tv)
    private TextView mPhoneTv;
    private String mPhone;
    private String mAuth;

    private NetCallback mGetAuthCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            //验证码发送成功  更新时间
            if (CommonUtils.isResultOK(result)) {

            } else {
                showToast(CommonUtils.getMsg(result));
                mAuthDev.resetAuth();
            }
        }

        @Override
        protected void timeOut() {

        }
    };

    private NetCallback mVerfityCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Intent intent = new Intent(mContext, UpdatePasswordSecondActivity.class);
                startActivity(intent);
            } else {
                showToast(CommonUtils.getMsg(result));
            }

        }

        @Override
        protected void timeOut() {
        }
    };

    @Override
    protected void initView() {
        mPhone = UserManager.getUser().mobile;
        mPhoneTv.setText(String.format(getString(R.string.current_phone), mPhone));
        mAuthDev.setAuthCallback(this);
        mAuthDev.getContentEt().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAuth = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.i(mAuth);
                mSureTv.setSelected(mAuth.length() == 6);
                if (mAuth.length()>6){
                    showToast(R.string.auth_error);
                }
            }
        });
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void clickAuthOk() {
        mAuthDev.updateAuthTime();
        BaseManager.forgetPasswordSendSms(getPhone(), mGetAuthCallback);
    }

    @Override
    public String getPhone() {
        return UserManager.getUser().mobile;
    }

    @Event(R.id.my_passwd_auth_sure_tv)
    private void makeSure(View view) {

        if (TextUtils.isEmpty(mAuth)) {
            showToast(R.string.auth_empty);
            return;
        }
        if (mAuth.length() != 6) {
            showToast(R.string.auth_error);
            return;
        }

        showdialog();
        BaseManager.forgetPasswordSendSmsVerify(mPhone,mAuth,mVerfityCallback);
    }

}
