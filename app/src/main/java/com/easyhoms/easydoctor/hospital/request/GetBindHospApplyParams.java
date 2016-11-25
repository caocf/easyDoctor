package com.easyhoms.easydoctor.hospital.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 获取医生绑定医院的申请
 */

public class GetBindHospApplyParams extends RequestParams {

    public GetBindHospApplyParams() {
        super(Constants.HOST+"/staffAction/get_bindHosp_apply.jhtml");
    }
}
