package com.easyhoms.easydoctor.my.activity.personal;

import android.view.View;
import android.widget.EditText;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.view.MyActionbar;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_real_name)
public class RealNameActivity extends BaseActivity {

    @BindView(R.id.real_name_ma)
    private MyActionbar mMyActionbar;
    @BindView(R.id.real_name_edit)
    private EditText mEditText;

    @Override
    protected void initView() {
        mMyActionbar.setRightTv("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }


}
