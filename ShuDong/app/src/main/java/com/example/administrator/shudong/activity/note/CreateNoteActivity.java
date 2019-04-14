package com.example.administrator.shudong.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.BaseActivity;
import com.example.administrator.shudong.bean.Note;
import com.example.administrator.shudong.bean.Type;
import com.example.administrator.shudong.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by zzq
 * on         2019/4/14
 * function:  发表帖子页面
 */

public class CreateNoteActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imageView_goback;
    private ImageView imageView_publish;
    private EditText editText;

    private Type type;
    private Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote_layout);
        initView();
    }


    public void initView(){
        Intent intent=getIntent();
        type=(Type)intent.getSerializableExtra("type");

        imageView_goback=findViewById(R.id.iv_createnote_goback);
        imageView_publish=findViewById(R.id.iv_createnote_publish);
        editText=findViewById(R.id.et_createnote);

        imageView_goback.setOnClickListener(this);
        imageView_publish.setOnClickListener(this);

    }






    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_createnote_goback:
                finish();
                break;

            case R.id.iv_createnote_publish:

                String content=editText.getText().toString().trim();
                if(!TextUtils.isEmpty(content)){
                    User user=BmobUser.getCurrentUser(User.class);
                    note = new Note();
                    note.setUserId(user.getUsername());
                    note.setNum_Reply(0);
                    note.setNum_Like(0);
                    note.setContent(content);
                    note.setTitle("空");
                    note.setTypeId(type.getTypeName());

                    note.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(CreateNoteActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(CreateNoteActivity.this,BanNoteActivity.class);
                                intent.putExtra("type",type);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(CreateNoteActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(CreateNoteActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }
                finish();
        }
    }
}
