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


/**
 * Created by 德医互联 on 2016/8/3.
 */
public class DetailMenu extends RelativeLayout{
    private TextView mTv ;
    private ImageView mIv ;
    private View mVw;

    public DetailMenu(Context context) {
        super(context);
    }

    public DetailMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_view_menu, this);

        mTv = (TextView) findViewById(R.id.dm_title_tv);
        mIv = (ImageView) findViewById(R.id.dm_icon_img);
        mVw = findViewById(R.id.dm_line_v);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.DetailMenu);
        //设置值
        initWidget(typedArray);
    }

    private void initWidget(TypedArray typedArray) {

        String textString = typedArray.getString(R.styleable.DetailMenu_dm_title);
        mTv.setText(textString);

        int imageSrc = typedArray.getResourceId(R.styleable.DetailMenu_dm_icon,0);
        mIv.setImageResource(imageSrc);

        boolean lineBottomVisible = typedArray.getBoolean(R.styleable.DetailMenu_dm_line,true);
        if(lineBottomVisible){
            mVw.setVisibility(VISIBLE);
        }else{
            mVw.setVisibility(GONE);
        }

        //资源回收
        typedArray.recycle();
    }

    /**
     * image点击事件的回调
     * textview点击事件
     * @param listener
     */
    public void onItemClick(final OnItemClickListener listener)
    {
        mIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageClick();
            }
        });
        mTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTvClick();
            }
        });
    }

    public interface OnItemClickListener
    {
        public void onImageClick();
        public void onTvClick();
    }
}
