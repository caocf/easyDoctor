package com.easyhoms.easydoctor.login.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * Created by 德医互联 on 2016/10/18.
 */
public class LoginParams extends RequestParams {
    public String mobile;
    public String password;

    public LoginParams(String password, String mobile) {

        super(ConstantValues.HOST + "/login/login.jhtml");
        this.password = password;
        this.mobile = mobile;
    }
}
