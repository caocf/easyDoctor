package com.easyhoms.easydoctor.common.manager;

import com.easyhoms.easydoctor.common.bean.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 保存医生
 */
public class UserManager {

    public static User getUser() {
        User user = null;
        try {
            user = x.db().selector(User.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return user;
    }
    public static void paraseUser(String result,String phone,String password) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject content=object.getJSONObject("content");
            String access_token=content.getString("access_token");
            JSONObject user=content.getJSONObject("user");
            JSONObject userExtend = content.getJSONObject("userExtend");
            String imagePath = userExtend.getString("imagePath");
            User userBean=new Gson().fromJson(user.toString(),User.class);
            userBean.access_token=access_token;
            userBean.Id = 1;
            userBean.mobile = phone;
            userBean.password = password;
            userBean.imagePath = imagePath;
            userBean.name = user.getString("name");
            if (user.getString("birth").equals("null")){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                userBean.birth = df.format(new Date());
            }
            else {
                userBean.birth = user.getString("birth");
            }
            if (user.getString("gender").equals("null")){
                userBean.gender = "1";
            }
            else {
                userBean.gender = user.getString("gender");
            }
            try {
                x.db().delete(User.class);
                x.db().saveBindingId(userBean);
            } catch (DbException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(){
        try {
            x.db().delete(User.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    //数据更新、
    public static void setUser(String key, String value) {
        User user = getUser();
        if (key.equals("imagePath")) {
            user.imagePath = value;
        }
        if (key.equals("name")) {
            user.name = value;

        }
        if (key.equals("gender")) {
            user.gender = value;

        }
        if (key.equals("birth")) {
            user.birth = value;
        }
        try {
            x.db().saveOrUpdate(user);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    public static void saveHosToDb(ArrayList<String> hospitals) {

        User user = UseManager.getUser();
        user.hospital = new Gson().toJson(hospitals);
        try {
            x.db().saveOrUpdate(user);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
