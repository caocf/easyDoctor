package com.easyhoms.easydoctor.common.utils;

import android.text.TextUtils;

import com.netease.nim.uinfo.UserInfoHelper;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 医院搜索
 */
public class LocalRecentSearch {

    /**
     * 按医院名-群名拼音搜索
     */
    public static ArrayList<RecentContact> searchGroup(String str,
                                                       ArrayList<RecentContact> allContacts) {


        ArrayList<RecentContact> groupList = new ArrayList<RecentContact>();

        if(TextUtils.isEmpty(str)){
            return groupList;
        }
        //如果含有中文->判断是否含有这个字段
        if (CommonUtils.isContainsChinese(str.toString())) {
            for (RecentContact group : allContacts) {
                String name=UserInfoHelper.getUserTitleName(group.getContactId(), group.getSessionType());
                if (name.contains(str)) {
                    groupList.add(group);
                }
            }
        } else {
            for (RecentContact group : allContacts) {
                if (contains(group, str.toString())) {
                    groupList.add(group);
                }
            }
        }
        return groupList;
    }

    /**
     * 根据拼音搜索
     */
    private static boolean contains(RecentContact group, String search) {
        String name=UserInfoHelper.getUserTitleName(group.getContactId(), group.getSessionType());
        if (TextUtils.isEmpty(name)) {
            return false;
        }

        String firstLetters = FirstLetterUtil
                .getFirstLetter(getGroupName(group));

        CharacterParser finder = CharacterParser.getInstance();

        LogUtils.i("  groupName:"+name+"简拼:"+firstLetters+"  全拼:"+finder.getSelling(getGroupName(group)));
        boolean flag = false;

        // 简拼匹配,如果输入在字符串长度大于6就不按首字母匹配了
        if (search.length() < 6) {

            // 不区分大小写
            Pattern firstLetterMatcher = Pattern.compile(search,
                    Pattern.CASE_INSENSITIVE);
            flag = firstLetterMatcher.matcher(firstLetters).find();
        }
//        //如果只有一个字母  就按首字母匹配
//        if(search.length()==1){
//            // 不区分大小写
//            Pattern firstLetterMatcher = Pattern.compile(search,
//                    Pattern.CASE_INSENSITIVE);
//            flag = firstLetterMatcher.matcher(firstLetters).find();
//            return flag;
//        }

        if (!flag) { // 如果简拼已经找到了，就不使用全拼了
            // 全拼匹配
            CharacterParser finder1 = CharacterParser.getInstance();
           // finder.setResource(getGroupName(group));
            // 不区分大小写
            Pattern pattern2 = Pattern
                    .compile(" "+search, Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = pattern2.matcher(finder1.getSelling(getGroupName(group)));
            flag = matcher2.find();
        }

        return flag;
    }

    private static String getGroupName(RecentContact group) {
        String name=UserInfoHelper.getUserTitleName(group.getContactId(), group.getSessionType());
        String strName = "";
        if (!TextUtils.isEmpty(name)) {
            strName = name;
        }

        return strName;
    }
}
