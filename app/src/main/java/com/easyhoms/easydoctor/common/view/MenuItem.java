package com.easyhoms.easydoctor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;

import org.xutils.view.annotation.BindView;


/**
 * 设置中menuItem
 */
public class MenuItem extends RelativeLayout {
    public static final int SINGLE = 1;
    public static final int TOP = 2;
    public static final int CENTER = 3;
    public static final int BOTTOM = 4;

    @BindView(R.id.top_line_v)
    View mTopLineV;
    @BindView(R.id.menu_title_tv)
    TextView mNameTv;
    @BindView(R.id.describe_tv)
    TextView mDescribeTv;
    @BindView(R.id.bottom_line_v)
    View mBottomLineV;
    @BindView(R.id.set_rl)
    RelativeLayout mSetRl;
    @BindView(R.id.next_img)
    ImageView mNextImg;

    public MenuItem(Context context) {
        super(context);
    }

    public MenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_view_setting_menu, this);

        mTopLineV=(View)findViewById(R.id.top_line_v);
        mNameTv= (TextView) findViewById(R.id.menu_title_tv);
        mDescribeTv= (TextView) findViewById(R.id.describe_tv);
        mBottomLineV=(View)findViewById(R.id.bottom_line_v);
        mSetRl= (RelativeLayout) findViewById(R.id.set_rl);
        mNextImg= (ImageView) findViewById(R.id.next_img);

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
        //资源回收
        typedArray.recycle();
    }

    private void initWidget(TypedArray typedArray) {


    }

}
