package com.easyhoms.easydoctor.team.activity.user;

import android.content.Intent;
import android.view.View;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.view.MenuItem;
import com.easyhoms.easydoctor.common.view.MyActionbar;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_user_note)
public class UserNoteActivity extends BaseActivity {

    @BindView(R.id.user_note_ma)
    private MyActionbar mActionbar;


    @Override
    protected void initView() {

    }

    @Override
    protected void initActionbar() {
        mActionbar.setRightTv(getString(R.string.complete), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initListener() {

    }


}
