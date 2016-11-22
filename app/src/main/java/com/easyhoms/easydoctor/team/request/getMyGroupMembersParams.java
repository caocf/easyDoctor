package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 获取群组的成员列表
 */
public class GetMyGroupMembersParams extends RequestParams {
    public long groupId;

    public GetMyGroupMembersParams(long groupId) {
        super(ConstantValues.HOST+"/staffGroup/get_groupMembers.jhtml");
        this.groupId = groupId;
    }
}
