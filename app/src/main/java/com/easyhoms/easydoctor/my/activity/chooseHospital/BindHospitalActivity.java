package com.easyhoms.easydoctor.my.activity.chooseHospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.bean.BindHospitalRecord;
import com.easyhoms.easydoctor.common.view.MenuItem;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.my.adapter.HospitalApplyRecordAdapter;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_bind_hospital)
public class BindHospitalActivity extends BaseActivity {

    @BindView(R.id.bind_hospital_ma)
    MyActionbar mActionbar;
    @BindView(R.id.bind_hospital_mi)
    MenuItem mBindHosMi;
    @BindView(R.id.bind_hospital_btn)
    Button mBindHosBtn;
    @BindView(R.id.bind_hospital_record)
    ListView mBindHosRecord;
    @BindView(R.id.bind_hospital_apply)
    TextView mBindHosApply;

    private HospitalApplyRecordAdapter mAdapter;

    private List<BindHospitalRecord> mList;

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        BindHospitalRecord record = new BindHospitalRecord();
        record.hospital = "厦门大学附属第一医院";
        record.time = "申请时间：2016/12/02";
        record.status = 1;
        mList.add(record);
        BindHospitalRecord hospitalRecord = new BindHospitalRecord();
        hospitalRecord.hospital = "加拿大伊丽莎白女皇医院";
        hospitalRecord.time = "申请时间：2016/11/20";
        hospitalRecord.status = 0;
        mList.add(hospitalRecord);
        mAdapter = new HospitalApplyRecordAdapter(this,mList);
        mBindHosRecord.setAdapter(mAdapter);
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

    @Override
    protected void onResume() {
        super.onResume();

    }
}
