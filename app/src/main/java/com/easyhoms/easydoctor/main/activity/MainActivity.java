package com.easyhoms.easydoctor.main.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.LogoutHelper;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.permission.MPermission;
import com.easyhoms.easydoctor.common.permission.annotation.OnMPermissionDenied;
import com.easyhoms.easydoctor.common.permission.annotation.OnMPermissionGranted;
import com.easyhoms.easydoctor.common.response.BaseArrayResp;
import com.easyhoms.easydoctor.common.response.Hospital;
import com.easyhoms.easydoctor.common.utils.AppManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.message.fragment.CommuicateFragment;
import com.easyhoms.easydoctor.my.fragment.MyFragment;
import com.easyhoms.easydoctor.team.fragment.TeamFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.lang.reflect.Type;
import java.util.ArrayList;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements CommuicateFragment.UnreadCallback{
    private final int BASIC_PERMISSION_REQUEST_CODE = 110;
    @BindView(R.id.mian_bottom_commcunication_ll)
    private RelativeLayout mCommcunicationLl;
    @BindView(R.id.mian_bottom_treatment_ll)
    private LinearLayout mTreatmentLl;
    @BindView(R.id.mian_bottom_my_ll)
    private LinearLayout mMyLl;
    @BindView(R.id.unread_tv)
    private TextView mUnReadTv;

    private CommuicateFragment mCommunicationFragment;
    private TeamFragment mCureFragment;
    private MyFragment mMyFragment;
    private int mIndex = 0;

    private FragmentManager mFragmentManager;
    private ArrayList<Hospital> mBindHospitals=new ArrayList<>();
    private NetCallback mGetHospCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<Hospital>>() {
                }.getType();
                BaseArrayResp<Hospital> res = new Gson().fromJson(result, objectType);
                mBindHospitals=res.content;
                UserManager.saveBindHosToDb(mBindHospitals);

            } else {

            }
        }
        @Override
        protected void timeOut() {

        }
    };

    @Override
    protected void initView() {
        requestBasicPermission();
        mFragmentManager = getSupportFragmentManager();
        //虽然他注册了广播接收者，但是他并没有进行处理


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        switch (mIndex) {
            case 0:
                onClickBottom(mCommcunicationLl);
                break;
            case 1:
                onClickBottom(mTreatmentLl);
                break;
            case 2:
                onClickBottom(mMyLl);
                break;
        }

        BaseManager.getMyHospitals(mGetHospCallback);
    }

    @Event({R.id.mian_bottom_commcunication_ll, R.id.mian_bottom_treatment_ll, R.id.mian_bottom_my_ll})
    private void onClickBottom(View view) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        mCommcunicationLl.setSelected(false);
        mTreatmentLl.setSelected(false);
        mMyLl.setSelected(false);

        switch (view.getId()) {
            case R.id.mian_bottom_commcunication_ll:
                mIndex = 0;
                mCommcunicationLl.setSelected(true);
                if (null == mCommunicationFragment) {
                    mCommunicationFragment = new CommuicateFragment();
                    transaction.add(R.id.main_fragment_fl, mCommunicationFragment);
                } else {
                    transaction.show(mCommunicationFragment);
                }
                mCommunicationFragment.setUnreadCallback(this);
                break;
            case R.id.mian_bottom_treatment_ll:
                mIndex = 1;
                mTreatmentLl.setSelected(true);
                if (null == mCureFragment) {
                    mCureFragment = new TeamFragment();
                    transaction.add(R.id.main_fragment_fl, mCureFragment);
                } else {
                    transaction.show(mCureFragment);
                }
                break;
            case R.id.mian_bottom_my_ll:
                mIndex = 2;
                mMyLl.setSelected(true);
                if (null == mMyFragment) {
                    mMyFragment = new MyFragment();
                    transaction.add(R.id.main_fragment_fl, mMyFragment);
                } else {
                    transaction.show(mMyFragment);
                }
                break;

            default:
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题
        super.onSaveInstanceState(outState);
        outState.putInt("index", mIndex);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mIndex = savedInstanceState.getInt("index");
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
//            showExitDialog();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != mCommunicationFragment) {
            transaction.hide(mCommunicationFragment);
        }
        if (null != mCureFragment) {
            transaction.hide(mCureFragment);
        }
        if (null != mMyFragment) {
            transaction.hide(mMyFragment);
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle(R.string.exit_app);
        ad.setMessage(R.string.whether_exit_app);
        ad.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LogoutHelper.logout();
                        AppManager.getAppManager().finishActivity();
                    }
                }
        );
        ad.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.create().show();
    }


    /**
     * 基本权限管理
     */
    private void requestBasicPermission() {
        MPermission.with(MainActivity.this)
                .addRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        //   showToast(R.string.granted_ok);

    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        showToast(R.string.granted_fail);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mMyFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void unread(int count) {
        String countStr="";
        if(count>99){
            countStr="99+";
        }else if(count==99){
            countStr="99";
        }else{
            countStr=count+"";
        }
        mUnReadTv.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        mUnReadTv.setText(countStr);
    }


}