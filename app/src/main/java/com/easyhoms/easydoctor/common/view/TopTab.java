package com.easyhoms.easydoctor.common.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;

import java.util.ArrayList;

/**
 * Created by 德医互联 on 2016/8/10.
 */
public class TopTab extends LinearLayout {
    private GridView mTopGv;
    private Context mContext;
    private String mLables[];
    private TopTabAdapter mAdapter;
    private int mCurrentItem = 0;
    private TopTabSelect mTopTabSelect;

    public TopTab(Context context) {
        super(context);
    }

    public TopTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public TopTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        this.setBackgroundColor(Color.rgb(250, 250, 250));

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;

        ArrayList<String> items = new ArrayList<>();
        for (String lable : mLables) {
            items.add(lable);
            View item = LayoutInflater.from(mContext).inflate(R.layout.item_top_tab, null);
            TextView tv = (TextView) item.findViewById(R.id.top_tab_tv);
            tv.setText(lable);
            this.addView(item, params);
        }

        getChildAt(0).setSelected(true);

        for (int i = 0; i < getChildCount(); i++) {

            final View view = getChildAt(i);
            view.setTag(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < getChildCount(); i++) {
                        getChildAt(i).setSelected(false);
                    }
                    v.setSelected(true);
                    mTopTabSelect.topTabSelected((int)v.getTag());
                }
            });
        }
    }

    public void setLables(String[] lables, TopTabSelect topTabSelect) {
        mLables = lables;
        mTopTabSelect = topTabSelect;
        init();
    }

    public void setCurrentItem(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setSelected(false);
        }
        getChildAt(position).setSelected(true);
    }

    public interface TopTabSelect {
        void topTabSelected(int position);
    }


}
