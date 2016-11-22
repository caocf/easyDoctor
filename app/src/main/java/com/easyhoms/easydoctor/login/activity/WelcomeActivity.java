package com.easyhoms.easydoctor.login.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.bean.User;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.AppManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.main.activity.MainActivity;
import com.netease.nim.preference.Preferences;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity {
    private static final long SPLASH_DELAY_MILLIS = 1000;

    private String mPhone;
    private String mPassword;

    //登录接口
    private NetCallback mCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {

            if (CommonUtils.isResultOK(result)) {
                UserManager.paraseUser(result, mPhone, mPassword);
                loginOk();

            } else {
                goHomeDelay();
            }
        }

        @Override
        protected void timeOut() {
            goHomeDelay();
        }
    };

    @Override
    protected void initListener() {

        User user = UserManager.getUser();
        //不是第一次登录并且账号密码不为空
        if (user != null && user.password != null && user.mobile != null && canAutoLogin()) {
            mPassword = user.password;
            mPhone = user.mobile;
            BaseManager.login(user.mobile, user.password, mCallback);
        } else {
            goHomeDelay();
        }

    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initView() {
        setStatusBarColor(R.color.black);
    }

    private void loginOk() {
        postDelay(new Runnable() {
            public void run() {
                AppManager.getAppManager().finishActivity();
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
    }

    private void goHomeDelay() {
        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
        postDelay(new Runnable() {
            public void run() {
                mContext.startActivity(new Intent(mContext, GuideActivity.class));
                AppManager.getAppManager().finishActivity();
            }
        });
    }

    /**
     * 云信已经登陆过，自动登陆
     */
    private boolean canAutoLogin() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        return !TextUtils.isEmpty(account) && !TextUtils.isEmpty(token);
    }

    private void postDelay(Runnable runnable) {
        new Handler().postDelayed(runnable, SPLASH_DELAY_MILLIS);
    }


}
