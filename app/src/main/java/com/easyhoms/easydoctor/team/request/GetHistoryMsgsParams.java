package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 查询群聊历史信息
 */
public class GetHistoryMsgsParams extends RequestParams {

    public long teamId;
    public String beginTime;
    public String endTime;

    public GetHistoryMsgsParams(long teamId, String beginTime, String endTime) {
        super(ConstantValues.HOST+"/team/get_historymsgs.jhtml");
        this.teamId = teamId;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
