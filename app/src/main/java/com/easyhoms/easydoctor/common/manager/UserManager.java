package com.easyhoms.easydoctor.common.manager;

import android.text.TextUtils;

import com.easyhoms.easydoctor.common.bean.User;
import com.easyhoms.easydoctor.common.response.Hospital;
import com.easyhoms.easydoctor.common.response.UserBean;
import com.easyhoms.easydoctor.common.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;

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

    public static void paraseUser(String result, String phone, String password) {
        UserBean bean = new Gson().fromJson(result, UserBean.class);
        User dbUser = new User();
        dbUser.staffImId = bean.content.staff_im_id;
        dbUser.access_token = bean.content.access_token;
        dbUser.staffImId = bean.content.staff_im_id;
        dbUser.mobile = phone;
        dbUser.password = password;
        if (bean.content.staffExtend != null) {
            dbUser.imagePath = bean.content.staffExtend.imagePath;
        }
        dbUser.name = TextUtils.isEmpty(bean.content.staff.name)?"":bean.content.staff.name;
        dbUser.birth = TextUtils.isEmpty(bean.content.staff.birth)?"":bean.content.staff.birth;
        dbUser.gender = TextUtils.isEmpty(bean.content.staff.gender)?"1":bean.content.staff.gender;
        dbUser.Id = 1;
        dbUser.id=bean.content.staff.id;
        try {
            x.db().delete(User.class);
            x.db().saveBindingId(dbUser);
            LogUtils.i(dbUser.toString());
        } catch (DbException e) {
            e.printStackTrace();
            LogUtils.e("db: " + e.getMessage());
        }

    }

    public static void deleteUser() {
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
        if (key.equals("auth")) {
            user.authState = Integer.valueOf(value);
        }
        try {
            x.db().saveOrUpdate(user);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    public static void saveBindHosToDb(ArrayList<Hospital> hospitals) {
        User user = UserManager.getUser();
        user.hospital = new Gson().toJson(hospitals);
        try {
            x.db().saveOrUpdate(user);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static Hospital getBindHos(){
        Hospital hospital=null;
        String hos=getUser().hospital;

        Type objectType = new TypeToken<ArrayList<Hospital>>() {
        }.getType();
        ArrayList<Hospital> res = new Gson().fromJson(hos, objectType);
        if(res==null){
           return null;
        }
        hospital=(res.size()==0?null:res.get(0));
        return hospital;
    }


}
