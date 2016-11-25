package com.easyhoms.easydoctor;

import android.os.Environment;

public class Constants {
    /**
     * intent key
     */
    public static final String KEY_FIRST_LOGIN = "FIRST_LOGIN";
    public static final String KEY_PHONE = "PHONE_NUMBER";
    public static final String KEY_PASSWORD = "PASSWORD";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_TYPE = "TYPE";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_DATA = "data";
    public static final String KEY_HOSPITAL = "hospital";
    public static final String KEY_ORDER_ID= "order_id";
    public static final String KEY_CURE_ID = "cure_id";
    public static final String KEY_CURE_TYPE = "cure_type";
    public static final String KEY_CURE_NAME = "cure_name";
    public static final String KEY_HOS_NAME = "hos_name";
    public static final String KEY_TIME = "time";
    public static final String KEY_MAX_COUNT = "max_count";
    public static final String KEY_COUNT = "count";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_GROUP_ID = "group_id";
    public static final String KEY_YX_TEAM_ID = "team_id";
    public static final String KEY_YX_TEAM_MEMBERS = "team_members";

    /**
     * 验证码时间
     */
    public static final int AUTH_TIME = 60;

    /**
     * 图片相机
     */
    public static final int CODE_GALLERY_RESULT_REQUEST = 0;
    public static final int CODE_CAMERA_RESULT_REQUEST = 1;
    public static final int CODE_GALLERY_REQUEST = 2;
    public static final int CODE_CAMERA_REQUEST = 3;

    /**
     * 网络请求
     */
    public static final int CODE_NET_SUCCESS = 0;//请求成功
    public static final int CODE_AUTH_ERROR = 11009;//短信验证码错误

    /**
     * 手机,密码错误值
     */
    public static final int RIGHT = 0; //手机号或者密码正确
    public static final int ERROR_EMPTY = 1;//手机号或者密码为空空
    public static final int ERROR_PHONE_FORMAT = 2;//手机格式错误
    public static final int ERROR_PASSWORD_LENGTH = 2;//密码长度错误,6-16位之间
    public static final int ERROR_PASSWORD_FORMAT = 3;//密码包含非法字符
    public static final int ERROR_PASSWORD_LENGTH_FORMAT = 4;//长度错误,同时包含非法字符

    /**
     * 数字或字母正则式
     */
    public static final String reg = "^[A-Za-z0-9]+$";

    /**
     * 服务器网址
     */
    public static final String HOST = "http://59.110.25.238:8080/doct-webClient";
    // public static final String HOST = "http://192.168.1.20:8090/doct-openapi";
    public static final String HOST_PATIENT = "http://59.110.25.238:8080/doct-webClient";

    /**
     * 图片下载前缀
     */
    public static final String HOST_HEAD = "http://59.110.25.238";

    /**
     * 图片保存的路径地址
     */
    //public static final String SDCARD = "sdcard";
    public static final String SDCARD = Environment.getExternalStorageDirectory().getPath();
    public static final String PATH = "/upload/";
    public static final String HEAD_JPG = "head.jpg";

    /**
     * 1:未治疗  2:治疗中   3:治疗完
     */
    public static final int CURE_TYPE_UNCURED = 0;
    public static final int CURE_TYPE_CURING = 1;
    public static final int CURE_TYPE_COMPLETE = 2;

    /**
     * 预约治疗状态
     */
    public static final int APPOINT_COMPLETE = 1;
    public static final int APPOINT_UPDATE = 2;

    /**
     * 选择医院->1.查看医院详情  2.选择医生聊天
     */
    public static final int ACTION_HOSPITAL_DETAIL = 1;
    public static final int ACTION_DOCTOR_LIST = 2;

    /**
     * 日期格式
     */
    public static final String DATE_STANDED="yyyy-MM-dd HH:mm:ss";
    public static final String DATE_YEAR_MONTH_DAY="yyyy-MM-dd";
    public static final String DATE_CHINESE="yyyy年M月dd日";
    public static final String DATE_CHINESE_2="yyyy年M月dd日 HH时mm分";
    public static final String DATE_CHINESE_3="M月dd日 HH时mm分";
    public static final String DATE_MONTH_YEAR="M月dd日";
    public static final String DATE_WEEK="yy/M/dd EEE HH:mm";
    public static final String DATE_WEEK_SYSTEM="yy/M/dd HH:mm";
    public static final String DATE_SERVER="yyyy-MM-dd HH:mm";
    //public static final String DATE_MONTH_DAY_HOUR_MIN , YEAR_MONTH,MORNING_HOURS_MINS,AFTERNOON_HOURS_MINS

    /**
     * Im前缀
     */
    public static final String IM_DOCTOR="staff_";
    public static final String IM_PATIENT="user_";

    /**
     * 认证状态
     */
    public static final int AUTH_SUCCESS=1;
    public static final int AUTH_FAIL=2;
    public static final int AUTH_AUTHING=3;
    public static final int AUTH_UNAUTHED=4;

    /**
     * 更新头像,名字,或年龄
     */
    public static final int USER_NAME=1;
    public static final int USER_AGE=2;
    public static final int USER_BIRTH=3;
    public static final int USER_HEAD=4;
}
