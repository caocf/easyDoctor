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
package com.easyhoms.easydoctor.team.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.message.listener.OnItemClickListener;
import com.easyhoms.easydoctor.team.response.Doctor;
import com.netease.nim.common.ui.imageview.HeadImageView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import org.xutils.view.annotation.BindView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MenuAdapter extends SwipeMenuAdapter<MenuAdapter.DefaultViewHolder> {

    private List<Doctor> titles;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public MenuAdapter(Context context,List<Doctor> titles) {
        this.titles = titles;
        this.mContext=context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_team_member, parent, false);
    }

    @Override
    public MenuAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.DefaultViewHolder holder, int position) {
        holder.setData(titles.get(position));
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mOnItemClickListener.onItemClick(position);
            }
        });
    }

    public void setData(ArrayList<Doctor> data) {
        titles= data;
        notifyDataSetChanged();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.member_name_tv)
        TextView mNameTv;
        @BindView(R.id.member_head_hiv)
        HeadImageView mHeadHiv;

        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(Doctor doctor) {

            this.mNameTv.setText(doctor.name);
            CommonUtils.loadImg(Constants.HOST_HEAD+"/"+doctor.imagePath,mHeadHiv);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
               mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
