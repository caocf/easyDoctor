package com.easyhoms.easydoctor.common.manager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.LogUtils;
import com.netease.nim.DemoCache;
import com.netease.nim.cache.DataCacheManager;
import com.netease.nim.preference.Preferences;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import org.xutils.common.util.MD5;


/**
 * 网易云信Manager
 */
public class ImManager {

    public static String mToken;
    public static String mAccount;
    private static AbortableFuture<LoginInfo> mLoginRequest;

    /**
     * 获取token
     */
    public static String tokenFromPassword(Context context, String password) {
        String appKey = readAppKey(context);
        boolean isDemo = "45c6af3c98409b18a84451215d0bdd6e".equals(appKey)
                || "fe416640c8e8a72734219e1847ad2547".equals(appKey);

        return isDemo ? MD5.md5(password) : password;
    }

    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void login(Context context, String account, final ImLoginCallback callback) {
        //设置的云信密码统一为123456
        String password = "123456";
        LogUtils.i("loginId: " + account);
        mAccount = account;
        mToken = tokenFromPassword(context, password);
        mToken = "123456";
        mLoginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, mToken));
        mLoginRequest.setCallback(new RequestCallback<LoginInfo>() {

            @Override
            public void onSuccess(LoginInfo loginInfo) {
                LogUtils.i("账号id:"+loginInfo.getAccount());
                onLoginDone();
                DemoCache.setAccount(mAccount);
                saveLoginInfo(mAccount, mToken);

                // 构建缓存
                DataCacheManager.buildDataCacheAsync();

                callback.success();
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                callback.fail();
                if (code == 302 || code == 404) {
                    //   startActivity(new Intent(mContext, MainActivity.class));
                    CommonUtils.showToast("帐号或密码错误");
                } else {
                    CommonUtils.showToast("登录失败: " + code);
                }
            }

            @Override
            public void onException(Throwable throwable) {
                onLoginDone();
                callback.fail();
            }
        });
    }

    public static void onLoginDone() {
        mLoginRequest = null;
    }

    public static void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

    public interface ImLoginCallback {
        void success();

        void fail();
    }




}
