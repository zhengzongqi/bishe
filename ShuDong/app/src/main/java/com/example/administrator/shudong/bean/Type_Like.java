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
    private String TypeName;

    public String getTypeId() {
        return TypeId;
    }
    public String getUsername(){
        return UserId;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
