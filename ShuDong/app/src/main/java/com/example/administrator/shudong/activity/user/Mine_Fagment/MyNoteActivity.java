package com.example.administrator.shudong.activity.user.Mine_Fagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.BaseActivity;
import com.example.administrator.shudong.activity.note.BanNoteActivity;
import com.example.administrator.shudong.activity.note.NoteDetailActivity;
import com.example.administrator.shudong.adapter.NoteAdapter;
import com.example.administrator.shudong.bean.Note;
import com.example.administrator.shudong.bean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zzq
 * on         2019/4/16
 * function:  我的帖子页面
 */
public class MyNoteActivity extends BaseActivity implements View.OnClickListener{

    NoteAdapter noteAdapter;
    List<Note> noteList =new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynote);
        initData();
        initView();
    }

    private void initView(){
        listView=(ListView)findViewById(R.id.lv_mynote);
        noteAdapter=new NoteAdapter(MyNoteActivity.this,noteList);
        listView.setAdapter(noteAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyNoteActivity.this,NoteDetailActivity.class);
                intent.putExtra("note",noteList.get(position));
                startActivity(intent);
            }
        });
    }

    private void initData(){
        User user=BmobUser.getCurrentUser(User.class);
        BmobQuery<Note> query=new BmobQuery<Note>();
        query.addWhereEqualTo("UserId",user.getUsername());
        query.order("-top");
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e==null){
                    noteList.clear();
                    noteList.addAll(list);
                    noteAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
