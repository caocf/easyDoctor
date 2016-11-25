package com.easyhoms.easydoctor.login.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 登录
 */
public class LoginParams extends RequestParams {
    public String mobile;
    public String password;

    public LoginParams(String mobile,String password) {

        super(Constants.HOST + "/login/mobilelogin.jhtml");
        this.password = password;
        this.mobile = mobile;
    }
}
