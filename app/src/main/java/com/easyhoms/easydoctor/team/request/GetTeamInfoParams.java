package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 获取群基本信息
 */
public class GetTeamInfoParams extends RequestParams {

    public long teamId;
    public long companyId;

    public GetTeamInfoParams(long teamId, long companyId) {
        super(ConstantValues.HOST+"/team/get_myteam.jhtml");
        this.teamId = teamId;
        this.companyId = companyId;
    }
}
