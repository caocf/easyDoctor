package com.easyhoms.easydoctor.team.response;

/**
 * Created by Administrator on 2016/3/30.
 */
public class TeamName {


    /**
     * id : 1
     * groupName : 未分组
     * doctorId : 1
     * orderby : 1
     * createDate : 2016-03-21 17:18:27
     */
    public String id;
    public String groupName;
    public String doctorId;
    public String orderby;
    public String createDate;

    public TeamName(String id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }
}
