package com.easyhoms.easydoctor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.ConstantValues;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.CommonUtils;

/**
 * 输入框
 * 左边文字  右边输入内容
 */

public class DescribeEditView extends LinearLayout {

    private static final int AUTH_TIME_SECONDS = ConstantValues.AUTH_TIME;
    private TextView mTitleTv;
    private EditText mContentEt;
    private ImageView mRightImg;
    private TextView mAuthTv;
    private View line;//底部的线是否要 默认是要的

    private boolean mAuthClickable = true;//发送验证码是否可点
    private boolean mImgSelected = false;//右侧图片是否点击
    private int mAuthTime = AUTH_TIME_SECONDS;


    private Context mContext;

    private SendAuthCallback mAuthCallback;

    public DescribeEditView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.DescribeEditView);

        String title = a.getString(R.styleable.DescribeEditView_dev_title);
        mTitleTv.setText(title);
        String hint = a.getString(R.styleable.DescribeEditView_dev_hint);
        mContentEt.setHint(hint);
        //右侧img是否可见   右侧验证码是否可见   验证码文字
        boolean imgVisible = a.getBoolean(R.styleable.DescribeEditView_dev_right_img_visible, false);
        boolean authVisible = a.getBoolean(R.styleable.DescribeEditView_dev_auth_visible, false);
        boolean isPassword = a.getBoolean(R.styleable.DescribeEditView_dev_is_password, false);

        //底部的线是否可见 默认可见
        boolean lineVisible = a.getBoolean(R.styleable.DescribeEditView_dev_is_line,true);

        if(lineVisible){
            line.setVisibility(VISIBLE);
        }
        else line.setVisibility(GONE);

        if (imgVisible) {
            mRightImg.setVisibility(VISIBLE);
        } else {
            mRightImg.setVisibility(GONE);
        }
        if (authVisible) {
            mAuthTv.setVisibility(VISIBLE);
        } else {
            mAuthTv.setVisibility(GONE);
        }
        if(isPassword){
            mImgSelected=false; //灰色  不可见
            mContentEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else{
            mImgSelected=true; //选中  蓝色  可见
            mContentEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        mRightImg.setSelected(mImgSelected);

        String authString = a.getString(R.styleable.DescribeEditView_dev_auth_text);
        mAuthTv.setText(authString);

        mAuthTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAuthClickable) {
                    return;
                }

                if (mAuthCallback == null) {
                    return;
                }
                String phone = mAuthCallback.getPhone();
                if (!CommonUtils.isMobile(phone)) {
                    switch (CommonUtils.isRightMobile(phone)){
                        case ConstantValues.ERROR_EMPTY:
                            CommonUtils.showToast(R.string.phone_empty);
                            return;
                        case ConstantValues.ERROR_PHONE_FORMAT:
                            CommonUtils.showToast(R.string.phone_error);
                            return;
                    }
                } else {
                    mAuthCallback.clickAuthOk();
                }
            }
        });

        mRightImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mImgSelected = !mImgSelected;
                if (mImgSelected) {
                    // 显示密码
                    mContentEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // 隐藏密码
                    mContentEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                CharSequence text = mContentEt.getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());// 将光标移动到最后
                }
                mRightImg.setSelected(mImgSelected);
            }
        });
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.custom_view_describe_edit, this);
        mTitleTv = (TextView) findViewById(R.id.edit_title_tv);
        mContentEt = (EditText) findViewById(R.id.edit_content_et);
        mRightImg = (ImageView) findViewById(R.id.edit_right_img);
        mAuthTv = (TextView) findViewById(R.id.edit_send_auth_tv);
        line = findViewById(R.id.bottom_line_dec);
    }

    public EditText getContentEt() {
        return mContentEt;
    }

    public String getContent() {
        return mContentEt.getText().toString();
    }

    public boolean isAuthClickable() {
        return mAuthClickable;
    }

    public void setAuthClickable(boolean authClickable) {
        mAuthClickable = authClickable;
    }

    public boolean isImgSelected() {
        return mImgSelected;
    }

    public void setImgSelected(boolean imgSelected) {
        mImgSelected = imgSelected;
    }

    //更新发送验证码时间
    public void updateAuthTime() {
        mAuthClickable = false;
        mAuthTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuthTime == 0) {
                    resetAuth();
                } else if (!mAuthClickable) {
                    mAuthTime--;
                    mAuthTv.setText(mAuthTime + mContext.getString(R.string.edit_after_seconds_resend));
                    mAuthTv.setTextColor(mContext.getResources().getColor(R.color.text_body_weak));
                    mAuthTv.postDelayed(this, 1000);
                }
            }
        }, 1000);

    }

    public void resetAuth() {
        mAuthTime = AUTH_TIME_SECONDS;
        mAuthClickable = true;
        mAuthTv.setText(R.string.send_auth_code);
        mAuthTv.setTextColor(mContext.getResources().getColor(R.color.main_color_blue));
        //  mGetAuthcodeTv.setBackgroundResource(R.drawable.bg_auth_green);
    }

    public void setAuthCallback(SendAuthCallback callback) {
        this.mAuthCallback = callback;
    }

    public interface SendAuthCallback {

        void clickAuthOk();

        String getPhone();
    }
}
