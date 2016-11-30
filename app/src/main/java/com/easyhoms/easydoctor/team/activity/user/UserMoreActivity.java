package com.easyhoms.easydoctor.team.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.view.MenuItem;
import com.easyhoms.easydoctor.common.view.MyActionbar;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_user_more)
public class UserMoreActivity extends BaseActivity {

    @BindView(R.id.user_more_remark)
    private MenuItem mMoreRemark;
    @BindView(R.id.user_more_switch)
    private ImageView mMoreSwitch;


    @Override
    protected void initView() {

    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }
    @Event(R.id.user_more_remark)
    private void setRemark(View view){
        Intent intent = new Intent(UserMoreActivity.this,UserNoteActivity.class);
        startActivity(intent);
    }
}
