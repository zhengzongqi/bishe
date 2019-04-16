package com.example.administrator.shudong.activity.user.Mine_Fagment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.BaseActivity;
import com.example.administrator.shudong.adapter.MyMessageAdapter;
import com.example.administrator.shudong.bean.Comment;
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
 * function:  我的消息页面
 */
public class MyMessageActivity  extends BaseActivity implements View.OnClickListener{

    ListView listView;
    MyMessageAdapter myMessageAdapter;
    List<Comment> commentList=new ArrayList<>();
    List<String> nid=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        initView();
        initData();
    }

    private void initView(){
        listView=(ListView)findViewById(R.id.lv_mymessage);
        myMessageAdapter=new MyMessageAdapter(MyMessageActivity.this,commentList);
        listView.setAdapter(myMessageAdapter);

    }

    private void initData(){
        //设置具体的值
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.addWhereEqualTo("UserId", user.getUsername());
        query.setLimit(50);
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        for (int i = 0; i < list.size(); i++) {
                            nid.add(list.get(i).getObjectId());
                        }
                        //根据noteid查找comment
                        BmobQuery<Comment> q = new BmobQuery<Comment>();
                        q.addWhereContainedIn("NoteId", nid);
                        q.setLimit(50);
                        q.findObjects(new FindListener<Comment>() {
                            @Override
                            public void done(List<Comment> list, BmobException e) {
                                if (e == null) {
                                    if (list.size() != 0) {
                                        commentList.addAll(list);
                                        myMessageAdapter.notifyDataSetChanged();

                                    } else {
                                        //textView.setText("你暂时未收到赞");
                                    }


                                } else {
                                    //textView.setText("数据获取失败，请检查网络");
                                }

                            }
                        });
                    } else {
                        //textView.setText("你还没有发帖，暂无评论");
                    }

                } else {
                    //textView.setText("数据获取失败，请检查网络");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
