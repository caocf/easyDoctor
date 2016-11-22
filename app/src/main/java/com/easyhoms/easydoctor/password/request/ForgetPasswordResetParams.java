package com.easyhoms.easydoctor.password.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 忘记密码重置
 */
public class ForgetPasswordResetParams extends RequestParams {
    public String mobile;
    public String password;

    public ForgetPasswordResetParams(String mobile, String password) {

        //更改路径即可
        super(ConstantValues.HOST + "/login/forget_passwd_reset.jhtml");
        this.mobile = mobile;
        this.password = password;
    }


}
