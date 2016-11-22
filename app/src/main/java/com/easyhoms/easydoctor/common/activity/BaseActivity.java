package com.easyhoms.easydoctor.common.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.AppManager;
import com.easyhoms.easydoctor.common.utils.NetworkUtil;
import com.easyhoms.easydoctor.common.view.SystemBarTintManager;
import com.netease.nim.common.ui.dialog.DialogMaker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.xutils.x;


public abstract class BaseActivity extends FragmentActivity {
    protected ProgressDialog mProgressDialog;
    protected AlertDialog mLogoutDialog;
    protected DisplayImageOptions mImageOptions;
    protected BaseActivity mContext;
    protected SystemBarTintManager tintManager;
    private boolean destroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);

        mContext=this;
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.main_color_blue);
        //显示图片的配置
        mImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


        initView();
        initActionbar();
        initListener();
    }

    protected abstract void initView();
    protected abstract void initActionbar();
    protected abstract void initListener();

    public void setStatusBarColor(int color){
        tintManager.setStatusBarTintResource(color);
    }



    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }

    /**
     * 等待对话框
     */
    public void showdialog() {
        if (!NetworkUtil.isNetAvailable(this)) {
            showToast("网络未连接,请连接网络后再查询!");
            return;
        }
//        if (null == mProgressDialog) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage("正在操作,请稍后······");
//            mProgressDialog.setCanceledOnTouchOutside(false);
//        }
//        mProgressDialog.show();
        DialogMaker.showProgressDialog(mContext);
    }

    public void closeDialog() {
//        if (null != mProgressDialog && mProgressDialog.isShowing()) {
//            mProgressDialog.dismiss();
//        }
        DialogMaker.dismissProgressDialog();
    }

    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int stringId) {
        Toast.makeText(this, getText(stringId), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        DialogMaker.dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    public boolean isDestroyedCompatible() {
        if (Build.VERSION.SDK_INT >= 17) {
            return isDestroyedCompatible17();
        } else {
            return destroyed || super.isFinishing();
        }
    }

    @TargetApi(17)
    private boolean isDestroyedCompatible17() {
        return super.isDestroyed();
    }
}
