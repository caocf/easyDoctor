package com.easyhoms.easydoctor.common.manager;

import android.text.TextUtils;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.common.bean.User;
import com.easyhoms.easydoctor.common.request.UploadFileParams;
import com.easyhoms.easydoctor.common.utils.HttpClient;
import com.easyhoms.easydoctor.common.utils.LogUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.hospital.request.FouseHosParams;
import com.easyhoms.easydoctor.hospital.request.GetBindHospApplyParams;
import com.easyhoms.easydoctor.hospital.request.GetDoctorsParams;
import com.easyhoms.easydoctor.login.request.LoginParams;
import com.easyhoms.easydoctor.my.request.AddAuthInfoParams;
import com.easyhoms.easydoctor.my.request.MobileResetSendSmsParams;
import com.easyhoms.easydoctor.my.request.ResetPhoneSendSmsParams;
import com.easyhoms.easydoctor.my.request.StaffDetailInfoParams;
import com.easyhoms.easydoctor.my.request.UserDetailInfoModifyParams;
import com.easyhoms.easydoctor.my.request.UserDetailInfoParams;
import com.easyhoms.easydoctor.my.request.UserHeadModifyParams;
import com.easyhoms.easydoctor.password.request.ForgetPasswordResetParams;
import com.easyhoms.easydoctor.password.request.ForgetPasswordSendSmsParams;
import com.easyhoms.easydoctor.password.request.ForgetPasswordSendSmsVerifyParams;
import com.easyhoms.easydoctor.password.request.MobileResetParams;
import com.easyhoms.easydoctor.password.request.OldPasswordResetParams;
import com.easyhoms.easydoctor.password.request.ResetPasswordSendSmsParams;
import com.easyhoms.easydoctor.register.request.RegisterUserParams;
import com.easyhoms.easydoctor.register.request.RegisterVerifyParams;
import com.easyhoms.easydoctor.register.request.SendsmsParams;
import com.easyhoms.easydoctor.team.request.AddAliasParams;
import com.easyhoms.easydoctor.team.request.AddFavoriteParams;
import com.easyhoms.easydoctor.team.request.AddStaffParams;
import com.easyhoms.easydoctor.team.request.CancelFavoriteParams;
import com.easyhoms.easydoctor.team.request.GetHistoryMsgsParams;
import com.easyhoms.easydoctor.team.request.GetMembersParams;
import com.easyhoms.easydoctor.team.request.GetMyGroupMembersParams;
import com.easyhoms.easydoctor.team.request.GetMyGroupParams;
import com.easyhoms.easydoctor.team.request.GetMyTeamParams;
import com.easyhoms.easydoctor.team.request.GetTeamInfoParams;
import com.easyhoms.easydoctor.team.request.StaffGroupAddParams;
import com.easyhoms.easydoctor.team.request.StaffGroupCreateParams;
import com.easyhoms.easydoctor.team.request.StaffGroupDelParams;

import org.xutils.http.RequestParams;

import java.io.File;

/**
 * 网络请求
 */
public class BaseManager {
    public static final String Token = "token";

    public static void base(NetCallback mCallback) {
        RequestParams params = new RequestParams();
        HttpClient.post(params, mCallback);
    }

    /**
     * 登录
     */
    public static void login(String phone, String password, NetCallback callback) {
        LoginParams params = new LoginParams(phone, password);
        HttpClient.post(params, callback);
    }

    /**
     * 发送短信
     */
    public static void sendSms(String platformId, String phone, NetCallback callback) {
        SendsmsParams params = new SendsmsParams(platformId, phone);
        HttpClient.post(params, callback);
    }

    /**
     * 手机验证码验证
     */
    public static void registerVerify(String platformId, String mobile, String securityCode, NetCallback callback) {
        RegisterVerifyParams params = new RegisterVerifyParams(platformId, mobile, securityCode);
        HttpClient.post(params, callback);
    }

    /**
     * 注册
     */
    public static void register(String platformId, String mobile, String name, String password,String securityCode, NetCallback callback) {
        RegisterUserParams params = new RegisterUserParams(platformId, mobile, name, password,securityCode);
        HttpClient.post(params, callback);
    }

    /**
     * 忘记密码发送短信
     */
    public static void forgetPasswordSendSms(String mobile, NetCallback callback) {
        ForgetPasswordSendSmsParams params = new ForgetPasswordSendSmsParams(mobile);
        HttpClient.post(params, callback);
    }

    /**
     * 忘记密码重置
     */
    public static void forgetPasswordReset(String mobile, String password, NetCallback callback) {
        ForgetPasswordResetParams params = new ForgetPasswordResetParams(mobile, password);
        HttpClient.post(params, callback);
    }

    /**
     * 忘记密码发送短信验证
     */
    public static void forgetPasswordSendSmsVerify(String mobile, String securityCode, NetCallback callback) {
        ForgetPasswordSendSmsVerifyParams params = new ForgetPasswordSendSmsVerifyParams(mobile, securityCode);
        HttpClient.post(params, callback);
    }


