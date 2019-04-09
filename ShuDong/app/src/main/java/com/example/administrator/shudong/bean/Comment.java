package com.example.administrator.shudong.bean;

import cn.bmob.v3.BmobObject;
/**
 * Created by zzq
 * on         2019/4/6.
 * function:  评论BEAN
 */
public class Comment extends BmobObject {
    private String NoteId;
    private String UserId;
    private String Content;
    private int Num_Like;

    public String getNoteId() {
        return NoteId;
    }

    public String getUserId() {
        return UserId;
    }

    public String getContent() {
        return Content;
    }

    public int getNum_Like() {
        return Num_Like;
    }

    public void setNoteId(String noteId) {
        NoteId = noteId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setNum_Like(int num_Like) {
        Num_Like = num_Like;
    }
}
