package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 将员工加入群聊
 */
public class AddStaffParams extends RequestParams {
    public String staffId;
    public long teamId;
    public long companyId;


    public AddStaffParams(String staffId, long teamId, long companyId) {
        super(Constants.HOST+"/team/add_staff.jhtml");
        this.staffId = staffId;
        this.teamId = teamId;
        this.companyId = companyId;
    }
}
