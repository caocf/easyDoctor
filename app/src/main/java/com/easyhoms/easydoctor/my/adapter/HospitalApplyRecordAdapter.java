package com.easyhoms.easydoctor.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.adapter.CommAdapter;
import com.easyhoms.easydoctor.common.bean.BindHospitalRecord;

import org.xutils.view.annotation.BindView;
import org.xutils.x;

import java.util.List;

/**
 * Created by 德医互联 on 2016/12/2.
 */

public class HospitalApplyRecordAdapter extends CommAdapter<BindHospitalRecord> {


    public HospitalApplyRecordAdapter(Context context, List<BindHospitalRecord> list) {
        super(context, list);
    }

    @Override
    protected View convert(BindHospitalRecord bindHospitalRecord, View convertView, Context context, int position, int layoutId) {
        ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hospital_apply_record,null);
            holder = new ViewHolder();
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (bindHospitalRecord.hospital!=null){
            holder.mNameTv.setText(bindHospitalRecord.hospital);
        }
        if (bindHospitalRecord.time!=null){
            holder.mTimeTv.setText(bindHospitalRecord.time);
        }
        if (bindHospitalRecord.status == 0){
            holder.mStatusTv.setText("已拒绝");
            holder.mStatusTv.setTextColor(mContext.getResources().getColor(R.color.letter_red));
        }else {
            holder.mStatusTv.setText("审核中");
            holder.mStatusTv.setTextColor(mContext.getResources().getColor(R.color.main_color_blue));
        }
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.hospital_name_tv)
        TextView mNameTv;
        @BindView(R.id.hospital_time_tv)
        TextView mTimeTv;
        @BindView(R.id.hospital_status_tv)
        TextView mStatusTv;
    }

}
