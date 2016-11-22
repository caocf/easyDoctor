package com.easyhoms.easydoctor.my.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 更改绑定手机发送短信
 */

public class MobileResetSendSmsParams extends RequestParams {
    public String newMobile;

    public MobileResetSendSmsParams(String newMobile) {
        super(ConstantValues.HOST+"/staff/mobile_reset_sendsms.jhtml");
        this.newMobile = newMobile;
    }
}
