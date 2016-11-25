package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 获取我的群
 */
public class GetMyTeamParams extends RequestParams {

    public long teamId;
    public long companyId;

    public GetMyTeamParams(long teamId, long companyId) {
        super(Constants.HOST+"/team/get_myteam.jhtml");
        this.teamId = teamId;
        this.companyId = companyId;
    }
}
