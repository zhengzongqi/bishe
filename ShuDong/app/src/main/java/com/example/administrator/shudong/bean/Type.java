package com.example.administrator.shudong.bean;

import cn.bmob.v3.BmobObject;
/**
 * Created by zzq
 * on         2019/4/6.
 * function:  版块BEAN
 */
public class Type extends BmobObject {
    private String Typename;
    private int Number_Today;
    private int Number_All;

    public String getTypename() {
        return Typename;
    }

    public int getNumber_All() {
        return Number_All;
    }

    public int getNumber_Today() {
        return Number_Today;
    }

    public void setNumber_All(int number_All) {
        Number_All = number_All;
    }

    public void setNumber_Today(int number_Today) {
        Number_Today = number_Today;
    }

    public void setTypename(String typename) {
        Typename = typename;
    }
}
