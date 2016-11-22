package com.easyhoms.easydoctor.my.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.ConstantValues;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.LogoutHelper;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.AppManager;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.MenuItem;
import com.easyhoms.easydoctor.common.view.MyAlertDialog;
import com.easyhoms.easydoctor.login.activity.LoginActivity;
import com.netease.nim.preference.Preferences;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.msg.MsgService;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;



@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {

    @BindView(R.id.set_clear_mi)
    MenuItem mSetClearMi;
    @BindView(R.id.set_protactol_mi)
    MenuItem mSetProtactolMi;
    @BindView(R.id.set_update_mi)
    MenuItem mSetUpdateMi;
    @BindView(R.id.setting_exit_tv)
    TextView mSettingExitTv;
    //  private UpdateVersion mVersion;
    private boolean mCanUpdate = false;
    private NetCallback mCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
//            if (CommonUtils.isResultOK(result)) {
//                Type objectType = new TypeToken<BaseResp<UpdateVersion>>() {
//                }.getType();
//                BaseResp<UpdateVersion> res = new Gson().fromJson(result, objectType);
//                mVersion=res.content;
//                if (Integer.valueOf(mVersion.versionCode)< VersionUtils.getVersionCode(mContext)) {
//                    mUpdateTv.setText(R.string.current_is_newest);
//                    mCanUpdate=false;
//                }else{
//                    mUpdateTv.setText("版本"+mVersion.versionName);
//                    mCanUpdate=true;
//                }
//            } else {
//
//            }
        }

        @Override
        protected void timeOut() {

        }
    };

    @Override
    protected void initView() {

    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Event(R.id.set_clear_mi)
    private void clearCach(View view) {
        new MyAlertDialog(mContext).builder().setTitle(getResources().getString(R.string.alert_confirm_clear))
                .setMsg(getResources().getString(R.string.alert_clear_all_cach))
                //.setEdit(false,"")
                .setLeftButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setRightButton(getResources().getString(R.string.alert_clear_cach), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIMClient.getService(MsgService.class).clearMsgDatabase(true);
            }
        }).show();
    }

    @Event(R.id.setting_exit_tv)
    private void isExit(View view) {
        showExitDialog();
    }

    private void showExitDialog() {
        new MyAlertDialog(mContext).builder().setMsg(getString(R.string.whether_exit_app)).setTitle(getString(R.string.exit_app)).setLeftButton(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setRightButton(getString(R.string.confirm), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空User表
                LogoutHelper.logout();
                logout();
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                String phone = UserManager.getUser().mobile;
                UserManager.deleteUser();
                intent.putExtra(ConstantValues.KEY_PHONE, phone);
                startActivity(intent);
                AppManager.getAppManager().finishAllActivity();
            }
        }).show();
    }

    @Event(R.id.set_protactol_mi)
    private void setAgreement(View view) {
        //      startActivity(new Intent(SettingActivity.this, ProtocolActivity.class));
    }

    @Event(R.id.set_update_mi)
    private void update(View view) {


//        if (!mCanUpdate) {
//           showToast(R.string.current_is_newest);
//        }else{
//            showAppUpdateDlg();
//
//        }
    }

    private void showAppUpdateDlg() {
//        String msg=String.format(getString(R.string.update_msg),mVersion.versionName,mVersion.versionMemo);
//        new MyAlertDialog(mContext).builder().setMsg(msg).setTitle(getString(R.string.new_app_avilable)).setLeftButton(getString(R.string.cancel), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }).setRightButton(getString(R.string.download), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(SettingActivity.this, UpdateAppActivity.class);
//                intent.putExtra(ConstantValues.KEY_DATA,mVersion);
//                startActivity(intent);
//            }
//        }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showdialog();
        //  BaseManager.getVersion(mCallback);
    }

    /**
     * 注销
     */
    private void logout() {
        removeLoginState();
        NIMClient.getService(AuthService.class).logout();
    }

    /**
     * 清除登陆状态
     */
    private void removeLoginState() {
        Preferences.saveUserToken("");
    }


}
