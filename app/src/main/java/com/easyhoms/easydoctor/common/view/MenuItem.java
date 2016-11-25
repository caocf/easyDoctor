package com.easyhoms.easydoctor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;


/**
 * 设置中menuItem
 */
public class MenuItem extends RelativeLayout {
    public static final int SINGLE = 1;
    public static final int TOP = 2;
    public static final int CENTER = 3;
    public static final int BOTTOM = 4;


    View mTopLineV;
    TextView mNameTv;
    TextView mDescribeTv;
    View mBottomLineV;
    RelativeLayout mSetRl;
    ImageView mNextImg;
    ImageView mLeftImg;
    Context mContext;

    public MenuItem(Context context) {
        super(context);
        mContext=context;
    }

    public MenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.custom_view_setting_menu, this);

        mTopLineV=(View)findViewById(R.id.top_line_v);
        mNameTv= (TextView) findViewById(R.id.menu_title_tv);
        mDescribeTv= (TextView) findViewById(R.id.describe_tv);
        mBottomLineV=(View)findViewById(R.id.bottom_line_v);
        mSetRl= (RelativeLayout) findViewById(R.id.set_rl);
        mNextImg= (ImageView) findViewById(R.id.next_img);
        mLeftImg= (ImageView) findViewById(R.id.describe_img);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MenuItem);

        int type = typedArray.getInteger(R.styleable.MenuItem_mi_type, 1);
        RelativeLayout.LayoutParams lp = (LayoutParams) mTopLineV.getLayoutParams();
        switch (type) {
            case SINGLE:
                mTopLineV.setVisibility(VISIBLE);
                mBottomLineV.setVisibility(VISIBLE);
                break;
            case TOP:
                mTopLineV.setVisibility(VISIBLE);
                mBottomLineV.setVisibility(INVISIBLE);
                break;
            case CENTER:
                mTopLineV.setVisibility(VISIBLE);
                mBottomLineV.setVisibility(INVISIBLE);
                lp.setMargins(40, 0, 0, 0);
                mTopLineV.setLayoutParams(lp);
                break;
            case BOTTOM:
                mTopLineV.setVisibility(VISIBLE);
                mBottomLineV.setVisibility(VISIBLE);
                lp.setMargins(40, 0, 0, 0);
                mTopLineV.setLayoutParams(lp);
                break;
        }
        int nextVisible = typedArray.getInteger(R.styleable.MenuItem_mi_next_visible, 2);
        switch (nextVisible){
            case 1:
                mNextImg.setVisibility(GONE);
                break;
            case 2:
                mNextImg.setVisibility(VISIBLE);
                break;
            case 3:
                mNextImg.setVisibility(INVISIBLE);
                break;
        }

        String title=typedArray.getString(R.styleable.MenuItem_mi_title);
        mNameTv.setText(title);

        String describe=typedArray.getString(R.styleable.MenuItem_mi_describe);
        mDescribeTv.setText(describe);

        boolean leftImgVisible=typedArray.getBoolean(R.styleable.MenuItem_mi_left_img_visible,false);
        mLeftImg.setVisibility(leftImgVisible?VISIBLE:GONE);

        int leftImgId = typedArray.getResourceId(R.styleable.MenuItem_mi_left_img, 0);
        if (leftImgId!= 0) {
            mLeftImg.setVisibility(VISIBLE);
            mLeftImg.setImageResource(leftImgId);
        }
        //资源回收
        typedArray.recycle();
    }

    public void setRightText(String text){
        mDescribeTv.setText(TextUtils.isEmpty(text)?"":text);
    }
    public void setRightText(int textId){
        setRightText(mContext.getString(textId));
    }

}
