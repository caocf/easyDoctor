package com.easyhoms.easydoctor.hospital.request;

import org.xutils.http.RequestParams;

/**
 * 关注医院,取消关注医院
 */
public class FouseHosParams extends RequestParams {
    public String companyId;

    public FouseHosParams(String uri, String companyId) {
        super(uri);
        this.companyId = companyId;
    }
}
