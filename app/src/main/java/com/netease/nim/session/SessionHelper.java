package com.netease.nim.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.easyhoms.easydoctor.R;
import com.netease.nim.NimUIKit;
import com.netease.nim.common.ui.popupmenu.NIMPopupMenu;
import com.netease.nim.common.ui.popupmenu.PopupMenuItem;
import com.netease.nim.model.TeamExtras;
import com.netease.nim.model.TeamRequestCode;
import com.netease.nim.session.extension.CustomAttachParser;
import com.netease.nim.session.extension.StickerAttachment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * UIKit自定义消息界面用法展示类
 */
public class SessionHelper {

    private static final int ACTION_PATIENT_DETAIL = 0;
    private static final int ACTION_DIAGNOSIS_ADVICE = 1;
    private static final int ACTION_END = 2;
    private static final int ACTION_HISTORY = 3;

    private static SessionCustomization reportCustomization;
    private static SessionCustomization p2pCustomization;
    private static SessionCustomization freeP2pCustomization;
    private static SessionCustomization teamCustomization;
    private static SessionCustomization doctorP2pCustomization;

    private static NIMPopupMenu popupMenu;
    private static List<PopupMenuItem> menuItemList;


    public static int mType;

    public static void init() {
        // 注册自定义消息附件解析器
        NIMClient.getService(MsgService.class).registerCustomAttachmentParser(new CustomAttachParser());

        // 注册各种扩展消息类型的显示ViewHolder
        registerViewHolders();

        // 设置会话中点击事件响应处理
        setSessionListener();
    }

    // 定制化单聊界面。如果使用默认界面，返回null即可
    public static SessionCustomization getTextP2pCustomization() {

        p2pCustomization = new SessionCustomization() {
            // 由于需要Activity Result， 所以重载该函数。
            @Override
            public void onActivityResult(final Activity activity, int requestCode, int resultCode, Intent data) {
                super.onActivityResult(activity, requestCode, resultCode, data);
            }

            @Override
            public MsgAttachment createStickerAttachment(String category, String item) {
                return new StickerAttachment(category, item);
            }
        };

        p2pCustomization.withSticker = true;

        // 定制ActionBar右边的按钮，可以加多个
        ArrayList<SessionCustomization.OptionsButton> buttons = new ArrayList<>();
        SessionCustomization.OptionsButton cloudMsgButton = new SessionCustomization.OptionsButton() {
            @Override
            public void onClick(Context context, View view, String sessionId) {
                initPopuptWindow(context, view, sessionId, SessionTypeEnum.P2P);
            }
        };
        cloudMsgButton.iconId = R.drawable.ic_add_photo;
        buttons.add(cloudMsgButton);

        p2pCustomization.buttons = buttons;

        SessionEventListener listener = new SessionEventListener() {
            @Override
            public void onAvatarClicked(Context context, IMMessage message) {
            }

            @Override
            public void onAvatarLongClicked(Context context, IMMessage message) {
                // 一般用于群组@功能，或者弹出菜单，做拉黑，加好友等功能
            }
        };

        return p2pCustomization;
    }


    private static void registerViewHolders() {
       // NimUIKit.registerMsgItemViewHolder(PatientAttachment.class, MsgViewHolderPatient.class);
    }

    private static void setSessionListener() {
        SessionEventListener listener = new SessionEventListener() {
            @Override
            public void onAvatarClicked(Context context, IMMessage message) {
            }

            @Override
            public void onAvatarLongClicked(Context context, IMMessage message) {
            }
        };

        NimUIKit.setSessionListener(listener);
    }

    private static void initPopuptWindow(Context context, View view, String sessionId, SessionTypeEnum sessionTypeEnum) {
        if (popupMenu == null) {
            menuItemList = new ArrayList<>();
            popupMenu = new NIMPopupMenu(context, menuItemList, listener);
        }
        menuItemList.clear();
        menuItemList.addAll(getMoreMenuItems(context, sessionId, sessionTypeEnum));
        popupMenu.notifyData();
        popupMenu.show(view);
    }

    private static NIMPopupMenu.MenuItemClickListener listener = new NIMPopupMenu.MenuItemClickListener() {
        @Override
        public void onItemClick(final PopupMenuItem item) {
            switch (item.getTag()) {

            }
        }
    };

    private static List<PopupMenuItem> getMoreMenuItems(Context context, String sessionId, SessionTypeEnum sessionTypeEnum) {
        List<PopupMenuItem> moreMenuItems = new ArrayList<PopupMenuItem>();

        return moreMenuItems;
    }

    public static SessionCustomization getTeamCustomization() {
        if (teamCustomization == null) {
            teamCustomization = new SessionCustomization() {
                @Override
                public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
                    if (requestCode == TeamRequestCode.REQUEST_CODE) {
                        if (resultCode == Activity.RESULT_OK) {
                            String reason = data.getStringExtra(TeamExtras.RESULT_EXTRA_REASON);
                            boolean finish = reason != null && (reason.equals(TeamExtras
                                    .RESULT_EXTRA_REASON_DISMISS) || reason.equals(TeamExtras.RESULT_EXTRA_REASON_QUIT));
                            if (finish) {
                                activity.finish(); // 退出or解散群直接退出多人会话
                            }
                        }
                    }
                }

                @Override
                public MsgAttachment createStickerAttachment(String category, String item) {
                    return new StickerAttachment(category, item);
                }
            };


            teamCustomization.withSticker = true;
        }

        return teamCustomization;
    }

}
