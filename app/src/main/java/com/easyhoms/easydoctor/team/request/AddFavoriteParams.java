package com.easyhoms.easydoctor.team.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 医生收藏用户
 */
public class AddFavoriteParams extends RequestParams {
    public  String yxId;
    public  long companyId;


    public AddFavoriteParams(String yxId,long companyId) {
        super(Constants.HOST+"/link/add_favorite.jhtml");
        this.yxId = yxId;
        this.companyId=companyId;
    }
}
