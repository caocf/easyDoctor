package com.easyhoms.easydoctor.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easyhoms.easydoctor.R;


/**
 * 自定义Toast
 */
public class ToastUtils {

    public static void showToast(Context context, String message, int resource, int time) {
        Toast mToast = new Toast(context);
        mToast.setDuration(time);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout mToastView = new LinearLayout(context);
        mToastView.setBackgroundResource(R.drawable.shape_toast_bg);
        mToastView.setOrientation(LinearLayout.VERTICAL);
        ImageView mImage = new ImageView(context);
        mImage.setImageResource(resource);
        LinearLayout.LayoutParams mParamsIv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mParamsIv.setMargins(18, 18, 18, 0);
        mImage.setLayoutParams(mParamsIv);
        mToastView.addView(mImage);
        TextView mText = new TextView(context);
        mText.setText(message);
        mText.setTextColor(context.getColor(R.color.white));
        LinearLayout.LayoutParams mParamsTv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mParamsTv.bottomMargin = 14;
        mParamsTv.gravity = Gravity.CENTER_HORIZONTAL;
        mText.setLayoutParams(mParamsTv);
        mToastView.addView(mText);
        mToast.setView(mToastView);
        mToast.show();
    }

    private static Toast toast;
    public static void showToast(Context context,String content){
        if (toast==null){
            toast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        }else {
            toast.setText(content);
        }
        toast.show();
    }

}
