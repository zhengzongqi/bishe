package com.example.administrator.shudong.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.BaseActivity;
import com.example.administrator.shudong.adapter.NoteAdapter;
import com.example.administrator.shudong.bean.Note;
import com.example.administrator.shudong.bean.Type;
import com.example.administrator.shudong.bean.Type_Like;
import com.example.administrator.shudong.bean.User;
import com.example.administrator.shudong.bean.Zan_Note;

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
 * on         2019/4/11
 * function:  兴趣圈帖子
 */
public class BanNoteActivity extends BaseActivity implements View.OnClickListener{

    private Type type;
    private List<Note> mnote=new ArrayList<>();
    private FloatingActionButton button_createnote;
    private NoteAdapter noteAdapter;
    private ListView lvbannote;
    private TextView bannotename;
    private TextView bannotepeople;
    private ImageView iv_like;


    private boolean islike=false;
    private Boolean tag=false;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannote_layout);
        initView();
        initBanNoteData();
    }

    private void initView(){

        Intent intent=getIntent();
        type= (Type) intent.getSerializableExtra("type");
        button_createnote=(FloatingActionButton)findViewById(R.id.bt_bannote_createnote);
        lvbannote=(ListView)findViewById(R.id.lv_bannote);
        bannotename=(TextView)findViewById(R.id.tv_bannotename);
        bannotepeople=(TextView)findViewById(R.id.tv_bannotepeople);
        button_createnote=(FloatingActionButton)findViewById((R.id.bt_bannote_createnote));
        iv_like=(ImageView)findViewById(R.id.ban_like);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_bannote);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_bfbfbf);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initBanNoteData();   //进行刷新操作
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        bannotename.setText(type.getTypeName());
        bannotepeople.setText(type.getNum_People()+"");


        button_createnote.setOnClickListener(this);
        iv_like.setOnClickListener(this);

        noteAdapter=new NoteAdapter(this,mnote);
        lvbannote.setAdapter(noteAdapter);

        lvbannote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(BanNoteActivity.this,NoteDetailActivity.class);
                intent.putExtra("note",mnote.get(position));
                startActivity(intent);
            }
        });
        initBan_Like();
    }

    @Override
    public void onResume() {
        super.onResume();
        initBanNoteData();
    }

    private void initBanNoteData(){
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.order("-top");
        query.addWhereEqualTo("TypeId",type.getTypeName());
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e==null){
                    mnote.clear();
                    mnote.addAll(list);
                    //重新调用ADAPTER更新页面
                    noteAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(BanNoteActivity.this, "加载数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_bannote_createnote:
                Intent intent_create=new Intent(BanNoteActivity.this,CreateNoteActivity.class);
                intent_create.putExtra("type",type);
                startActivity(intent_create);
                break;
            case R.id.ban_like:
                if(islike==true){
                    cancelLike();
                }
                else {
                    makesureLike();
                }
                break;
        }
    }



    private void initBan_Like(){
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Type_Like> query = new BmobQuery<Type_Like>();
        query.addWhereEqualTo("UserId", user.getObjectId());
        query.addWhereEqualTo("TypeId",type.getObjectId());
        query.findObjects(new FindListener<Type_Like>(){
            @Override
            public void done(List<Type_Like> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        iv_like.setImageResource(R.drawable.like_selected);
                        islike = true;
                        tag = true;
                    }
                    else {
                        iv_like.setImageResource(R.drawable.like);
                        islike=false;
                    }
                }
            }
        });
    }

    private void makesureLike(){
        User user = BmobUser.getCurrentUser(User.class);
        final Type_Like type_like =new Type_Like();
        type_like.setTypeId(type.getObjectId());
        type_like.setUserId(user.getObjectId());
        type_like.setTypeName(type.getTypeName());
        type_like.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){

                    iv_like.setImageResource(R.drawable.like_selected);
                    islike=true;
                    bannotepeople.setText(""+(Integer.valueOf(bannotepeople.getText().toString().trim())+1));
                    type.setNum_People(type.getNum_People()+1);
                    type.update(type.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(BanNoteActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        });

    }

    private void cancelLike(){

        User user=BmobUser.getCurrentUser(User.class);
        iv_like.setImageResource(R.drawable.like);
        bannotepeople.setText(""+(Integer.valueOf(bannotepeople.getText().toString().trim())-1));
        islike=false;
        BmobQuery<Type_Like> query =new BmobQuery<Type_Like>();
        query.addWhereEqualTo("UserId",user.getObjectId());
        query.addWhereEqualTo("TypeId", type.getObjectId());
        query.setLimit(1);
        query.findObjects(new FindListener<Type_Like>() {
            @Override
            public void done(List<Type_Like> list, BmobException e) {
                if(e==null){
                    Type_Like t=list.get(0);
                    type.setNum_People(type.getNum_People()-1);
                    type.update(type.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                    t.delete(t.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(BanNoteActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }





}

