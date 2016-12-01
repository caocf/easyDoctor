package com.easyhoms.easydoctor.team.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.adapter.CommAdapter;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.team.response.Doctor;
import com.netease.nim.common.ui.imageview.HeadImageView;

import org.xutils.view.annotation.BindView;
import org.xutils.x;

import java.util.ArrayList;

/**
 *
 */

public class MemberCheckAdapter extends CommAdapter<Doctor> implements SectionIndexer {
    private CheckCallBack mCallBack;
    private boolean mSessionVisible;

    public MemberCheckAdapter(Context context, ArrayList<Doctor> list,CheckCallBack checkCallBack,boolean sessionVisible) {
        super(context, list);
        this.mCallBack=checkCallBack;
        this.mSessionVisible=sessionVisible;
    }

    public void setSessionVisible(boolean sessionVisible) {
        mSessionVisible = sessionVisible;
    }

    @Override
    protected View convert(Doctor doctor, View convertView, Context context, final int position, int layoutId) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_member_check, null);
            viewHolder = new ViewHolder();
            x.view().inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(mSessionVisible){
            // 根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position);

            // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)) {
                viewHolder.mLetterTv.setVisibility(View.VISIBLE);
                viewHolder.mLetterTv.setText(doctor.sortLetters);
            } else {
                viewHolder.mLetterTv.setVisibility(View.GONE);
            }
        }else{
            viewHolder.mLetterTv.setVisibility(View.GONE);
        }

        viewHolder.mCheckCb.setSelected(doctor.isChoose);
        viewHolder.mCheckCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.check(position);
            }
        });
        viewHolder.mNameTv.setText(doctor.name);
        CommonUtils.loadImg(Constants.HOST_HEAD+"/"+doctor.imagePath,viewHolder.mHeadHiv,R.drawable.doctors_default_head);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.member_name_tv)
        TextView mNameTv;
        @BindView(R.id.member_check_cb)
        ImageView mCheckCb;
        @BindView(R.id.treat_item_letter_tv)
        TextView mLetterTv;
        @BindView(R.id.head_hiv)
        HeadImageView mHeadHiv;
    }

    public interface CheckCallBack{
        void check(int position);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mList.get(position).sortLetters.charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mList.get(i).sortLetters;

            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }
    @Override
    public Object[] getSections() {
        return null;
    }

}
