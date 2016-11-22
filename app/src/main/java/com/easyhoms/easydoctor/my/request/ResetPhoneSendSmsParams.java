package com.easyhoms.easydoctor.my.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 更改手机号码发送短信验证
 */
public class ResetPhoneSendSmsParams extends RequestParams {
    public String newMobile;


    public ResetPhoneSendSmsParams(String newMobile) {
        super(ConstantValues.HOST + "/api/user/mobile_reset_sendsms.jhtml");
        this.newMobile = newMobile;
    }

}
