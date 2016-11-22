package com.netease.nim.session.viewholder;

import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.netease.nim.session.helper.TeamNotificationHelper;


public class MsgViewHolderNotification extends MsgViewHolderBase {

    protected TextView notificationTextView;

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_notification;
    }

    @Override
    protected void inflateContentView() {
        notificationTextView = (TextView) view.findViewById(R.id.message_item_notification_label);

    }

    @Override
    protected void bindContentView() {


        if(TextUtils.isEmpty(getDisplayText())){
            notificationTextView.setVisibility(View.GONE);
        }else {
            notificationTextView.setVisibility(View.VISIBLE);
            handleTextNotification(getDisplayText());
        }

     //   handleTextNotification("");
    }

    protected String getDisplayText() {

        return TeamNotificationHelper.getTeamNotificationText(message, message.getSessionId());
    }

    private void handleTextNotification(String text) {
        notificationTextView.setText(text);
        notificationTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected boolean isMiddleItem() {
        return true;
    }
}

