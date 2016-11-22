package com.easyhoms.easydoctor.password.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 忘记密码重置
 */
public class ForgetPasswordSendSmsVerifyParams extends RequestParams {
    public String mobile;
    public String securityCode;

    public ForgetPasswordSendSmsVerifyParams(String mobile, String securityCode) {

        //更改路径即可
        super(ConstantValues.HOST + "/login/forget_passwd_sendsms_verify.jhtml");
        this.mobile = mobile;
        this.securityCode = securityCode;
    }


}
