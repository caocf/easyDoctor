package com.easyhoms.easydoctor.register.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 注册用户
 */
public class RegisterUserParams extends RequestParams {
    public String platformId;
    public String mobile;
    public String name;
    public String password;
    public String securityCode;

    public RegisterUserParams(String platformId, String mobile,String name, String password,String securityCode) {
        super(ConstantValues.HOST+"/register/register.jhtml");
        this.platformId = platformId;
        this.mobile = mobile;
        this.password =password;
        this.name =name;
        this.securityCode =securityCode;
    }
}
