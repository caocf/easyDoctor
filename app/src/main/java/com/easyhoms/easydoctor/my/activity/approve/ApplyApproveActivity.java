package com.easyhoms.easydoctor.my.activity.approve;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.bean.UserImagePath;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.AppManager;
import com.easyhoms.easydoctor.common.utils.CameraUtils;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.IdCardUtils;
import com.easyhoms.easydoctor.common.utils.LogUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.utils.ToastUtils;
import com.easyhoms.easydoctor.common.view.ActionSheetDialog;
import com.easyhoms.easydoctor.common.view.AddImgMenu;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 申请认证
 */
@ContentView(R.layout.activity_apply)
public class ApplyApproveActivity extends BaseActivity {


    @BindView(R.id.apply_et)
    private EditText mApplyEt;
    @BindView(R.id.apply_ma)
    private MyActionbar mApplyMa;
    @BindView(R.id.one_aim)
    private AddImgMenu mOneAim;
    @BindView(R.id.two_aim)
    private AddImgMenu mTwoAim;
    @BindView(R.id.three_aim)
    private AddImgMenu mThreeAim;
    @BindView(R.id.four_aim)
    private AddImgMenu mFourAim;
    @BindView(R.id.five_aim)
    private AddImgMenu mFiveAim;

    private int mWhich;

    private File mUploadFile;

    private final int mOne = 0;
    private final int mTwo = 1;
    private final int mThree = 2;
    private final int mFour = 3;
    private final int mFive = 4;


    private List<AddImgMenu> mImgMenus;

    private List<String> mPaths;

    private NetCallback mFileCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Gson gson = new Gson();
                UserImagePath userImagePath = gson.fromJson(result, UserImagePath.class);
                setPath2List(mWhich,userImagePath.content.get("url"));
            } else {
                showToast(CommonUtils.getMsg(result));
            }
        }

        @Override
        protected void timeOut() {
        }
    };

    private NetCallback mAuthInfoCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            if (CommonUtils.isResultOK(result)){
                ToastUtils.showToast(mContext,getString(R.string.update_success));
                Intent intent = new Intent(mContext,ApproveDetailActivity.class);
                startActivity(intent);
                finish();
            }else {
                showToast(CommonUtils.getMsg(result));
            }
        }

        @Override
        protected void timeOut() {

        }
    };


    @Override
    protected void initView() {
        mPaths = new ArrayList<>();
        mImgMenus = new ArrayList<>();
        mImgMenus.add(mOneAim);
        mImgMenus.add(mTwoAim);
        mImgMenus.add(mThreeAim);
        mImgMenus.add(mFourAim);
        mImgMenus.add(mFiveAim);
    }

    @Override
    protected void initActionbar() {
        mApplyMa.setRightTv(getString(R.string.submit), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApplyEt.getText().length()<15){
                    CommonUtils.showToast(mContext,getString(R.string.enter_right_idcard));
                }else if (mPaths.size()!=5){
                    CommonUtils.showToast(mContext,getString(R.string.enter_all_photos));
                }else {
                    BaseManager.addAuthInfo(String.valueOf(UserManager.getUser().id),mPaths.get(0),mPaths.get(1),mPaths.get(2),
                            mPaths.get(3),mPaths.get(4),mAuthInfoCallback);
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Event({R.id.one_aim,R.id.two_aim,R.id.three_aim,R.id.four_aim,R.id.five_aim})
    private void choosePhoto(View view){
        switch (view.getId()){
            case R.id.one_aim:
                mWhich = mOne;
                break;
            case R.id.two_aim:
                mWhich = mTwo;
                break;
            case R.id.three_aim:
                mWhich = mThree;
                break;
            case R.id.four_aim:
                mWhich = mFour;
                break;
            case R.id.five_aim:
                mWhich = mFive;
                break;
        }
        ActionSheetDialog dialog = new ActionSheetDialog(mContext).builder().addSheetItem(getString(R.string.take_picture)
                , R.color.popcolor, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        initFile();
                        CameraUtils.choseHeadImageFromCameraCapture(mContext, new File(mUploadFile, Constants.HEAD_JPG));
                    }
                }
        ).addSheetItem(getString(R.string.picture_fromlocal), R.color.popcolor, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                initFile();
                CameraUtils.choseHeadImageFromGallery(mContext);
            }
        });
        dialog.show();
    }


    private void initFile() {
        mUploadFile = new File(Constants.SDCARD, Constants.PATH + UserManager.getUser().id + "/");
        if (!mUploadFile.exists()) {
            mUploadFile.mkdirs();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // 用户没有进行有效的设置操作，返回
        if (resultCode == Activity.RESULT_CANCELED) {
            if (requestCode == Constants.CODE_CAMERA_RESULT_REQUEST) {//裁剪不满意  重新拍照
                CameraUtils.choseHeadImageFromCameraCapture(mContext, new File(mUploadFile, Constants.HEAD_JPG));
            } else if (requestCode == Constants.CODE_GALLERY_RESULT_REQUEST) {
                CameraUtils.choseHeadImageFromGallery(mContext);
            } else {
                showToast(R.string.cancel);
            }
            return;
        }
        switch (requestCode) {
            case Constants.CODE_GALLERY_REQUEST:
                CameraUtils.cropRawPhoto(mContext, intent.getData(), Constants.CODE_GALLERY_RESULT_REQUEST);
                break;

            case Constants.CODE_CAMERA_REQUEST:
                if (CommonUtils.hasSdcard()) {
                    CameraUtils.cropRawPhoto(mContext, Uri.fromFile(new File(mUploadFile, Constants.HEAD_JPG)), Constants.CODE_CAMERA_RESULT_REQUEST);
                } else {
                    showToast(R.string.no_sdcard);
                }
                break;
            case Constants.CODE_GALLERY_RESULT_REQUEST:
            case Constants.CODE_CAMERA_RESULT_REQUEST:
                if (intent != null) {
                    File file = new File(mUploadFile, Constants.HEAD_JPG);
                    Bitmap photo = CameraUtils.saveIntentToFile(intent, file);
                    CameraUtils.saveFile(photo, file);
                    setImg(mWhich,photo);
                    showdialog();
                    BaseManager.uploadFile("image", file, mFileCallback);
                }
                break;
        }
    }

    private void setImg(int position,Bitmap photo){
        mImgMenus.get(position).setImg(photo);
    }

    private void setPath2List(int position,String url){
        mPaths.add(position,url);
    }
}
