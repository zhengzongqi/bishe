package com.example.administrator.shudong.bean;


import cn.bmob.v3.BmobObject;

/**
 * Created by zzq
 * on         2019/4/16
 * function:  反馈bean
 */
public class Feedback extends BmobObject {
    private String UserId;
    private String Content;

    public String getUserId() {
        return UserId;
    }

    public String getContent() {
        return Content;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setContent(String content) {
        Content = content;
    }
}
