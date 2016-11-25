package com.easyhoms.easydoctor.my.activity.chooseHospital;

import android.view.View;
import android.widget.ListView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.response.BaseArrayResp;
import com.easyhoms.easydoctor.common.response.Hospital;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.LocalHospialSearch;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.common.view.SearchLayout;
import com.easyhoms.easydoctor.my.adapter.MyChooseHospitalAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;

import java.lang.reflect.Type;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;


@ContentView(R.layout.activity_my_choose_hospial)
public class MyChooseHospialActivity extends BaseActivity implements MyChooseHospitalAdapter.ChooseCallback,SearchLayout.SearchCallback {

    @BindView(R.id.hospital_lv)
    ListView mHospitalLv;
    @BindView(R.id.choose_hospital_ma)
    MyActionbar mMyActionbar;
    @BindView(R.id.search_sl)
    SearchLayout mSearchSl;
    private MyChooseHospitalAdapter mAdapter;
    private ArrayList<Hospital> mHospitals=new ArrayList<>();
    private ArrayList<Hospital> mSearchHosipals=new ArrayList<>();
    private boolean mIsSearch=false;
    private NetCallback mCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<Hospital>>() {
                }.getType();
                BaseArrayResp<Hospital> res = new Gson().fromJson(result, objectType);
                mHospitals=res.content;
                // 排序
                Collections.sort(mHospitals, new Comparator<Hospital>() {
                    @Override
                    public int compare(Hospital l, Hospital r) {
                        return Collator.getInstance(Locale.CHINESE).compare(l.companyName, r.companyName);
                    }
                });
                mAdapter.setData(mHospitals);
            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };

    @Override
    protected void initView() {
        mAdapter=new MyChooseHospitalAdapter(mContext,mHospitals,this);
        mHospitalLv.setAdapter(mAdapter);
        mSearchSl.setCallback(this);
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseManager.getAllHospital(mCallback);
    }


    @Override
    public void choose(int hosId) {
        for (Hospital hospital : mSearchHosipals) {
            if(hospital.id==hosId){
                hospital.isChecked=true;
            }else{
                hospital.isChecked=false;
            }
        }
        for (Hospital hospital : mHospitals) {
            if(hospital.id==hosId){
                hospital.isChecked=true;
            }else{
                hospital.isChecked=false;
            }
        }
        if(mIsSearch){
            mAdapter.setData(mSearchHosipals);
        }else{
            mAdapter.setData(mHospitals);
        }
    }


    @Override
    public void fillData(String filterStr) {

        ArrayList<Hospital> filterDateList = LocalHospialSearch.searchGroup(filterStr, mHospitals);
        mSearchHosipals=filterDateList;
        mAdapter.setData(mSearchHosipals);
    }

    @Override
    public void cancel() {
        mIsSearch=false;
        mMyActionbar.setVisibility(View.VISIBLE);
        mAdapter.setData(mHospitals);
    }

    @Override
    public void showEditView() {
        mIsSearch=true;
        mMyActionbar.setVisibility(View.GONE);
        mAdapter.setData(new ArrayList<Hospital>());
    }
}
