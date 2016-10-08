package com.easyhoms.easydoctor.common.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.adapter.CommAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 顶部TopTab切换adapter
 */
public class TopTabAdapter extends CommAdapter<String> {

    public TopTabAdapter(Context context, ArrayList<String> list) {
        super(context, list);
    }

    @Override
    protected View convert(String s, View convertView, Context context, int position, int layoutId) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_top_tab, null);
            viewHolder = new ViewHolder();
            x.view().inject(viewHolder, convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTabTv.setText(s);

        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.top_tab_rl)
        RelativeLayout mTopRl;
        @ViewInject(R.id.top_tab_tv)
        TextView mTabTv;
    }
}