    /**
     * 登录修改密码(旧密码方式)
     */
    public static void oldPasswdReset(String oldPasswd, String newPasswd, NetCallback callback) {
        OldPasswordResetParams params = new OldPasswordResetParams(oldPasswd, newPasswd);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }

    /**
     * 登录修改密码(发送验证码)
     */
    public static void resetPasswdSendsms(String mobile, NetCallback callback) {
        ResetPasswordSendSmsParams params = new ResetPasswordSendSmsParams(mobile);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }


    /**
     * 修改绑定手机发送验证码
     */

    public static void changePhoneSendSms(String newMobile, NetCallback callback) {
        ResetPhoneSendSmsParams params = new ResetPhoneSendSmsParams(newMobile);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);

    }


    /**
     * 获取所有医院
     */
    public static void getAllHospital(NetCallback callback) {
        RequestParams params = new RequestParams(Constants.HOST + "/api/company/get_all.jhtml");
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }

    /**
     * 获取我关注的医院
     */
    public static void getMyHospitals(NetCallback callback) {
        RequestParams params = new RequestParams(Constants.HOST + "/api/company/get_myhospitals.jhtml");
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }

    /**
     * 关注该医院
     */
    public static void cancelFocusHosp(String hosId, NetCallback callback) {
        FouseHosParams params = new FouseHosParams(Constants.HOST + "/api/company/cancel_focus.jhtml", hosId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }

    /**
     * 取消关注此医院
     */
    public static void focusHosp(String hosId, NetCallback callback) {
        FouseHosParams params = new FouseHosParams(Constants.HOST + "/api/company/focus.jhtml", hosId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }

    /**
     * 上传文件
     */
    public static void uploadFile(String fileType, File file, NetCallback callback) {
        UploadFileParams params = new UploadFileParams(fileType, file);
        params.addHeader(Token, UserManager.getUser().access_token);
        params.setMultipart(true);
        HttpClient.post(params, callback);
    }

    /**
     * 获取该医院的医生
     */
    public static void getDoctors(int companyId, NetCallback callback) {
        GetDoctorsParams params = new GetDoctorsParams(companyId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }


    //*******************************************staff******************************************

    /**
     * 更改用户信息
     */
    public static void changeUserDetaliInfo(String imagePath, String name, String gender, String birth, NetCallback callback) {
        UserDetailInfoModifyParams params = new UserDetailInfoModifyParams(imagePath, name, gender, birth);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }

    /**
     * 上传医生认证信息
     */
    public static void addAuthInfo(String idNumber, String profDocterQualCer1, String profDocterQualCer2, String profDocterRegisterCer1, String profDocterRegisterCer2, String profTitleCer, NetCallback callback) {
        AddAuthInfoParams params = new AddAuthInfoParams(idNumber, profDocterQualCer1, profDocterQualCer2, profDocterRegisterCer1, profDocterRegisterCer2, profTitleCer);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }

    /**
     * 获取医生认证信息
     */
    public static void getAuthInfo(NetCallback callback) {
        RequestParams params = new RequestParams(Constants.HOST + "/staff/get_authinfo.jhtml");
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }

    /**
     * 获取医生认证状态
     */
    public static void getAuthStatus(NetCallback callback) {
        RequestParams params = new RequestParams(Constants.HOST + "/staff/get_authstatus.jhtml");
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }

    /**
     * 修改绑定手机
     */
    public static void mobileReset(String newMobile, String securityCode, NetCallback callback) {
        MobileResetParams params = new MobileResetParams(newMobile, securityCode);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }

    /**
     * 更改绑定手机发送短信
     */
    public static void mobileResetSendSms(String newMobile, NetCallback callback) {
        MobileResetSendSmsParams params = new MobileResetSendSmsParams(newMobile);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }

    /**
     * 用户详细列表
     */
    public static void staffDetailInfo(String yxId,NetCallback callback) {
        StaffDetailInfoParams params = new StaffDetailInfoParams(yxId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }

    /**
     * 患者详细列表
     */
    public static void userDetailInfo(String yxId,boolean isFo,NetCallback callback) {
        UserDetailInfoParams params = new UserDetailInfoParams(yxId,isFo);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }

    /**
     * 用户信息修改
     */
    public static void userDetailInfoModify(String imagePath, String name, String gender, String birth, NetCallback callback) {

        User user=UserManager.getUser();

        imagePath= TextUtils.isEmpty(imagePath)?user.imagePath:imagePath;
        name= TextUtils.isEmpty(name)?user.name:name;
        gender= TextUtils.isEmpty(gender)?user.gender:gender;
        birth= TextUtils.isEmpty(birth)?user.birth:birth;

        UserDetailInfoModifyParams params = new UserDetailInfoModifyParams(imagePath, name, gender, birth);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }

    /**
     * 用户头像修改
     */
    public static void userHeadModify(String imagePath,NetCallback callback) {
        UserHeadModifyParams params = new UserHeadModifyParams(imagePath);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }

    //*********************************************版本更新******************************************
    /**
     * 版本更新
     */
    public static void versionInfo(NetCallback callback){
        RequestParams params = new RequestParams(Constants.HOST+"/api/version/version_info.jhtml");
        params.addHeader(Token, UserManager.getUser().access_token);
        params.addBodyParameter("client", "2");
        HttpClient.get(params, callback);
    }

    //********************************************team**********************************************
    /**
     * 将员工加入群聊
     */
    public static void addStaff(String staffId, long teamId, long companyId,NetCallback callback){
        AddStaffParams params=new AddStaffParams(staffId, teamId, companyId);
        params.addHeader(Token, UserManager.getUser().access_token);
        LogUtils.i("teamId"+teamId+"teamId");
        HttpClient.post(params, callback);
    }
    /**
     * 查询群聊历史信息
     */
    public static void getHistoryMsgs(long teamId, String beginTime, String endTime,NetCallback callback){
        GetHistoryMsgsParams params=new GetHistoryMsgsParams(teamId, beginTime, endTime);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }
    /**
     * 获取群成员信息
     */
    public static void getMembers(long teamId, long companyId,NetCallback callback){
        GetMembersParams params=new GetMembersParams(teamId, companyId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }
    /**
     * 获取我的群
     */
    public static void getMyteam(long teamId, long companyId,NetCallback callback){
        GetMyTeamParams params=new GetMyTeamParams(teamId,companyId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }
    /**
     * 获取群基本信息
     */
    public static void getTeamInfo(long teamId, long companyId,NetCallback callback){
        GetTeamInfoParams params=new GetTeamInfoParams(teamId, companyId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }

    //***********************************************staffGroup*************************************
    /**
     * 添加同事
     */
    public static void staffGroupAdd(String groupId, String staffId,NetCallback callback){
        StaffGroupAddParams params=new StaffGroupAddParams(groupId, staffId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }
    /**
     * 添加同事
     */
    public static void staffGroupCreate(String companyId,NetCallback callback){
        StaffGroupCreateParams params=new StaffGroupCreateParams(companyId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }
    /**
     * 添加同事
     */
    public static void staffGroupDel(String groupId, String staffId,NetCallback callback){
        StaffGroupDelParams params=new StaffGroupDelParams(groupId, staffId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }
    /**
     * 添加同事
     */
    public static void getMyGroupMembers(long groupId,NetCallback callback){
        GetMyGroupMembersParams params=new GetMyGroupMembersParams(groupId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }
    /**
     * 添加同事
     */
    public static void getMyGroup(String companyId,NetCallback callback){
        GetMyGroupParams params=new GetMyGroupParams(companyId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }
    /**
     * 获取加入的队伍
     */
    public static void getJoinedGroup(String staffId,NetCallback callback){
        RequestParams params=new RequestParams(Constants.HOST+"/staffGroup/get_joinedGroups.jhtml");
        params.addHeader(Token, UserManager.getUser().access_token);
        params.addBodyParameter("staffId",staffId);
        HttpClient.get(params, callback);
    }

    //***********************************staffAction*************************************
    /**
     * 获取医生绑定医院的申请
     */
    public static void getBindHospApply(NetCallback callback){
        GetBindHospApplyParams params=new GetBindHospApplyParams();
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }
    //***********************************link******************************
    /**
     * 医生备注收藏用户
     */
    public static void addALias(String yxId,String alias,NetCallback callback){
        AddAliasParams params=new AddAliasParams(yxId, alias);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }
    /**
     * 医生收藏用户
     */
    public static void addFavorite(String yxId,NetCallback callback){
        AddFavoriteParams params=new AddFavoriteParams(yxId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }
    /**
     * 医生取消收藏用户
     */
    public static void cancelFavorite(String yxId,NetCallback callback){
        CancelFavoriteParams params=new CancelFavoriteParams(yxId);
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.post(params, callback);
    }

    /**
     * 获取医生收藏的用户
     */
    public static void getFavorites(NetCallback callback) {
        RequestParams params = new RequestParams(Constants.HOST + "/link/get_favorites.jhtml");
        params.addHeader(Token, UserManager.getUser().access_token);
        HttpClient.get(params, callback);
    }

    /**
     * 判断是否收藏该用户
     */
    public static void getFavoriteStatus(String yxId,NetCallback callback) {
        RequestParams params = new RequestParams(Constants.HOST + "/link/get_favoriteStatus.jhtml");
        params.addHeader(Token, UserManager.getUser().access_token);
        params.addBodyParameter("yxId", yxId);
        params.addBodyParameter("companyId", UserManager.getBindHos().id+"");
        HttpClient.get(params, callback);
    }
}
