package com.easyhoms.easydoctor.my.request;

import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

/**
 * 上传医生认证信息
 */
public class AddAuthInfoParams extends RequestParams {

    public String  idNumber;
    public String profDocterQualCer1;
    public String profDocterQualCer2;
    public String profDocterRegisterCer1;
    public String profDocterRegisterCer2;
    public String profTitleCer;

    public AddAuthInfoParams(String idNumber, String profDocterQualCer1, String profDocterQualCer2, String profDocterRegisterCer1, String profDocterRegisterCer2, String profTitleCer) {
        super(Constants.HOST+"/staff/add_authInfo.jhtml");
        this.idNumber = idNumber;
        this.profDocterQualCer1 = profDocterQualCer1;
        this.profDocterQualCer2 = profDocterQualCer2;
        this.profDocterRegisterCer1 = profDocterRegisterCer1;
        this.profDocterRegisterCer2 = profDocterRegisterCer2;
        this.profTitleCer = profTitleCer;
    }
}
