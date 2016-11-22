package com.easyhoms.easydoctor.register.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 云信发送短信验证码
 */
public class RegisterVerifyParams extends RequestParams {
    public String platformId;
    public String mobile;
    public String securityCode;

    public RegisterVerifyParams(String platformId, String mobile,String securityCode) {
        super(ConstantValues.HOST+"/api/register/register_verify.jhtml");
        this.platformId = platformId;
        this.mobile = mobile;
        this.securityCode=securityCode;
    }
}
