package com.netease.nim.session.action;

import com.easyhoms.easydoctor.R;
import com.netease.nim.session.SessionCustomization;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;

/**
 * Created by hzxuwen on 2015/6/12.
 */
public class ImageAction extends PickImageAction {

    public ImageAction() {
        super(R.drawable.ic_add_photo, R.string.image, true);
    }
    public ImageAction(SessionCustomization customization) {
        super(R.drawable.ic_add_photo, R.string.image, true,customization);
    }

    @Override
    protected void onPicked(File file) {
        IMMessage message = MessageBuilder.createImageMessage(getAccount(), getSessionType(), file, file.getName());
        sendMessage(message);
    }
}

