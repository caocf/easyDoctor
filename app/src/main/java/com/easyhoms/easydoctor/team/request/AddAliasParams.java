package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 医生备注收藏用户
 */
public class AddAliasParams extends RequestParams {
    public  String yxId;
    public  String alias;

    public AddAliasParams(String yxId,String alias) {
        super(ConstantValues.HOST+"/link/add_alias.jhtml");
        this.yxId = yxId;
        this.alias = alias;
    }
}
