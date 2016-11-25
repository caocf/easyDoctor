package com.easyhoms.easydoctor.my.request;


import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 获取用户基础信息
 */
public class StaffDetailInfoParams extends RequestParams {
    public String yxId;
    public StaffDetailInfoParams(String yxId) {
        super(Constants.HOST+"/user_detail_info.jhtml");
        this.yxId=yxId;
    }
}
