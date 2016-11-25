package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 创建医生的群信息
 */
public class StaffGroupCreateParams extends RequestParams {
    public String companyId;
    public String staffId;

    public StaffGroupCreateParams(String companyId) {
        super(Constants.HOST+"/staffGroup/create.jhtml");
        this.companyId = companyId;
    }
}
