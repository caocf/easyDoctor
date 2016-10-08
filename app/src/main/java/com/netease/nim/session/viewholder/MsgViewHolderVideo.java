package com.netease.nim.session.viewholder;


import com.easyhoms.easydoctor.R;
import com.netease.nim.common.ui.media.BitmapDecoder;
import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;


/**
 * 视频
 */
public class MsgViewHolderVideo extends MsgViewHolderThumbBase {

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_video;
    }

    @Override
    protected void onItemClick() {
     //   WatchVideoActivity.start(context, message);
    }

    @Override
    protected String thumbFromSourceFile(String path) {
        VideoAttachment attachment = (VideoAttachment) message.getAttachment();
        String thumb = attachment.getThumbPathForSave();
        return BitmapDecoder.extractThumbnail(path, thumb) ? thumb : null;
    }
}
