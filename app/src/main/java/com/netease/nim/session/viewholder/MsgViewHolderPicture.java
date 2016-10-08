package com.netease.nim.session.viewholder;


import com.easyhoms.easydoctor.R;
import com.netease.nim.session.activity.WatchMessagePictureActivity;


/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderPicture extends MsgViewHolderThumbBase {

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_picture;
    }

    @Override
    protected void onItemClick() {
        WatchMessagePictureActivity.start(context, message);
    }

    @Override
    protected String thumbFromSourceFile(String path) {
        return path;
    }
}
