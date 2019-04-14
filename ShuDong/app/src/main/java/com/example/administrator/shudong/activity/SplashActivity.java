package com.example.administrator.shudong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.note.BanNoteActivity;
import com.example.administrator.shudong.activity.note.SearchBanActivity;
import com.example.administrator.shudong.activity.user.LoginActivity;
import com.example.administrator.shudong.revealbanner.BannerActivity;

/**
 * Created by zzq
 * on         2019/3/15.
 * function:  闪烁界面
 */
public class SplashActivity extends BaseActivity{

    private TextView textView;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);
        handler.sendEmptyMessageDelayed(1000,2000);


    }
}
