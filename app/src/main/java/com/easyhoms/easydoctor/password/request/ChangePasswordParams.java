package com.easyhoms.easydoctor.password.request;


import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 修改密码
 */
public class ChangePasswordParams extends RequestParams {
    public String mobile;
    public String newPassword;
    public String platformId;

    public ChangePasswordParams(String platformId, String mobile, String newPassword) {

        //更改路径即可
        super(Constants.HOST + "/api/register/register_sendsms.jhtml");
        this.mobile = mobile;
        this.newPassword = newPassword;
        this.platformId = platformId;
    }


}
