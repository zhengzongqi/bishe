package com.example.administrator.shudong.bean;


import cn.bmob.v3.BmobObject;

/**
 * Created by zzq
 * on         2019/4/8.
 * function:  评论赞BEAN
 */
public class Zan_Comment extends BmobObject {
    private String CommentId;
    private String UserId;
    private boolean status;

    public String getUserId() {
        return UserId;
    }

    public String getCommentId() {
        return CommentId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setCommentId(String commentId) {
        CommentId = commentId;
    }
}
