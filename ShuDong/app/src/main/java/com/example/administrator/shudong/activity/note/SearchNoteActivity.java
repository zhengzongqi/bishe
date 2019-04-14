package com.example.administrator.shudong.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.BaseActivity;
import com.example.administrator.shudong.adapter.NoteAdapter;
import com.example.administrator.shudong.bean.Note;
import com.example.administrator.shudong.util.StaticData;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by zzq
 * on         2019/4/14
 * function:  搜索帖子页面
 */

public class SearchNoteActivity extends BaseActivity implements View.OnClickListener {

    private EditText etSearcNote;
    private ImageView ivSearchNote;
    private TextView tvSearchNote;
    private ListView lvSearchNote;

    private NoteAdapter noteAdapter;
    List<Note> note_list =new ArrayList<>();
    List<Note> getNote_list_filtered =new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticData.SEARCH_NOTE_SUCCESS:

                    tvSearchNote.setVisibility(View.GONE);
                    noteAdapter.notifyDataSetChanged();

                    break;
                case StaticData.SEARCH_NOTE_SUCCESS_NODATA:
                    tvSearchNote.setText("暂无记录");
                    noteAdapter.notifyDataSetChanged();
                    break;
                case StaticData.SEARCH_NOTE_FAILED:
                    Toast.makeText(SearchNoteActivity.this, "搜索失败，请检查网络", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchnote_layout);
        initView();
    }

    private void initView(){
        etSearcNote = (EditText) findViewById(R.id.et_searchnote);
        ivSearchNote = (ImageView) findViewById(R.id.iv_searchnote);
        tvSearchNote = (TextView) findViewById(R.id.tv_searchnote);
        lvSearchNote = (ListView) findViewById(R.id.lv_searchnote);

        ivSearchNote.setOnClickListener(this);

        noteAdapter = new NoteAdapter(SearchNoteActivity.this, getNote_list_filtered);
        lvSearchNote.setAdapter(noteAdapter);

        lvSearchNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchNoteActivity.this, NoteDetailActivity.class);
                intent.putExtra("note", getNote_list_filtered.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_searchnote:

                String title = etSearcNote.getText().toString().trim();
                note_list.clear();

                if (!TextUtils.isEmpty(title)) {
                    tvSearchNote.setVisibility(View.VISIBLE);

                    //2018/12/29 开启线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BmobQuery<Note> query = new BmobQuery<Note>();
                            query.setLimit(10);
                            query.findObjects(new FindListener<Note>() {
                                @Override
                                public void done(List<Note> list, BmobException e) {
                                    if (e == null) {
                                        if (list.size() != 0) {
                                            note_list.clear();
                                            getNote_list_filtered.clear();
                                            note_list.addAll(list);
                                            for(int i=0;i<list.size();i++){
                                                if(note_list.get(i).getContent().contains(etSearcNote.getText().toString().trim())){
                                                    getNote_list_filtered.add(note_list.get(i));
                                                }
                                            }
                                            if (getNote_list_filtered.size()!=0){
                                                handler.sendEmptyMessage(StaticData.SEARCH_NOTE_SUCCESS);
                                            }
                                            else {
                                                handler.sendEmptyMessage(StaticData.SEARCH_NOTE_SUCCESS_NODATA);
                                            }

                                        } else {
                                            handler.sendEmptyMessage(StaticData.SEARCH_NOTE_SUCCESS_NODATA);
                                        }
                                    } else {
                                        handler.sendEmptyMessage(StaticData.SEARCH_NOTE_FAILED);
                                    }
                                }
                            });

                        }
                    }).start();

                } else {
                    Toast.makeText(SearchNoteActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
