package com.easyhoms.easydoctor.register.request;


import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 云信发送短信验证码
 */
public class SendsmsParams extends RequestParams {
    public String platformId;
    public String mobile;

    public SendsmsParams(String platformId, String mobile) {
        super(Constants.HOST+"/register/register_sendsms.jhtml");
        this.platformId = platformId;
        this.mobile = mobile;
    }
}
