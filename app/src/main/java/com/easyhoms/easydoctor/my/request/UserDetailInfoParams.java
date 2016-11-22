package com.easyhoms.easydoctor.my.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 获取用户基础信息
 */
public class UserDetailInfoParams extends RequestParams {
    public String yxId;
    public boolean isFo;
    public UserDetailInfoParams(String yxId,boolean isFo) {
        super(ConstantValues.HOST_PATIENT+"/user/user_detail_info.jhtml");
        this.yxId=yxId;
        this.isFo=isFo;
    }
}
