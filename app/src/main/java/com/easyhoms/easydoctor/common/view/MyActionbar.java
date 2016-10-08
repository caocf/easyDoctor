package com.easyhoms.easydoctor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.AppManager;


/**
 * 自定义Actionbar
 * 返回  中央title 中央图片   右侧图片
 */
public class MyActionbar extends FrameLayout {

    private ImageView mBackImg;
    private ImageView mRightFirstImg;
    private ImageView mRightSecondImg;
    private ImageView mCenterImg;
    private TextView mTitleTv;
    private TextView mRightTv;
    private TextView mLeftTv;
    private String mTitle;
    private RelativeLayout mBarRl;

    private Context mContext;

    public MyActionbar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.custom_view_actionbar, this);
        mBackImg = (ImageView) findViewById(R.id.bar_back_img);
        mTitleTv = (TextView) findViewById(R.id.bar_center_title_tv);
        mRightTv = (TextView) findViewById(R.id.bar_right_tv);
        mLeftTv = (TextView) findViewById(R.id.bar_left_tv);
        mRightFirstImg = (ImageView) findViewById(R.id.bar_right_first_img);
        mRightSecondImg = (ImageView) findViewById(R.id.bar_right_second_img);
        mCenterImg = (ImageView) findViewById(R.id.bar_center_img);

        mBarRl = (RelativeLayout) findViewById(R.id.bar_rl);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyActionbar);

        boolean rightTitleVisible = a.getBoolean(R.styleable.MyActionbar_ma_right_title,false);

        String title = a.getString(R.styleable.MyActionbar_ma_title);
        if (title != null) {
            mTitleTv.setText(title);
        }

        boolean rightBtnVisible = a.getBoolean(R.styleable.MyActionbar_ma_right_img_visible, true);
        if (rightBtnVisible) {
            mRightFirstImg.setVisibility(VISIBLE);
        }else{
            mRightFirstImg.setVisibility(INVISIBLE);
        }

        int rightImgId=a.getResourceId(R.styleable.MyActionbar_ma_right_img,0);
        if(rightImgId!=0){
            mRightFirstImg.setVisibility(VISIBLE);
            mRightFirstImg.setImageResource(rightImgId);
        }
        int secondImgId=a.getResourceId(R.styleable.MyActionbar_ma_second_img,0);
        if(rightImgId!=0){
            mRightSecondImg.setVisibility(VISIBLE);
            mRightSecondImg.setImageResource(secondImgId);
        }
        int backImgId=a.getResourceId(R.styleable.MyActionbar_ma_back_img,0);
        if(backImgId!=0){
            mBackImg.setVisibility(VISIBLE);
            mBackImg.setImageResource(backImgId);
        }
        int centerImgId=a.getResourceId(R.styleable.MyActionbar_ma_center_img,0);
        if(centerImgId!=0){
            mCenterImg.setVisibility(VISIBLE);
            mCenterImg.setImageResource(centerImgId);
        }
        boolean backBtnVisible = a.getBoolean(R.styleable.MyActionbar_ma_back_img_visible, true);
        if (backBtnVisible) {
            mBackImg.setVisibility(VISIBLE);
        }else{
            mBackImg.setVisibility(INVISIBLE);
        }
        boolean centerImgVisible = a.getBoolean(R.styleable.MyActionbar_ma_center_img_visible, false);
        if (centerImgVisible) {
            mCenterImg.setVisibility(VISIBLE);
        }else{
            mCenterImg.setVisibility(GONE);
        }
        mBackImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });

        //设置背景颜色
        int bgColor=a.getColor(R.styleable.MyActionbar_ma_bg,getResources().getColor(R.color.white));

        mBarRl.setBackgroundColor(bgColor);
    }

    public void setRightClick(OnClickListener onClickListener) {
        mRightFirstImg.setOnClickListener(onClickListener);
    }

    public void setRightBtnVisible(int visible) {
        mRightFirstImg.setVisibility(visible);
    }

    public void setLeftbtnVisible(int visible) {
        mBackImg.setVisibility(visible);
    }

    public void setSecondbtnVisible(int visible) {
        mRightSecondImg.setVisibility(visible);
    }

    public void setTitle(String text) {
        mTitleTv.setText(text);
    }

    public void setTitle(int textId) {
        mTitleTv.setText(textId);
    }
    public void setTitleVisible(int visible) {
        mTitleTv.setVisibility(visible);
    }

    public void setRightBtnImg(int imageId, OnClickListener onClickListener) {
        mRightFirstImg.setVisibility(VISIBLE);
        mRightFirstImg.setImageResource(imageId);
        mRightFirstImg.setOnClickListener(onClickListener);
    }

    public ImageView getRightBtnImg() {
        return mRightFirstImg;
    }
    public ImageView getSecondBtnImg() {
        return mRightSecondImg;
    }

    public void setSecondBtnImg(int imageId, OnClickListener onClickListener) {
        mRightSecondImg.setImageResource(imageId);
        mRightSecondImg.setOnClickListener(onClickListener);
    }

    public void setRightTv(int stringId, OnClickListener onClickListener) {
        this.setRightTv(mContext.getResources().getString(stringId),onClickListener);
    }
    public void setRightTv(String string, OnClickListener onClickListener) {
        mRightTv.setVisibility(VISIBLE);
        mRightTv.setText(string);
        mRightTv.setOnClickListener(onClickListener);
    }

    public void setLeftTv(int stringId, OnClickListener onClickListener) {
        mLeftTv.setVisibility(VISIBLE);
        mBackImg.setVisibility(INVISIBLE);
        mLeftTv.setText(stringId);
        mLeftTv.setOnClickListener(onClickListener);
    }
    public void setLeftBtnListener(OnClickListener onClickListener) {

        mBackImg.setVisibility(VISIBLE);

        mBackImg.setOnClickListener(onClickListener);
    }

    public void setRightTvVisible(int rightTvVisible) {
        mRightTv.setVisibility(rightTvVisible);
    }

    public ImageView getCenterImg() {
        return  mCenterImg;
    }

    public void setCenterImgClickListerer(OnClickListener listerer){
        mCenterImg.setOnClickListener(listerer);
    }
}
