package com.easyhoms.easydoctor.my.activity.approve;

import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.bean.User;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.MenuItem;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.netease.nim.common.ui.imageview.HeadImageView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;

/**
 * 认证资料  认证中
 */
@ContentView(R.layout.activity_approve_detail)
public class ApproveDetailActivity extends BaseActivity {

    @BindView(R.id.apply_approve_ma)
    private MyActionbar mActionbar;
    @BindView(R.id.approve_hiv)
    private HeadImageView mApproveHiv;
    @BindView(R.id.applay_state_tv)
    private TextView mStateTv;
    @BindView(R.id.approve_name)
    private MenuItem mApproveName;
    @BindView(R.id.approve_idCard)
    private MenuItem mApproveIdCard;
    @BindView(R.id.approve_idCheck)
    private MenuItem mApproveIdCheck;

    private User mUser;


    @Override
    protected void initView() {
        mUser = UserManager.getUser();
        CommonUtils.loadImg(Constants.HOST_HEAD+"/"+UserManager.getUser().imagePath,mApproveHiv);
        BaseManager.getAuthInfo(new NetCallback(mContext) {
            @Override
            protected void requestOK(String result) {
                if (CommonUtils.isResultOK(result)){
                    switch (mUser.authState){
                        case Constants.AUTH_AUTHING:
                            mStateTv.setText(getString(R.string.certificating));
                            mApproveIdCheck.setRightText(R.string.authing);
                            break;
                        case Constants.AUTH_FAIL:
                            mStateTv.setText(getString(R.string.certificate_fail));
                            mApproveIdCheck.setRightText(R.string.auth_fail);
                            break;
                        case Constants.AUTH_SUCCESS:
                            mStateTv.setText(getString(R.string.certificate_suc));
                            mApproveIdCheck.setRightText(R.string.auth_suc);
                            break;
                    }
                    mApproveName.setRightText(mUser.name);

                }
            }

            @Override
            protected void timeOut() {

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
