package com.easyhoms.easydoctor.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.easyhoms.easydoctor.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 照相,拍照
 */
public class CameraUtils {


    /**
     * 将bitmap保存为到file
     */
    public static void saveFile(Bitmap bm, File file) {

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedOutputStream bos = null;
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过intent得到的数据保存图片
     */
    public static Bitmap saveIntentToFile(Intent intent, File file) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable(Constants.KEY_DATA);
            CameraUtils.saveFile(photo, file);
            return photo;
        }
        return null;
    }

    /**
     * 裁剪原始的图片
     */
    public static void cropRawPhoto(Activity activity, Uri uri,int resultCode) {

        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        activity.startActivityForResult(intent, resultCode);

    }


    /**
     * 从本地相册选取图片
     */
    public static void choseHeadImageFromGallery(Activity activity) {
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK);
        // 设置文件类型
        intentFromGallery.setType("image/*");

        activity.startActivityForResult(intentFromGallery, Constants.CODE_GALLERY_REQUEST);
    }

    /**
     * 启动手机相机拍摄照片
     */
    public static Intent choseHeadImageFromCameraCapture(Activity activity, File file) {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.putExtra("android.intent.extra.quickCapture",true);
        // 判断存储卡是否可用，存储照片文件
        if (CommonUtils.hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(file));
        }

        activity.startActivityForResult(intentFromCapture, Constants.CODE_CAMERA_REQUEST);
        return intentFromCapture;
    }


    /**
     * 加载图片,如果手机中不存在 就从网络获取
     */
    public static void loadPhoto(File file, ImageView img, String url) {
        if (file.exists()) {
            ImageLoader.getInstance().displayImage("file://" + file.getPath(), img);
        } else if (url != null && (!url.trim().equals(""))) {
            //  url = Constants.IMG_HOST + url;
            String url1 = url.replaceAll("\\\\", "/");
            ImageLoader.getInstance().displayImage(url1, img);
        }
    }


}
