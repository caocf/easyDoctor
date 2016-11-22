/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easyhoms.easydoctor.message.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.message.listener.OnItemClickListener;
import com.netease.nim.common.ui.imageview.HeadImageView;
import com.netease.nim.common.util.sys.TimeUtil;
import com.netease.nim.uinfo.UserInfoHelper;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import org.xutils.view.annotation.BindView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class RecentContactAdapter extends SwipeMenuAdapter<RecentContactAdapter.DefaultViewHolder> {

    public static final int VIEW_TYPE_MENU_NONE = 1;
    public static final int VIEW_TYPE_MENU_SINGLE = 2;
    public static final int VIEW_TYPE_MENU_MULTI = 3;
    public static final int VIEW_TYPE_MENU_LEFT = 4;

    private List<RecentContact> mViewTypeBeanList;

    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private ArrayList<RecentContact> mData;

    public RecentContactAdapter(Context context, List<RecentContact> viewTypeBeanList) {
        this.mViewTypeBeanList = viewTypeBeanList;
        this.mContext=context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        RecentContact contact=mViewTypeBeanList.get(position);
        if (contact.getSessionType()== SessionTypeEnum.Team){
            return VIEW_TYPE_MENU_MULTI;
        }
        return VIEW_TYPE_MENU_SINGLE;
    }

    @Override
    public int getItemCount() {
        return mViewTypeBeanList == null ? 0 : mViewTypeBeanList.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_contact, parent, false);
    }

    @Override
    public RecentContactAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView,mContext);
    }

    @Override
    public void onBindViewHolder(RecentContactAdapter.DefaultViewHolder holder, int position) {
        holder.setData(mViewTypeBeanList.get(position));
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    public void setData(ArrayList<RecentContact> data) {
        mViewTypeBeanList = data;
        notifyDataSetChanged();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recent_head_img)
        HeadImageView mHeadImg;
        @BindView(R.id.recent_message_tv)
        TextView mMessageTv;
        @BindView(R.id.recent_name_tv)
        TextView mNameTv;
        @BindView(R.id.recent_time_tv)
        TextView mTimeTv;
        @BindView(R.id.recent_unread_tv)
        TextView mUnreadTv;
        @BindView(R.id.recent_system_dot_tv)
        TextView mSystemDotTv;
        OnItemClickListener mOnItemClickListener;

        Context mContext;
        public DefaultViewHolder(View itemView, Context context) {
            super(itemView);
            mContext=context;
            x.view().inject(this,itemView);
            itemView.setOnClickListener(this);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(RecentContact recentContact) {
            MsgStatusEnum status = recentContact.getMsgStatus();
            this.mMessageTv.setText(recentContact.getContent());
            this.mTimeTv.setText(TimeUtil.getTimeShowString(recentContact.getTime()));
            if(recentContact.getMsgType()!= MsgTypeEnum.custom){

                this.mHeadImg.loadBuddyAvatar(recentContact.getContactId());
                int count=recentContact.getUnreadCount();
                //  count=count>99?99:count;//大于99条  显示99
                this.mUnreadTv.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
                this.mUnreadTv.setText(count+"");
                this.mMessageTv.setTextColor(mContext.getResources().getColor(R.color.text_body_weak));
                this.mSystemDotTv.setVisibility(View.GONE);
                this.mNameTv.setText(UserInfoHelper.getUserTitleName(recentContact.getContactId(), recentContact.getSessionType()));
            }

            if (status== MsgStatusEnum.fail) {
                Drawable drawable=mContext.getDrawable(R.drawable.nim_g_ic_failed_small);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                this.mMessageTv.setCompoundDrawables(drawable,null,null,null);
                this.mMessageTv.setCompoundDrawablePadding(4);
            }else{
                this.mMessageTv.setCompoundDrawables(null,null,null,null);
            }

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
