package com.easyhoms.easydoctor.my.activity.password;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.DescribeEditView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;


@ContentView(R.layout.activity_update_password_second)
public class UpdatePasswordSecondActivity extends BaseActivity {
    @BindView(R.id.my_passwd_auth_new_dev)
    private DescribeEditView mNewPasswordDev;
    @BindView(R.id.my_passwd_auth_sure_tv)
    private TextView mSureTv;

    private String mPassword;
    private NetCallback mVerfityCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                showToast(R.string.reset_password_ok);

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
        mNewPasswordDev.getContentEt().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                mSureTv.setSelected(mPassword.length()>=6&&mPassword.length()<=16);
            }
        });
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Event(R.id.my_passwd_auth_sure_tv)
    private void makeSure(View view) {

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
        BaseManager.forgetPasswordReset(UserManager.getUser().mobile, mPassword, mVerfityCallback);
    }

}
