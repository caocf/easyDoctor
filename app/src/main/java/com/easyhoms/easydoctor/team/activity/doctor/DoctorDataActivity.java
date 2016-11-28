package com.easyhoms.easydoctor.team.activity.doctor;

import android.view.View;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.view.MyActionbar;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_doctor_data)
public class DoctorDataActivity extends BaseActivity {

    @BindView(R.id.doctor_data_ma)
    private MyActionbar mActionbar;

    @Override
    protected void initView() {

    }

    @Override
    protected void initActionbar() {
        mActionbar.setRightTv("更多", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initListener() {

    }
}
