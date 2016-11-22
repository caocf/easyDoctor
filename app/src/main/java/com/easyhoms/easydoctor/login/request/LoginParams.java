package com.easyhoms.easydoctor.login.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 登录
 */
public class LoginParams extends RequestParams {
    public String mobile;
    public String password;

    public LoginParams(String mobile,String password) {

        super(ConstantValues.HOST + "/login/mobilelogin.jhtml");
        this.password = password;
        this.mobile = mobile;
    }
}
