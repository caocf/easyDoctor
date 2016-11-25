package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 添加同事
 */
public class StaffGroupAddParams extends RequestParams {
    public String groupId;
    public String staffId;

    public StaffGroupAddParams(String groupId, String staffId) {
        super(Constants.HOST+"/staffGroup/add.jhtml");
        this.groupId = groupId;
        this.staffId = staffId;
    }
}
