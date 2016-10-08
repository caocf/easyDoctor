package com.easyhoms.easydoctor.common.response;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/10.
 */
public class BaseArrayResp<T> {
    public int code;
    public String desc;
    public ArrayList<T> content=new ArrayList<>();
}
