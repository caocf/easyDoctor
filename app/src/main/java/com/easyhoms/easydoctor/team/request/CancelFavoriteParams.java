package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 医生取消收藏用户
 */
public class CancelFavoriteParams extends RequestParams {
    public  String yxId;
    public  long companyId;

    public CancelFavoriteParams(String yxId,long companyId) {
        super(Constants.HOST+"/link/cancel_favorite.jhtml");
        this.yxId = yxId;
        this.companyId=companyId;
    }
}
