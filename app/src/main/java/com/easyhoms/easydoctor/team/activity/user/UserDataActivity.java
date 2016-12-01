package com.easyhoms.easydoctor.team.activity.user;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.bean.FavoritePatient;
import com.easyhoms.easydoctor.common.bean.FavoritePatientDetail;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.response.BaseArrayResp;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.utils.ToastUtils;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.common.ui.imageview.HeadImageView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;

import java.lang.reflect.Type;


/**
 * 用户资料
 */
@ContentView(R.layout.activity_user_data)
public class UserDataActivity extends BaseActivity {

    @BindView(R.id.user_data_ma)
    MyActionbar mActionbar;
    @BindView(R.id.user_data_head_hiv)
    HeadImageView mHeadHiv;
    @BindView(R.id.user_data_name_tv)
    TextView mNameTv;
    @BindView(R.id.user_data_nickname_tv)
    TextView mNicknameTv;
    @BindView(R.id.user_data_age_tv)
    TextView mAgeTv;
    @BindView(R.id.user_data_sex_img)
    ImageView mSexImg;
    @BindView(R.id.user_data_order_project_tv)
    TextView mOrderProjectTv;
    @BindView(R.id.user_data_order_time_tv)
    TextView mOrderTimeTv;
    @BindView(R.id.user_data_treat_project_tv)
    TextView mTreatProjectTv;
    @BindView(R.id.user_data_send_msg_btn)
    TextView mSendMsgBtn;

    private FavoritePatientDetail mDetail;

    private String mYxId;

    private NetCallback mDetailCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            Type objectType = new TypeToken<BaseArrayResp<FavoritePatientDetail>>() {
            }.getType();
            BaseArrayResp<FavoritePatientDetail> res = new Gson().fromJson(result, objectType);
            if (CommonUtils.isResultOK(result)) {
                mDetail = res.content.get(0);
                initData(mDetail);
            }else {
                ToastUtils.showToast(mContext,res.desc);
            }

        }

        @Override
        protected void timeOut() {

        }
    };

    @Override
    protected void initView() {
        mYxId = getIntent().getStringExtra("yxId");

    }

    @Override
    protected void initActionbar() {
        mActionbar.setRightTv(getString(R.string.more), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,UserMoreActivity.class);
                intent.putExtra("yxId",mYxId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    private void initData(FavoritePatientDetail detail){
        if (detail!=null){
            CommonUtils.loadImg(Constants.HOST_HEAD+"/"+detail.image_path,this.mHeadHiv);
            mNameTv.setText(detail.name);
            if (detail.gender == 1){
                mSexImg.setImageResource(R.drawable.icon_mail);
            }else {
                mSexImg.setImageResource(R.drawable.icon_grail);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseManager.userDetailInfo(mYxId,false,mDetailCallback);
    }
}
