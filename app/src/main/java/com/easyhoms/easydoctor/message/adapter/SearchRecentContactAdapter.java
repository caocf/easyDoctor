package com.easyhoms.easydoctor.message.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.adapter.CommAdapter;
import com.netease.nim.cache.TeamDataCache;
import com.netease.nim.common.ui.imageview.HeadImageView;
import com.netease.nim.common.util.sys.TimeUtil;
import com.netease.nim.uinfo.UserInfoHelper;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.model.Team;

import org.xutils.view.annotation.BindView;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 最近联系人列表
 */
public class SearchRecentContactAdapter extends CommAdapter<RecentContact> {

    public SearchRecentContactAdapter(Context context, ArrayList<RecentContact> list) {
        super(context, list);
    }

    @Override
    protected View convert(RecentContact recentContact, View convertView, Context context, int position, int layoutId) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_recent_contact, null);
            viewHolder = new ViewHolder();
            x.view().inject(viewHolder,convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MsgStatusEnum status = recentContact.getMsgStatus();
        viewHolder.mMessageTv.setText(recentContact.getContent());
        viewHolder.mTimeTv.setText(TimeUtil.getTimeShowString(recentContact.getTime()));
        if(recentContact.getMsgType()!= MsgTypeEnum.custom){

            // 设置头像
            if (recentContact.getSessionType() == SessionTypeEnum.P2P) {
                viewHolder.mHeadImg.loadBuddyAvatar(recentContact.getContactId());
            } else if (recentContact.getSessionType() == SessionTypeEnum.Team) {
                Team team = TeamDataCache.getInstance().getTeamById(recentContact.getContactId());
                viewHolder.mHeadImg.loadTeamIconByTeam(team);
            }
            int count=recentContact.getUnreadCount();
            String countStr="";
            countStr=count>99?"99+":count+"";//大于99条  显示99
            viewHolder.mUnreadTv.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
            viewHolder.mUnreadTv.setText(countStr);
            viewHolder.mMessageTv.setTextColor(mContext.getResources().getColor(R.color.text_body_weak));
            viewHolder.mSystemDotTv.setVisibility(View.GONE);
            viewHolder.mNameTv.setText(UserInfoHelper.getUserTitleName(recentContact.getContactId(), recentContact.getSessionType()));
        }else{//系统消息
            viewHolder.mNameTv.setText(recentContact.getFromNick());
            viewHolder.mHeadImg.setImageResource(R.drawable.icon_remind);
            viewHolder.mMessageTv.setTextColor(mContext.getResources().getColor(R.color.main_color_blue));

            viewHolder.mUnreadTv.setVisibility(View.GONE);

            if(status== MsgStatusEnum.unread){
                viewHolder.mSystemDotTv.setVisibility(View.VISIBLE);
            }else{
                viewHolder.mSystemDotTv.setVisibility(View.GONE);
            }
        }

        if (status== MsgStatusEnum.fail) {
            Drawable drawable=mContext.getDrawable(R.drawable.nim_g_ic_failed_small);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            viewHolder.mMessageTv.setCompoundDrawables(drawable,null,null,null);
            viewHolder.mMessageTv.setCompoundDrawablePadding(4);
        }else{
            viewHolder.mMessageTv.setCompoundDrawables(null,null,null,null);
        }


        return convertView;
    }

    static class ViewHolder {
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

    }
}
