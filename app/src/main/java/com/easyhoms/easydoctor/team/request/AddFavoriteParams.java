package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 医生收藏用户
 */
public class AddFavoriteParams extends RequestParams {
    public  String yxId;

    public AddFavoriteParams(String yxId) {
        super(ConstantValues.HOST+"/link/add_favorite.jhtml");
        this.yxId = yxId;
    }
}
