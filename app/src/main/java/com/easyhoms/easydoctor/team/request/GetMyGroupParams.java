package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 获取医生的群信息
 */
public class GetMyGroupParams extends RequestParams {
    public String companyId;

    public GetMyGroupParams(String companyId) {
        super(ConstantValues.HOST+"/staffGroup/get_mygroup.jhtml");
        this.companyId = companyId;
    }
}
