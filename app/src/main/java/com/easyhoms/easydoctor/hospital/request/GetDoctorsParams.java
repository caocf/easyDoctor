package com.easyhoms.easydoctor.hospital.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

/**
 * 获取该医院医生
 */
public class GetDoctorsParams extends RequestParams {
    public long companyId;


    public GetDoctorsParams(int companyId) {
        super(ConstantValues.HOST + "/api/company/get_doctors.jhtml");
        this.companyId = companyId;
    }
}
