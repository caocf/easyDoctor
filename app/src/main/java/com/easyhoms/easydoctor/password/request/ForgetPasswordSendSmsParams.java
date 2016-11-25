package com.easyhoms.easydoctor.password.request;


import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 忘记密码重置
 */
public class ForgetPasswordSendSmsParams extends RequestParams {
    public String mobile;

    public ForgetPasswordSendSmsParams(String mobile) {

        //更改路径即可
        super(Constants.HOST + "/login/forget_passwd_sendsms.jhtml");
        this.mobile = mobile;
    }


}
