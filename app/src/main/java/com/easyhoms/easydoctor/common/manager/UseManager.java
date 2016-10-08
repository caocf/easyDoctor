package com.easyhoms.easydoctor.common.manager;

import com.easyhoms.easydoctor.common.bean.User;

import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * 用户数据库增删改查
 */

public class UseManager {

    public static User getUser() {
        User user = null;
        try {
            user = x.db().selector(User.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return user;
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

}
