package com.example.administrator.shudong.bean;


import cn.bmob.v3.BmobObject;

/**
 * Created by zzq
 * on         2019/4/15
 * function:  喜欢版块BEAN
 */
public class Type_Like extends BmobObject {
    private String TypeId;
    private String UserId;

    public String getTypename() {
        return TypeId;
    }
    public String getUsername(){
        return UserId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
