package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 创建医生的群信息
 */
public class StaffGroupCreateParams extends RequestParams {
    public String companyId;
    public String staffId;

    public StaffGroupCreateParams(String companyId) {
        super(ConstantValues.HOST+"/staffGroup/create.jhtml");
        this.companyId = companyId;
    }
}
