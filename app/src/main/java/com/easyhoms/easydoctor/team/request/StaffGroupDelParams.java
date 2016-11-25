package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 移除同事
 */
public class StaffGroupDelParams extends RequestParams {
    public String groupId;
    public String staffId;

    public StaffGroupDelParams(String groupId, String staffId) {
        super(Constants.HOST+"/staffGroup/del.jhtml");
        this.groupId = groupId;
        this.staffId = staffId;
    }
}
