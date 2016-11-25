package com.netease.nim.session.viewholder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.netease.nim.NimUIKit;
import com.netease.nim.cache.TeamDataCache;
import com.netease.nim.session.helper.TeamNotificationHelper;
import com.netease.nimlib.sdk.team.model.Team;

import java.util.Date;


public class MsgViewHolderNotification extends MsgViewHolderBase {

    protected TextView notificationTextView;
    protected TextView notificationTimeView;
    protected RelativeLayout notificationRl;


    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_notification;
    }

    @Override
    protected void inflateContentView() {
        notificationTextView = (TextView) view.findViewById(R.id.message_item_notification_label);
        notificationTimeView = (TextView) view.findViewById(R.id.notification_time_tv);
        notificationRl = (RelativeLayout) view.findViewById(R.id.notification_rl);


    }

    @Override
    protected void bindContentView() {
          Team team= TeamDataCache.getInstance().getTeamById(message.getSessionId());
          int count=team.getMemberCount();
         // String result=TeamNotificationHelper.getTeamNotificationText(message, message.getSessionId());
          if(count>=3&&mLastMember.equals(NimUIKit.getAccount())){
              notificationRl.setVisibility(View.VISIBLE);
              notificationTimeView.setText(CommonUtils.getDateTime(new Date(message.getTime()), Constants.DATE_CHINESE_3));
          }else{
              notificationRl.setVisibility(View.GONE);
          }

    }

    protected String getDisplayText() {


        return TeamNotificationHelper.getTeamNotificationText(message, message.getSessionId());
    }

//    private void handleTextNotification(String text) {
//        notificationTextView.setText(text);
//        notificationTextView.setMovementMethod(LinkMovementMethod.getInstance());
//    }

    @Override
    protected boolean isMiddleItem() {
        return true;
    }


    public void getNotificationMsg() {



    }
}

