package com.easyhoms.easydoctor.common.response;

/**
 * Created by Administrator on 2016/3/10.
 */
public class BaseResp<T> {
    public int code;
    public String desc;
    public T content;
}
