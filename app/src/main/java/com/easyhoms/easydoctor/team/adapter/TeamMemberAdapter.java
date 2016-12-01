package com.easyhoms.easydoctor.team.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.adapter.CommAdapter;
import com.easyhoms.easydoctor.team.response.Doctor;
import com.netease.nim.common.ui.imageview.HeadImageView;

import org.xutils.view.annotation.BindView;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by 德医互联 on 2016/11/2.
 */

public class TeamMemberAdapter extends CommAdapter<Doctor> {
    public TeamMemberAdapter(Context context, ArrayList<Doctor> list) {
        super(context, list);
    }

    @Override
    protected View convert(Doctor doctor, View convertView, Context context, int position, int layoutId) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_team_member, null);
            viewHolder = new ViewHolder();
            x.view().inject(viewHolder, convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
      //  CommonUtils.loadImg(Constants.HOST_HEAD+doctor.imagePath,viewHolder.mHeadHiv);
        viewHolder.mNameTv.setText(doctor.name);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.member_name_tv)
        TextView mNameTv;
        @BindView(R.id.member_head_hiv)
        HeadImageView mHeadHiv;
    }
}
