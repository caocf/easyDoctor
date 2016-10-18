package com.easyhoms.easydoctor.common.manager;

import com.easyhoms.easydoctor.common.utils.HttpClient;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.login.request.LoginParams;

/**
 * 网络请求
 */
public class BaseManager {
    public static final String Token = "access_token";


    public static void login(String mPhone, String mPassword, NetCallback mCallback) {
        LoginParams params = new LoginParams(mPassword,mPhone);
        HttpClient.post(params,mCallback);

    }
}
