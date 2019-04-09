package com.example.administrator.shudong.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.BaseActivity;
import com.example.administrator.shudong.activity.HomeActivity;
import com.example.administrator.shudong.bean.User;
import com.example.administrator.shudong.util.SharedPreferencesTools;
import com.example.administrator.shudong.util.StaticData;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zzq
 * on         2019/3/24.
 * function:  登录界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    //控件声明
    private EditText etName;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private TextView tvTouristLogin;
    private TextView tvForgetPassword;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case StaticData.LOGIN_SUCCESS:
                    Toast.makeText(LoginActivity.this, "欢迎回来", Toast.LENGTH_SHORT).show();
                    //跳转到主页
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case StaticData.LOGIN_FAILED:
                    Toast.makeText(LoginActivity.this, "登录失败，请检查用户名或密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        initView();
    }

    public void initView(){
        etName = (EditText) findViewById(R.id.et_name);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.bt_login);
        btnRegister = (Button) findViewById(R.id.bt_registered);
        tvTouristLogin = (TextView) findViewById(R.id.tv_tourist);
        tvForgetPassword = (TextView) findViewById(R.id.tv_forget);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvTouristLogin.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.bt_login:
                final String name =etName.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                if(!TextUtils.isEmpty(name)&!TextUtils.isEmpty(password)){

                    final BmobUser user =new BmobUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            user.login(new SaveListener<User>() {
                                @Override
                                public void done(User user, BmobException e) {
                                    if (e == null) {

                                        //如果是第一次登陆
                                        if (SharedPreferencesTools.getString(LoginActivity.this, StaticData.USER_NAME, "").equals("")) {
                                            //保存用户信息
                                            SharedPreferencesTools.putString(LoginActivity.this, StaticData.USER_NAME, name);
                                            SharedPreferencesTools.putString(LoginActivity.this, StaticData.PASSWORD, password);
                                        }
                                        handler.sendEmptyMessage(StaticData.LOGIN_SUCCESS);
                                    } else {
                                        handler.sendEmptyMessage(StaticData.LOGIN_FAILED);
                                    }
                                }
                            });
                        }
                    }).start();
                }
            else {
                    Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bt_registered:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }
}
