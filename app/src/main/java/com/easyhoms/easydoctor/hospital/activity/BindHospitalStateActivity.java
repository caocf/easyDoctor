package com.easyhoms.easydoctor.hospital.activity;

import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.common.view.MyAlertDialog;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;


@ContentView(R.layout.activity_bind_hospital_state)
public class BindHospitalStateActivity extends BaseActivity {

    @BindView(R.id.bind_hosp_ma)
    MyActionbar mBindHospMa;
    @BindView(R.id.bind_state_tv)
    TextView mBindStateTv;

    private boolean mBinded = true;
    private String mBindHospital = "厦门第一医院";
    private Resources mResources;

    @Override
    protected void initView() {
        mResources = getResources();
        String bindStr;
        SpannableStringBuilder style;
        if (mBinded) {
            bindStr= String.format(getString(R.string.bind_hospital_ok), mBindHospital);
            style = new SpannableStringBuilder(bindStr);
            //SpannableStringBuilder实现CharSequence接口
            style.setSpan(new ForegroundColorSpan(mResources.getColor(R.color.text_body_weak)), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(mResources.getColor(R.color.main_color_blue)), 5, 5 + mBindHospital.length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else{
            bindStr = String.format(getString(R.string.wating_bind_hospital), mBindHospital);
            style= new SpannableStringBuilder(bindStr);
            //SpannableStringBuilder实现CharSequence接口
            style.setSpan(new ForegroundColorSpan(mResources.getColor(R.color.text_body_weak)), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(mResources.getColor(R.color.main_color_blue)), 5, 5 + mBindHospital.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(mResources.getColor(R.color.text_body_weak)), 5 + mBindHospital.length()+1, bindStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mBindStateTv.setText(style);//将其添加到tv中
    }

    @Override
    protected void initActionbar() {
        mBindHospMa.setRightTv(getString(R.string.unbind_hospital), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showUnBindDialog();
            }
        });

        mBindHospMa.setRightTvVisible(mBinded ? View.VISIBLE : View.GONE);
    }

    private void showUnBindDialog() {
        String title=String.format(mResources.getString(R.string.unbind_hospital_confirm),mBindHospital);
        new MyAlertDialog(mContext).builder().setTitle(title)
                .setMsg(getResources().getString(R.string.unbind_hospital_detail))
                .setLeftButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setRightButton(getResources().getString(R.string.make_sure), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

    @Override
    protected void initListener() {

    }

}
