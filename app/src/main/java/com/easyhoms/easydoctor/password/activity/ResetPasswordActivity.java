package com.easyhoms.easydoctor.password.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.KeyBoardUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.DescribeEditView;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.login.activity.LoginActivity;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;



/**
 * 更改密码,设置新密码
 */
@ContentView(R.layout.activity_reset_password)
public class ResetPasswordActivity extends BaseActivity {
    @BindView(R.id.change_password_dev)
    private DescribeEditView mPasswordNewDev;
    @BindView(R.id.change_sure_tv)
    private TextView mPasswordSureTv;
    @BindView(R.id.change_length_error_tv)
    private TextView mLengthErrorTv;
    @BindView(R.id.change_format_error_tv)
    private TextView mFormatErrorTv;
    @BindView(R.id.change_ma)
    private MyActionbar mChangeMa;
    private String mPhoneNumber;
    private String mTitle;//1.忘记密码->设置新密码  2.注册->新密码

    private NetCallback mChangePasswordCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if(CommonUtils.isResultOK(result)){
                KeyBoardUtils.hideKeyboard(mPasswordNewDev.getContentEt());
                showToast(R.string.reset_password_ok);
                Intent intent=new Intent(mContext, LoginActivity.class);
                intent.putExtra(Constants.KEY_PHONE,mPhoneNumber);
                startActivity(intent);
                finish();
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
        Intent intent = getIntent();
        mPhoneNumber = intent.getStringExtra(Constants.KEY_PHONE);
    }


    @Event(R.id.change_sure_tv)
    private void make_sure(View view) {
        mLengthErrorTv.setVisibility(View.GONE);
        mFormatErrorTv.setVisibility(View.GONE);
        String password = mPasswordNewDev.getContent();

        //遇到return直接退出该函数
        switch (CommonUtils.isRightPassword(password)) {
            case Constants.ERROR_PASSWORD_FORMAT:
                mFormatErrorTv.setVisibility(View.VISIBLE);
                return;
            case Constants.ERROR_PASSWORD_LENGTH_FORMAT:
                mLengthErrorTv.setVisibility(View.VISIBLE);
                mFormatErrorTv.setVisibility(View.VISIBLE);
                return;
            case Constants.ERROR_PASSWORD_LENGTH:
                mLengthErrorTv.setVisibility(View.VISIBLE);
                return;
        }
            BaseManager.forgetPasswordReset(mPhoneNumber,password,mChangePasswordCallback);
    }
}
