package com.easyhoms.easydoctor.my.request;


import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 获取图片后上传图片地址
 */
public class UserHeadModifyParams extends RequestParams {
    public String imagePath;
    public UserHeadModifyParams(String imagePath) {
        super(Constants.HOST + "/staff/user_head_modify.jhtml");
        this.imagePath = imagePath;
    }
}
