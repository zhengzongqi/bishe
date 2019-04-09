package com.example.administrator.shudong.bean;

import cn.bmob.v3.BmobObject;


/**
 * Created by zzq
 * on         2019/4/8.
 * function:  帖子赞BEAN
 */
public class Zan_Note extends BmobObject {
    private String NoteId;
    private String UserId;
    private boolean Status;
    public String getUserId() {
        return UserId;
    }

    public String getNoteId() {
        return NoteId;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setNoteId(String noteId) {
        NoteId = noteId;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
