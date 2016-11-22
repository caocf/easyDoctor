package com.easyhoms.easydoctor.password.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 更改绑定手机
 */
public class MobileResetParams extends RequestParams {
    public String securityCode;
    public String newPasswd;

    public MobileResetParams(String securityCode, String newPasswd) {
        super(ConstantValues.HOST + "/staff/mobile_reset.jhtml");
        this.securityCode = securityCode;
        this.newPasswd = newPasswd;
    }


}
