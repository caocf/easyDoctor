package com.easyhoms.easydoctor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.netease.nim.common.ui.imageview.HeadImageView;


/**
 * 设置中menuItem
 */
public class TeamItem extends RelativeLayout {

    HeadImageView mMyHeadHiv;
    TextView mTitleTv;
    ImageView mMyTeamSelectedImg;

    private Context mContext;
    public TeamItem(Context context) {
        super(context);
        mContext = context;
    }

    public TeamItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.custom_view_team_menu, this);

        mMyHeadHiv = (HeadImageView) findViewById(R.id.my_head_hiv);
        mTitleTv = (TextView) findViewById(R.id.title_tv);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TeamItem);

        int imgId=typedArray.getResourceId(R.styleable.TeamItem_ti_img,R.drawable.ic_create_team);
        if (imgId!=0) {
            mMyHeadHiv.setImageResource(imgId);
        }
        String title = typedArray.getString(R.styleable.TeamItem_ti_title);
        mTitleTv.setText(title);


        //资源回收
        typedArray.recycle();
    }

    public void setRightTv(String text){
        mTitleTv.setText(text);
    }
    public void setRightTv(int textId){
        mTitleTv.setText(textId);
    }

    public void setImgUrl(String url){
        CommonUtils.loadImg(Constants.HOST_HEAD+"/"+url,mMyHeadHiv);
    }
    public void setImgSrc(int imgId){
        mMyHeadHiv.setImageResource(imgId);
    }

}
