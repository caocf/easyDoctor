package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 医生备注收藏用户
 */
public class AddAliasParams extends RequestParams {
    public  String yxId;
    public  String alias;
    public int companyId;

    public AddAliasParams(String yxId,String alias,int companyId) {
        super(Constants.HOST+"/link/add_alias.jhtml");
        this.yxId = yxId;
        this.alias = alias;
        this.companyId = companyId;
    }
}
