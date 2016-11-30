package com.easyhoms.easydoctor.team.activity.user;


import android.view.View;
import android.widget.EditText;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.view.MyActionbar;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_user_note)
public class UserNoteActivity extends BaseActivity {

    @BindView(R.id.user_note_ma)
    private MyActionbar mActionbar;
    @BindView(R.id.user_note_et)
    private EditText mNoteEt;

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
