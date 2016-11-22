package com.easyhoms.easydoctor.team.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.team.response.Group;
import com.easyhoms.easydoctor.team.response.PatientByGroup;

import org.xutils.view.annotation.BindView;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by zhanghua on 2015/12/29.
 */
public class MedicalRecordExpandAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<Group> mGroupStrings;
    ArrayList<ArrayList<PatientByGroup>> mItems;
    GroupClick mGroupClick;

    public MedicalRecordExpandAdapter(Context mContext, ArrayList<Group> mGroupStrings, ArrayList<ArrayList<PatientByGroup>> mItems) {
        this.mContext = mContext;
        this.mGroupStrings = mGroupStrings;
        this.mItems = mItems;
    }

    public void setGroupClick(GroupClick groupClick) {
        mGroupClick = groupClick;
    }

    @Override
    public int getGroupCount() {
        return mGroupStrings.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(mItems.size()==0){
            return 0;
        }
        if(mItems.get(groupPosition)==null){
            return 0;
        }
        return mItems.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItems.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_crm_group, null);
            viewHolder = new GroupViewHolder();
            x.view().inject(viewHolder,convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        viewHolder.mGroupNameText.setText(mGroupStrings.get(groupPosition).groupName);

        viewHolder.mArrawImg.setSelected(isExpanded ? true : false);

        viewHolder.mGroupRl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mGroupClick.groupLongClick(groupPosition);
                return true;
            }
        });
        viewHolder.mGroupRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupClick.groupClick(groupPosition, isExpanded);
            }
        });

        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) viewHolder.mLineV.getLayoutParams();
        if (isExpanded) {
            lp.setMargins(CommonUtils.dp2px(mContext,60),0,0,0);
        }else{
            lp.setMargins(0,0,0,0);
        }
        viewHolder.mLineV.setLayoutParams(lp);


                //    viewHolder.mGroupCountText.setText(mGroupStrings.get(groupPosition).createDate);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PatientByGroup patient = (PatientByGroup) getChild(groupPosition, childPosition);
        ChildViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_crm_child, null);
            viewHolder = new ChildViewHolder();

            x.view().inject(viewHolder,convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }

      //  int i = (int) (Math.random() * 20 + 15);

     //   viewHolder.mHeadImg.setImageResource(R.drawable.ic_patient_head);

        viewHolder.mNameText.setText(patient.name);
     //   viewHolder.mCountText.setText(patient.age+"");
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        @BindView(R.id.tv_group_name)
        TextView mGroupNameText;
        @BindView(R.id.group_rl)
        RelativeLayout mGroupRl;
        @BindView(R.id.tv_group_count)
        TextView mGroupCountText;
        @BindView(R.id.img_arraw)
        ImageView mArrawImg;
        @BindView(R.id.line)
        View mLineV;
    }

    static class ChildViewHolder {
        @BindView(R.id.head_img)
        ImageView mHeadImg;
        @BindView(R.id.menu_title_tv)
        TextView mNameText;

    }

    public void setData(ArrayList<Group> mGroupStrings, ArrayList<ArrayList<PatientByGroup>> mItems) {
        this.mItems=mItems;
        this.mGroupStrings=mGroupStrings;
        notifyDataSetInvalidated();
    }

    public interface GroupClick{

        void groupLongClick(int position);
        void groupClick(int position, boolean isExpanded);
    }




}