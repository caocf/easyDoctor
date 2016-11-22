package com.easyhoms.easydoctor.login.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.register.activity.RegisterActivity;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;


@ContentView(R.layout.activity_guide)
public class GuideActivity extends BaseActivity {
    @BindView(R.id.guide_login_tv)
    private TextView mLoginTv;
    @BindView(R.id.guide_register_tv)
    private TextView mRegisterTv;

    @Override
    protected void initView() {
        setStatusBarColor(R.color.black);
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Event(R.id.guide_login_tv)
    private void startLogin(View view) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);

    }

    @Event(R.id.guide_register_tv)
    private void startRegister(View view) {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivity(intent);
    }
}
