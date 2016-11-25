package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 查询群聊历史信息
 */
public class GetMembersParams extends RequestParams {

    public long teamId;
    public long companyId;

    public GetMembersParams(long teamId, long companyId) {
        super(Constants.HOST+"/team/get_members.jhtml");
        this.teamId = teamId;
        this.companyId = companyId;
    }
}
