package com.easyhoms.easydoctor.common.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.netease.nim.common.ui.imageview.HeadImageView;


public class MyTransforMemberDialog {
    private LinearLayout lLayout_bg;
    HeadImageView mHeadHiv;
    TextView mNameTv;
    Button mBtnNeg;
    Button mBtnPos;

    LinearLayout mLLayoutBg;
    private Context context;
    private Dialog dialog;
    private Display display;

    private DialogCallback mCallback;

    public MyTransforMemberDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();

    }

    public MyTransforMemberDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_dialog_transfor_member, null);
        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        mHeadHiv= (HeadImageView) view.findViewById(R.id.head_hiv);
        mNameTv= (TextView) view.findViewById(R.id.name_tv);
        mBtnNeg= (Button) view.findViewById(R.id.btn_neg);
        mBtnPos= (Button) view.findViewById(R.id.btn_pos);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    public MyTransforMemberDialog setDoctorHeadUrl(String url) {
        CommonUtils.loadImg(Constants.HOST_HEAD+"/"+ url,mHeadHiv);
        return this;
    }

    public MyTransforMemberDialog setDoctorName(String doctorName) {
        mNameTv.setText(doctorName);
        return this;
    }


    //设置点击屏幕不消失
    public MyTransforMemberDialog setAppera(boolean isAppera) {
        dialog.setCanceledOnTouchOutside(isAppera);// 设置点击屏幕Dialog不消失
        return this;
    }

    public MyTransforMemberDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public MyTransforMemberDialog setCallback(DialogCallback callback) {
        mCallback = callback;
        return this;
    }

    public MyTransforMemberDialog setRightButton(String text,
                                                 final OnClickListener listener) {
        if ("".equals(text)) {
            mBtnPos.setText("确定");
        } else {
            mBtnPos.setText(text);
        }
        mBtnPos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public MyTransforMemberDialog setLeftButton(String text,
                                                final OnClickListener listener) {

        if ("".equals(text)) {
            mBtnNeg.setText("取消");
        } else {
            mBtnNeg.setText(text);
        }
        mBtnNeg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }


    public void show() {
        dialog.show();
    }


    public interface DialogCallback {
        void getText(String text);
    }


}
