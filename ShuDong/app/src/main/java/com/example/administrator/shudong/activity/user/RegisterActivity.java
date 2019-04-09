package com.example.administrator.shudong.activity.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.BaseActivity;
import com.example.administrator.shudong.bean.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zzq
 * on         2019/4/2.
 * function:  注册功能
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText et_user;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private EditText et_pass;
    private EditText et_password;
    private EditText et_email;
    private Button btnRegistered;
    //性别
    private boolean isGender = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        initView();
    }

    private void initView(){
        et_user = (EditText) findViewById(R.id.et_user);
        et_age = (EditText) findViewById(R.id.et_age);
        et_desc = (EditText) findViewById(R.id.et_desc);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_password = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);
        btnRegistered = (Button) findViewById(R.id.btnRegistered);

        btnRegistered.setOnClickListener(this);
    }

    //判邮箱格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistered:

                final String name = et_user.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                final String email = et_email.getText().toString().trim();

                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) &
                        !TextUtils.isEmpty(pass) &
                        !TextUtils.isEmpty(password) &
                        !TextUtils.isEmpty(email)){
                    if(pass.equals(password)){
                        if(isEmail(email)){

                            //判断性别
                            mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    if (checkedId == R.id.rb_boy) {
                                        isGender = true;
                                    } else if (checkedId == R.id.rb_girl) {
                                        isGender = false;
                                    }
                                }
                            });
                            //填充简介
                            if (TextUtils.isEmpty(desc)) {
                                desc = getString(R.string.text_nothing);
                            }

                            //填充数据
                            final User user=new User();

                            user.setUsername(name);
                            user.setPassword(password);
                            user.setSex(isGender);
                            user.setDesc(desc);
                            user.setAge(Integer.parseInt(age));
                            user.setEmail(email);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {



                                    user.signUp(new SaveListener<User>() {
                                        @Override
                                        public void done(User user, BmobException e) {
                                            if(e==null){
                                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                                                //发送邮箱确认邮件
                                                user.requestEmailVerify(email, new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if(e==null){

                                                        }
                                                        else {

                                                        }
                                                    }
                                                });

                                                //跳转到登录页面
                                                Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }).start();

                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                            et_email.setText("");
                        }
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "两次密码不相同,请确认", Toast.LENGTH_SHORT).show();
                        et_pass.setText("");
                        et_password.setText("");
                    }

                }

                else {
                    Toast.makeText(RegisterActivity.this, "请认真检查所有信息是否填写完毕", Toast.LENGTH_SHORT).show();
                }




                break;
        }
    }
}
