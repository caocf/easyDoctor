package com.easyhoms.easydoctor.password.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 登录修改密码(发送验证码)
 */
public class ResetPasswordSendSmsParams extends RequestParams {
    public String mobile;
    public String newPasswd;

    public ResetPasswordSendSmsParams(String mobile) {
        super(ConstantValues.HOST + "/api/passwd/reset_passwd_sendsms.jhtml");
        this.mobile = mobile;
    }
}
