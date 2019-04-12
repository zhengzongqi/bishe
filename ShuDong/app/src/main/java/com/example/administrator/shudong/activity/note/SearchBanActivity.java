package com.example.administrator.shudong.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.BaseActivity;
import com.example.administrator.shudong.adapter.BanAdapter;
import com.example.administrator.shudong.bean.Comment;
import com.example.administrator.shudong.bean.Type;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by zzq
 * on         2019/4/9
 * function:  查找兴趣圈页面
 */
public class SearchBanActivity extends BaseActivity implements View.OnClickListener{

    private Button button_searchban;
    private EditText et_searchban;
    private ListView lvsearchban;
    private BanAdapter banadapter;
    private List<Type> mlist=new ArrayList<>();
    private BmobQuery<Type> query=new BmobQuery<Type>();
    private Type type_result;
    private Intent intent;

    private Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            intent.putExtra("type",type_result);
            startActivity(intent);
        }
    });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchban);
        initView();
        initBanData();
    }

    private void initView(){
        button_searchban=(Button)findViewById(R.id.bt_searchban);
        et_searchban=(EditText)findViewById(R.id.et_searchban);
        button_searchban.setOnClickListener(this);

        lvsearchban=(ListView)findViewById(R.id.lv_searchban);
        banadapter=new BanAdapter(this,mlist);
        lvsearchban.setAdapter(banadapter);
    }

    private void initBanData(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                BmobQuery<Type> query = new BmobQuery<Type>();
                query.findObjects(new FindListener<Type>() {
                    @Override
                    public void done(List<Type> list, BmobException e) {
                        mlist.addAll(list);
                        banadapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_searchban:
                final String ban_name=et_searchban.getText().toString();
                intent=new Intent(SearchBanActivity.this,BanNoteActivity.class);
                search_type(ban_name);
        }
    }


    private void search_type(String type_name){

        query.addWhereEqualTo("TypeName",type_name);
        new Thread(new Runnable() {
            @Override
            public void run() {
                query.findObjects(new FindListener<Type>() {
                    @Override
                    public void done(List<Type> list, BmobException e) {
                        if(e==null){
                            type_result=list.get(0);
                            t.start();
                        }
                    }
                });
            }
        }).start();

    }

}
