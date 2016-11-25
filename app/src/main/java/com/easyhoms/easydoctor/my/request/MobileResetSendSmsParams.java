package com.easyhoms.easydoctor.my.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 更改绑定手机发送短信
 */

public class MobileResetSendSmsParams extends RequestParams {
    public String newMobile;

    public MobileResetSendSmsParams(String newMobile) {
        super(Constants.HOST+"/staff/mobile_reset_sendsms.jhtml");
        this.newMobile = newMobile;
    }
}
