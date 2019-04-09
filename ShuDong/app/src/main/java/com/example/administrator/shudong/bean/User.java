package com.example.administrator.shudong.bean;

/**
 * Created by zzq
 * on         2019/3/24.
 * function:  用户BEAN
 */
import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private Boolean sex;
    private int age;
    private String desc;

    public Boolean getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getDesc(){
        return desc;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
