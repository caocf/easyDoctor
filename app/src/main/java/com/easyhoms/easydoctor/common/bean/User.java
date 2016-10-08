package com.easyhoms.easydoctor.common.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 患者用户
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


}
