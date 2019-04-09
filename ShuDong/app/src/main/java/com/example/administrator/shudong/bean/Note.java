package com.example.administrator.shudong.bean;


import cn.bmob.v3.BmobObject;

/**
 * Created by zzq
 * on         2019/4/6.
 * function:  帖子BEAN
 */
public class Note extends BmobObject {
    private String UserId;
    private String TypeId;
    private String Title;
    private int Num_Like;
    private int Num_Reply;
    private String Content;

    public String getUserId() {
        return UserId;
    }

    public String getTypeId() {
        return TypeId;
    }
    public String getTitle(){
        return Title;
    }

    public int getNum_Like() {
        return Num_Like;
    }

    public int getNum_Reply() {
        return Num_Reply;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setNum_Like(int num_Like) {
        Num_Like = num_Like;
    }

    public void setNum_Reply(int num_Reply) {
        Num_Reply = num_Reply;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
