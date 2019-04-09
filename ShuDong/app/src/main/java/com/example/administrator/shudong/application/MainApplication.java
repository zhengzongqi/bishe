package com.example.administrator.shudong.application;

import android.app.Application;

import com.example.administrator.shudong.util.StaticData;

import cn.bmob.v3.Bmob;

/*程序入口，第三方插件加载*/

public class MainApplication extends Application {
    private static MainApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(this,StaticData.BMOB_ID);
        mApplication = this;
    }

    public static MainApplication getInstance(){
        return mApplication;
    }
}
