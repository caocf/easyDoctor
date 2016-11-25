package com.easyhoms.easydoctor.password.request;


import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 登录修改密码(旧密码方式)
 */
public class OldPasswordResetParams extends RequestParams {
    public String oldPasswd;
    public String newPasswd;

    public OldPasswordResetParams(String oldPasswd, String newPasswd) {
        super(Constants.HOST + "/api/passwd/oldpasswd_reset.jhtml");
        this.oldPasswd = oldPasswd;
        this.newPasswd = newPasswd;
    }
}
