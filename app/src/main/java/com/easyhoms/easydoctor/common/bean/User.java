package com.easyhoms.easydoctor.common.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 医生
 */
@Table(name="user")
public class User {
    @Column(name = "id",isId = true)
    public int Id;

    @Column(name = "access_token")
    public String access_token;

    @Column(name = "createDate")
    public String createDate;

    @Column(name = "modifyDate")
    public String modifyDate;

    @Column(name = "serverid")
    public int id;

    @Column(name = "mobile")
    public String mobile;

    @Column(name = "password")
    public String password;

    @Column(name = "imagePath")
    public String imagePath;

    @Column(name = "name")
    public String name;

    @Column(name = "gender")
    public String gender;

    @Column(name = "birth")
    public String birth;

    @Column(name = "hospital")
    public String hospital;

    @Column(name = "staff_im_id")
    public String staffImId;

    @Column(name = "auth_state")
    public int authState;

    @Override
    public String toString() {

        return "User{" +
                "Id=" + Id +
                ", access_token='" + access_token + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", id=" + id +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birth='" + birth + '\'' +
                ", hospital='" + hospital + '\'' +
                ", staffImId='" + staffImId + '\'' +
                '}';
    }
}
