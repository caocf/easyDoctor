package com.netease.nim.common.media.picker;

import android.app.Activity;
import android.content.Context;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.view.ActionSheetDialog;
import com.netease.nim.common.media.picker.activity.PickImageActivity;
import com.netease.nim.common.util.storage.StorageType;
import com.netease.nim.common.util.storage.StorageUtil;
import com.netease.nim.common.util.string.StringUtil;


/**
 * Created by huangjun on 2015/9/22.
 */
public class PickImageHelper {

    public static class PickImageOption {
        /**
         * 图片选择器标题
         */
        public int titleResId = R.string.nim_choose;

        /**
         * 是否多选
         */
        public boolean multiSelect = true;

        /**
         * 最多选多少张图（多选时有效）
         */
        public int multiSelectMaxCount = 9;

        /**
         * 是否进行图片裁剪
         */
        public boolean crop = false;

        /**
         * 图片裁剪的宽度（裁剪时有效）
         */
        public int cropOutputImageWidth = 720;

        /**
         * 图片裁剪的高度（裁剪时有效）
         */
        public int cropOutputImageHeight = 720;

        /**
         * 图片选择保存路径
         */
        public String outputPath = StorageUtil.getWritePath(StringUtil.get32UUID() + ".jpg", StorageType.TYPE_TEMP);
    }

    /**
     * 打开图片选择器
     */
    public static void pickImage(final Context context, final int requestCode, final PickImageOption option) {
        if (context == null) {
            return;
        }
        ActionSheetDialog dialog = new ActionSheetDialog(context).builder().addSheetItem(context.getString(R.string.take_picture)
                , R.color.popcolor, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        int from = PickImageActivity.FROM_CAMERA;
                        if (!option.crop) {
                            PickImageActivity.start((Activity) context, requestCode, from, option.outputPath, option.multiSelect, 1,
                                    false, false, 0, 0);
                        } else {
                            PickImageActivity.start((Activity) context, requestCode, from, option.outputPath, false, 1,
                                    false, true, option.cropOutputImageWidth, option.cropOutputImageHeight);
                        }

                    }
                }
        ).addSheetItem(context.getString(R.string.picture_fromlocal), R.color.popcolor, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                int from = PickImageActivity.FROM_LOCAL;
                if (!option.crop) {
                    PickImageActivity.start((Activity) context, requestCode, from, option.outputPath, option.multiSelect,
                            option.multiSelectMaxCount, false, false, 0, 0);
                } else {
                    PickImageActivity.start((Activity) context, requestCode, from, option.outputPath, false, 1,
                            false, true, option.cropOutputImageWidth, option.cropOutputImageHeight);
                }
            }
        });
        dialog.show();
    }
}
