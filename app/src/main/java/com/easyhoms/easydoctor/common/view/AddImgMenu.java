package com.easyhoms.easydoctor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;


/**
 * Created by 德医互联 on 2016/8/3.
 */
public class AddImgMenu extends FrameLayout{

    RelativeLayout mBottomRl;
    ImageView mTopImg;
    TextView mMsgTv;

    public AddImgMenu(Context context) {
        super(context);
    }

    public AddImgMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_view_add_img, this);

        mBottomRl = (RelativeLayout) findViewById(R.id.bottom_rl);
        mTopImg = (ImageView) findViewById(R.id.top_img);
        mMsgTv = (TextView) findViewById(R.id.msg_tv);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AddImgMenu);
        String describe=typedArray.getString(R.styleable.AddImgMenu_aim_msg);
        mMsgTv.setText(describe);

    }


    /**
     * image点击事件的回调
     */
    public void setImg(Bitmap bitmap)
    {
        mTopImg.setVisibility(VISIBLE);
        mTopImg.setImageBitmap(bitmap);

    }

    public interface OnItemClickListener
    {
        public void onImageClick();
        public void onTvClick();
    }
}
