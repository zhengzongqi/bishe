package com.example.administrator.shudong.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.BaseActivity;
import com.example.administrator.shudong.adapter.NoteAdapter;
import com.example.administrator.shudong.bean.Note;
import com.example.administrator.shudong.bean.Type;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.internal.operators.flowable.FlowableTakeLastOne;


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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannote);
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



        bannotename.setText(type.getTypeName());
        bannotepeople.setText(type.getNum_People()+"");


        button_createnote.setOnClickListener(this);

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
        }
    }
}
