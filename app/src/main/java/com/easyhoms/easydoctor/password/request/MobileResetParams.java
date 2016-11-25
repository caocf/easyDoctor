package com.easyhoms.easydoctor.password.request;


import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 更改绑定手机
 */
public class MobileResetParams extends RequestParams {
    public String securityCode;
    public String newPasswd;

    public MobileResetParams(String securityCode, String newPasswd) {
        super(Constants.HOST + "/staff/mobile_reset.jhtml");
        this.securityCode = securityCode;
        this.newPasswd = newPasswd;
    }


}
