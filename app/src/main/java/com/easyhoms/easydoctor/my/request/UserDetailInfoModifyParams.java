package com.easyhoms.easydoctor.my.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 用户详细信息修改
 */
public class UserDetailInfoModifyParams extends RequestParams {
    public String imagePath;
    public String name;
    public String gender;
    public String birth;
    public UserDetailInfoModifyParams(String imagePath, String name, String gender, String birth ) {
        super(ConstantValues.HOST + "/staff/user_detail_info_modify.jhtml");
        this.imagePath = imagePath;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }
}
