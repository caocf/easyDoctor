package com.easyhoms.easydoctor.team.adapter;

import android.content.Context;
import android.view.View;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.adapter.CommAdapter;
import com.easyhoms.easydoctor.common.view.TeamItem;
import com.easyhoms.easydoctor.team.response.MyGroup;

import org.xutils.view.annotation.BindView;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 其他队伍
 */
public class OtherTeamAdapter extends CommAdapter<MyGroup> {

    public OtherTeamAdapter(Context context, ArrayList<MyGroup> list) {
        super(context, list);
    }

    @Override
    protected View convert(MyGroup group, View convertView, Context context, int position, int layoutId) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_team_group, null);
            viewHolder = new ViewHolder();
            x.view().inject(viewHolder, convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTeamTi.setRightTv(group.name);
        viewHolder.mTeamTi.setImgSrc(R.drawable.icon_team);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.group_ti)
        TeamItem mTeamTi;
    }
}
