package com.easyhoms.easydoctor.my.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.adapter.CommAdapter;
import com.easyhoms.easydoctor.common.response.Hospital;

import org.xutils.view.annotation.BindView;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 我的-选择医院
 */

public class MyChooseHospitalAdapter extends CommAdapter<Hospital> {
    private ChooseCallback mCallback;

    public MyChooseHospitalAdapter(Context context, ArrayList<Hospital> list) {
        super(context, list);
    }

    public MyChooseHospitalAdapter(Context context, ArrayList<Hospital> list, ChooseCallback callback) {
        super(context, list);
        mCallback = callback;
    }

    @Override
    protected View convert(final Hospital hospital, View convertView, Context context, final int position, int layoutId) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_my_choose_hospital, null);
            viewHolder = new ViewHolder();
            x.view().inject(viewHolder, convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mCheckRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallback.choose(hospital.id);
            }
        });

        viewHolder.mCheckImg.setVisibility(hospital.isChecked?View.VISIBLE:View.INVISIBLE);
        viewHolder.mNameTv.setText(hospital.companyName);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.hospital_name_tv)
        TextView mNameTv;
        @BindView(R.id.check_rl)
        RelativeLayout mCheckRl;
        @BindView(R.id.hospital_checked_img)
        ImageView mCheckImg;
    }


    public interface ChooseCallback{
        void choose(int hosId);
    }
}
