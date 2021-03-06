package com.netease.nim.common.ui.popupmenu;

import android.content.Context;

import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

/**
 * 菜单显示条目
 */
public class PopupMenuItem {
    public boolean selected;
    public String iconUrl;

    public int tag;
    private String strTag;

    private int icon;

    public String title;

    private Context context;

    private String sessionId;

    private SessionTypeEnum sessionTypeEnum;

    public PopupMenuItem(int tag, int icon, String title) {
        this.tag = tag;
        this.icon = icon;
        this.title = title;
    }
    public PopupMenuItem(String tag, int icon, String title) {
        this.strTag = tag;
        this.icon = icon;
        this.title = title;
    }

    public PopupMenuItem(String iconUrl, int tag, String title,boolean selected) {
        this.selected = selected;
        this.iconUrl = iconUrl;
        this.tag = tag;
        this.title = title;
    }

    /**
     * 只有文字
     *
     * @param tag
     * @param title
     */
    public PopupMenuItem(int tag, String title) {
        this(tag, 0, title);
    }
    public PopupMenuItem(String tag, String title) {
        this(tag, 0, title);
    }

    public PopupMenuItem(Context context, int tag, String sessionId, SessionTypeEnum sessionTypeEnum, String title) {
        this(tag, title);
        this.context = context;
        this.sessionId = sessionId;
        this.sessionTypeEnum = sessionTypeEnum;
    }
    public PopupMenuItem(Context context, String tag, String sessionId, SessionTypeEnum sessionTypeEnum, String title) {
        this(tag, title);
        this.context = context;
        this.sessionId = sessionId;
        this.sessionTypeEnum = sessionTypeEnum;
    }

    public int getTag() {
        return tag;
    }
    public String getStrTag() {
        return strTag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public SessionTypeEnum getSessionTypeEnum() {
        return sessionTypeEnum;
    }

    public void setSessionTypeEnum(SessionTypeEnum sessionTypeEnum) {
        this.sessionTypeEnum = sessionTypeEnum;
    }
}
