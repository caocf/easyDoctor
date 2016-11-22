package com.easyhoms.easydoctor.my.activity.personal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.ConstantValues;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.bean.UserImagePath;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.CameraUtils;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.ActionSheetDialog;
import com.easyhoms.easydoctor.common.view.MenuItem;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.my.activity.approve.ApplyApproveActivity;
import com.easyhoms.pickview.TimePickerView;
import com.google.gson.Gson;
import com.netease.nim.common.ui.imageview.HeadImageView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * 个人资料
 */
@ContentView(R.layout.activity_my_data)
public class MyDataActivity extends BaseActivity {
    @BindView(R.id.menu_title_tv)
    TextView mNameTv;
    @BindView(R.id.my_data_ma)
    MyActionbar mMyActionbar;
    @BindView(R.id.head_hiv)
    HeadImageView mHeadHiv;
    @BindView(R.id.set_head_rl)
    RelativeLayout mSetHeadRl;
    @BindView(R.id.set_name_mi)
    MenuItem mSetNameMi;
    @BindView(R.id.set_sex_mi)
    MenuItem mSetSexMi;
    @BindView(R.id.set_birth_mi)
    MenuItem mSetBirthMi;
    @BindView(R.id.doctor_protactol_mi)
    MenuItem mDoctorProtactolMi;
    @BindView(R.id.doctor_title_mi)
    MenuItem mDoctorTitleMi;
    @BindView(R.id.special_project_mi)
    MenuItem mSpecialProjectMi;
    @BindView(R.id.introduce_mi)
    MenuItem mIntroduceMi;
    @BindView(R.id.activity_my_data)
    LinearLayout mActivityMyData;

    private File mUploadFile;
    //判断图片地址是否更改
    private String mImagePath;
    //判断性别是否更改
    private String mSex;
    //判断年龄是否更改；
    private String mNowYear;
    private String mUserYear;
    private String mNowImagePath;
    //获取当前年份
    private Calendar cal = Calendar.getInstance();

    private TimePickerView mTimePickerView;

    //更改头像
    private NetCallback mFileCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Gson gson = new Gson();
                UserImagePath userImagePath = gson.fromJson(result, UserImagePath.class);
                mNowImagePath = userImagePath.content.get("url");
                //向服务器上传地址
                BaseManager.userDetailInfoModify(mNowImagePath, "", "", "", mImagePathCallback);
            } else {
                showToast(CommonUtils.getMsg(result));
            }
        }

        @Override
        protected void timeOut() {
        }
    };
    //上传图片地址
    private NetCallback mImagePathCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            if (CommonUtils.isResultOK(result)) {
                UserManager.setUser("imagePath", mNowImagePath);

            } else {
                showToast(CommonUtils.getMsg(result));
            }
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
//        mMyActionbar.setSubTitle();
    }

    //更改头像
    @Event(R.id.set_head_rl)
    private void changePhoto(View view) {
        ActionSheetDialog dialog = new ActionSheetDialog(mContext).builder().addSheetItem(getString(R.string.take_picture)
                , R.color.popcolor, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        initFile();
                        CameraUtils.choseHeadImageFromCameraCapture(mContext, new File(mUploadFile, ConstantValues.HEAD_JPG));
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
        mUploadFile = new File(ConstantValues.SDCARD, ConstantValues.PATH + UserManager.getUser().id + "/");
        if (!mUploadFile.exists()) {
            mUploadFile.mkdirs();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == Activity.RESULT_CANCELED) {
            if (requestCode == ConstantValues.CODE_CAMERA_RESULT_REQUEST) {//裁剪不满意  重新拍照
                CameraUtils.choseHeadImageFromCameraCapture(mContext, new File(mUploadFile, ConstantValues.HEAD_JPG));
            } else if (requestCode == ConstantValues.CODE_GALLERY_RESULT_REQUEST) {
                CameraUtils.choseHeadImageFromGallery(mContext);
            } else {
                showToast(R.string.cancel);
            }
            return;
        }
        switch (requestCode) {
            case ConstantValues.CODE_GALLERY_REQUEST:
                CameraUtils.cropRawPhoto(mContext, intent.getData(), ConstantValues.CODE_GALLERY_RESULT_REQUEST);
                break;

            case ConstantValues.CODE_CAMERA_REQUEST:
                if (CommonUtils.hasSdcard()) {
                    CameraUtils.cropRawPhoto(mContext, Uri.fromFile(new File(mUploadFile, ConstantValues.HEAD_JPG)), ConstantValues.CODE_CAMERA_RESULT_REQUEST);
                } else {
                    showToast(R.string.no_sdcard);
                }
                break;
            case ConstantValues.CODE_GALLERY_RESULT_REQUEST:
            case ConstantValues.CODE_CAMERA_RESULT_REQUEST:
                if (intent != null) {
                    File file = new File(mUploadFile, ConstantValues.HEAD_JPG);
                    Bitmap photo = CameraUtils.saveIntentToFile(intent, file);
                    CameraUtils.saveFile(photo, file);
                    mHeadHiv.setImageBitmap(photo);
                    showdialog();
                    BaseManager.uploadFile("image", file, mFileCallback);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Event(R.id.set_birth_mi)
    private void choseBirth(View view) {

        mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY, new Date(System.currentTimeMillis()), false, true);
        mTimePickerView.setTitle(getString(R.string.choose_birth));
        mTimePickerView.show();
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                if (date.getTime() >= System.currentTimeMillis()) {
                    showToast("请选择正确的出生日期!");
                    return;
                }

            }
        });

    }

    @Event(R.id.set_sex_mi)
    private void startSex(View view) {

        Intent intent = new Intent(mContext, SexActivity.class);
        startActivity(intent);
    }
    @Event(R.id.doctor_protactol_mi)
    private void startprotactol(View view) {

        Intent intent = new Intent(mContext, ApplyApproveActivity.class);
        startActivity(intent);
    }

    @Event(R.id.set_name_mi)
    private void startName(View view){
        Intent intent = new Intent(mContext,RealNameActivity.class);
        startActivity(intent);
    }


}
