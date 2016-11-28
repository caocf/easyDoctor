package com.easyhoms.easydoctor.my.activity.chooseHospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.view.MenuItem;
import com.easyhoms.easydoctor.common.view.MyActionbar;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.Event;

public class BindHospitalActivity extends BaseActivity {

    @BindView(R.id.bind_hospital_ma)
    private MyActionbar mActionbar;
    @BindView(R.id.bind_hospital_mi)
    private MenuItem mBindHosMi;
    @BindView(R.id.bind_hospital_btn)
    private Button mBindHosBtn;


    @Override
    protected void initView() {

    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Event(R.id.bind_hospital_mi)
    private void chooseHospital(View view){
        Intent intent  = new Intent(BindHospitalActivity.this,MyChooseHospitalActivity.class);
        startActivity(intent);
    }

    @Event(R.id.bind_hospital_btn)
    private void bindHos(View view){

    }
}
