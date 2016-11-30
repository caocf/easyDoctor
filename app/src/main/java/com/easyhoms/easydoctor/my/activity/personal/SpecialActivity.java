package com.easyhoms.easydoctor.my.activity.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.my.adapter.ChooseSpecialAdapter;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;

import java.util.Arrays;

@ContentView(R.layout.activity_special)
public class SpecialActivity extends BaseActivity {

    @BindView(R.id.special_ma)
    private MyActionbar mActionbar;
    @BindView(R.id.special_list)
    private ListView mSpecialList;

    private ChooseSpecialAdapter mAdapter;

    private String [] haha = {"胸部","眼部","美体塑形","皮肤","耳部","私密整形"};

    @Override
    protected void initView() {
        mAdapter = new ChooseSpecialAdapter(mContext, Arrays.asList(haha), new ChooseSpecialAdapter.SpecialCallback() {
            @Override
            public void chooseThis(String s) {

            }
        });
        mSpecialList.setAdapter(mAdapter);
    }

    @Override
    protected void initActionbar() {
        mActionbar.setSubTitle();
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
