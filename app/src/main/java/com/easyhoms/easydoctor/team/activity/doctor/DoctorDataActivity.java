package com.easyhoms.easydoctor.team.activity.doctor;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.team.response.Doctor;
import com.netease.nim.common.ui.imageview.HeadImageView;
import com.netease.nim.session.SessionHelper;
import com.netease.nim.session.activity.P2PMessageActivity;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import static com.easyhoms.easydoctor.R.id.doctor_data_send_msg_btn;

@ContentView(R.layout.activity_doctor_data)
public class DoctorDataActivity extends BaseActivity {

    @BindView(R.id.doctor_data_ma)
    MyActionbar mActionbar;
    @BindView(R.id.doctor_data_head_hiv)
    HeadImageView mDoctorDataHeadHiv;
    @BindView(R.id.doctor_data_approve_img)
    ImageView mDoctorDataApproveImg;
    @BindView(R.id.doctor_data_name_tv)
    TextView mDoctorDataNameTv;
    @BindView(R.id.doctor_data_sex_img)
    ImageView mDoctorDataSexImg;
    @BindView(R.id.doctor_data_nickname_tv)
    TextView mDoctorDataNicknameTv;
    @BindView(R.id.doctor_data_doctor_title_tv)
    TextView mDoctorDataDoctorTitleTv;
    @BindView(R.id.doctor_data_age_tv)
    TextView mDoctorDataAgeTv;
    @BindView(R.id.doctor_data_hos_tv)
    TextView mDoctorDataHosTv;
    @BindView(R.id.doctor_data_special_tv)
    TextView mDoctorDataSpecialTv;
    @BindView(R.id.doctor_data_introduce_tv)
    TextView mDoctorDataIntroduceTv;
    @BindView(R.id.doctor_data_from_tv)
    TextView mDoctorDataFromTv;
    @BindView(doctor_data_send_msg_btn)
    TextView mDoctorDataSendMsgBtn;
    @BindView(R.id.activity_doctor_data)
    LinearLayout mActivityDoctorData;

    private Doctor mDoctor;

    @Override
    protected void initView() {
        mDoctor = getIntent().getParcelableExtra(Constants.KEY_DATA);
        CommonUtils.loadImg(Constants.HOST_HEAD + "/" + mDoctor.imagePath, mDoctorDataHeadHiv, R.drawable.doctors_default_head);
        setText(mDoctorDataNameTv, mDoctor.name);

    }

    @Override
    protected void initActionbar() {
        if (mDoctor.id != UserManager.getUser().id) {
            mActionbar.setRightTv(R.string.more, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,DoctorMoreActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void initListener() {

    }


    @Event(R.id.doctor_data_send_msg_btn)
    private void sendMsg(View view) {
        P2PMessageActivity.start(mContext,Constants.IM_DOCTOR+mDoctor.id, SessionHelper.getTextP2pCustomization());
    }

}
