package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 医生取消收藏用户
 */
public class CancelFavoriteParams extends RequestParams {
    public  String yxId;

    public CancelFavoriteParams(String yxId) {
        super(ConstantValues.HOST+"/link/cancel_favorite.jhtml");
        this.yxId = yxId;
    }
}
