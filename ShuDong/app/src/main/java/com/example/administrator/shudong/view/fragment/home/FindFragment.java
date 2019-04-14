package com.example.administrator.shudong.view.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.note.NoteDetailActivity;
import com.example.administrator.shudong.activity.note.SearchBanActivity;
import com.example.administrator.shudong.activity.note.SearchNoteActivity;
import com.example.administrator.shudong.view.fragment.BaseFragment;
import com.example.administrator.shudong.bean.Note;
import com.example.administrator.shudong.adapter.NoteAdapter;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by zzq
 * on         2019/4/6.
 * function:  发现fragment
 */
public class FindFragment extends BaseFragment implements View.OnClickListener{

    private EditText etSearch;
    private ImageView ivSearch;
    private ImageView ivPost;

    private ListView lvNote;
    private Banner banner;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<String> listp=new ArrayList<>();

    private List<Note> listNote=new ArrayList<>();
    private NoteAdapter noteadapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        etSearch = (EditText) view.findViewById(R.id.et_note);
        etSearch.setInputType(InputType.TYPE_NULL);
        etSearch.setEnabled(false);
        etSearch.setOnClickListener(this);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(this);
        ivPost = (ImageView) view.findViewById(R.id.iv_post);
        ivPost.setOnClickListener(this);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_bfbfbf);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();   //进行刷新操作
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        lvNote=(ListView)view.findViewById(R.id.lv_note);
        //设置适配器
        noteadapter=new NoteAdapter(getActivity(),listNote);
        lvNote.setAdapter(noteadapter);
        lvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),NoteDetailActivity.class);
                intent.putExtra("note",listNote.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    //初始化数据
    private void initData() {
            //获取数据
            BmobQuery<Note> query = new BmobQuery<Note>();
            query.setLimit(50);
            //按时间降序
            query.order("-updatedAt");
            query.findObjects(new FindListener<Note>() {
                @Override
                public void done(List<Note> list, BmobException e) {
                    if(e==null){
                        listNote.clear();
                        listNote.addAll(list);
                        //重新调用ADAPTER更新页面
                        noteadapter.notifyDataSetChanged();

                    }else{
                        Toast.makeText(getActivity(), "加载数据失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){

            case R.id.iv_search:
                intent=new Intent(getActivity(),SearchNoteActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_post:
                intent=new Intent(getActivity(),SearchBanActivity.class);
                startActivity(intent);
                break;
        }

    }
}
