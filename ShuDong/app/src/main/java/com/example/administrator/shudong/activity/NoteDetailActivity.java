package com.example.administrator.shudong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.shudong.bean.Zan_Note;
import com.makeramen.roundedimageview.RoundedImageView;
import com.example.administrator.shudong.R;
import com.example.administrator.shudong.bean.Note;
import com.example.administrator.shudong.bean.Comment;
import com.example.administrator.shudong.bean.User;
import com.example.administrator.shudong.adapter.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zzq
 * on         2019/4/8
 * function:  帖子详情页
 */
public class NoteDetailActivity extends BaseActivity implements View.OnClickListener{

    private RoundedImageView note_profile;
    private TextView title;
    private TextView type;
    private TextView time;
    private TextView content;
    private ImageView zan;
    private TextView zancount;
    private ImageView replay;
    private TextView replaycount;

    private EditText editText;
    private Button button;
    private ListView lvnotedetail;

    private Note note;
    private List<Comment> mlist=new ArrayList<>();
    private CommentAdapter commentAdapter;

    //默认没有点赞
    private Boolean iszan=false;
    //回复数量
    private int count=0;

    private Boolean tag=false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initdata();
        initCommentdata();
        initView();
    }

    //初始化帖子数据
    private void initdata(){
        Intent intent=getIntent();
        note= (Note) intent.getSerializableExtra("note");
    }
    private void initView(){
        type=(TextView)findViewById(R.id.note_detail_type);
        time=(TextView)findViewById(R.id.note_detail_time);
        content=(TextView)findViewById(R.id.note_detail_content);
        zan=(ImageView)findViewById(R.id.note_detail_zan);
        replay=(ImageView)findViewById(R.id.note_detail_replay);
        zancount=(TextView)findViewById(R.id.note_detail_zancount);
        replaycount=(TextView)findViewById(R.id.note_detail_replaycount);

        //设置帖子数据
        type.setText(note.getTypeId());
        time.setText(note.getUpdatedAt().substring(0,10));
        content.setText(note.getContent());
        zancount.setText(""+note.getNum_Like());
        count=note.getNum_Reply();
        replaycount.setText(""+note.getNum_Reply());

        initZanState();

        zan.setOnClickListener(this);

        lvnotedetail=(ListView)findViewById(R.id.lv_note_detail);
        commentAdapter=new CommentAdapter(this,mlist);
        lvnotedetail.setAdapter(commentAdapter);
    }

    //设置赞初始状态
    private void initZanState() {
    }

    //初始化评论数据
    private void initCommentdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                BmobQuery<Comment> query = new BmobQuery<Comment>();
                query.addWhereEqualTo("NoteId",note.getObjectId());
                query.setLimit(50);
                query.order("-createdAt");
                query.findObjects(new FindListener<Comment>() {
                    @Override
                    public void done(List<Comment> list, BmobException e) {
                        mlist.addAll(list);
                        commentAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }

    //点赞
    private void makesureZan() {

        User user = BmobUser.getCurrentUser(User.class);
        //无数据创建数据，有数据修改数据
        if(tag==true){
            zan.setImageResource(R.drawable.zan_pressed);
            zancount.setText(""+(Integer.valueOf(zancount.getText().toString().trim())+1));

            BmobQuery<Zan_Note> query = new BmobQuery<Zan_Note>();
            query.addWhereEqualTo("userid",user.getObjectId());
            query.addWhereNotEqualTo("noteid", note.getObjectId());
            query.setLimit(1);

            query.findObjects(new FindListener<Zan_Note>() {
                @Override
                public void done(List<Zan_Note> list, BmobException e) {
                    if(e==null){
                        Zan_Note z=list.get(0);
                        z.setStatus(true);
                        z.update(z.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    note.setNum_Like(Integer.valueOf(note.getNum_Like()+1));
                                    note.update(note.getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e==null){
                                                iszan=true;
                                            }else{

                                            }
                                        }
                                    });

                                }else{


                                }
                            }
                        });


                    }else{


                    }

                }
            });





        }else {

            zan.setImageResource(R.drawable.zan_pressed);
            zancount.setText("" + (Integer.valueOf(zancount.getText().toString().trim()) + 1));


            Zan_Note z = new Zan_Note();
            z.setUserId(user.getObjectId());
            z.setNoteId(note.getObjectId());
            z.setStatus(true);

            z.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {

                        note.setNum_Like(
                                note.getNum_Like() + 1);
                        note.update(note.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {

                                if (e == null) {

                                    iszan = true;
                                    tag = true;


                                } else {
                                    Toast.makeText(NoteDetailActivity.this, "登录失败，请检查用户名或密码错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(NoteDetailActivity.this, "点赞失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }

    }


    //取消点赞
    private void cancelZan() {


        zan.setImageResource(R.drawable.zan);
        zancount.setText(""+(Integer.valueOf(zancount.getText().toString().trim())-1));
        iszan=false;
        User user = BmobUser.getCurrentUser(User.class);

        BmobQuery<Zan_Note> query = new BmobQuery<Zan_Note>();
        query.addWhereEqualTo("userid",user.getObjectId());
        query.addWhereNotEqualTo("noteid", note.getObjectId());
        query.setLimit(1);

        query.findObjects(new FindListener<Zan_Note>() {
            @Override
            public void done(List<Zan_Note> list, BmobException e) {
                if(e==null){
                    Zan_Note z=list.get(0);
                    z.setStatus(false);
                    z.update(z.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                note.setNum_Like(Integer.valueOf(note.getNum_Like()-1));
                                note.update(note.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e==null){
                                            iszan=false;
                                        }
                                    }
                                });

                            }else{

                            }
                        }
                    });


                }else{

                }

            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.note_detail_zan:
                if(iszan==false){
                    //点赞
                    makesureZan();
                    iszan=true;
                }else{
                    //取消点赞
                    cancelZan();
                }
                break;

        }
    }
}
