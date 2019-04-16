package com.example.administrator.shudong.view.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.activity.note.BanNoteActivity;
import com.example.administrator.shudong.adapter.BanAdapter;
import com.example.administrator.shudong.adapter.BanFragmentAdapter;
import com.example.administrator.shudong.bean.Type;
import com.example.administrator.shudong.bean.Type_Like;
import com.example.administrator.shudong.bean.User;
import com.example.administrator.shudong.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by zzq
 * on         2019/4/6.
 * function:  版块fragment
 */
public class BanFragment extends BaseFragment implements View.OnClickListener{

    private ListView listView;
    private BanFragmentAdapter banFragmentAdapter;
    private List<Type_Like> typeList=new ArrayList<>();
    private Type type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ban, null);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view){

        listView=(ListView) view.findViewById(R.id.lv_ban);
        banFragmentAdapter=new BanFragmentAdapter(getActivity(),typeList);
        listView.setAdapter(banFragmentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Type_Like type_like=typeList.get(position);
                BmobQuery<Type> query = new BmobQuery<Type>();
                query.addWhereEqualTo("TypeName",type_like.getTypeName());
                query.findObjects(new FindListener<Type>() {
                    @Override
                    public void done(List<Type> list, BmobException e) {
                        if(e==null){
                            type =list.get(0);
                            Intent intent=new Intent(getActivity(),BanNoteActivity.class);
                            intent.putExtra("type",type);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private void initData(){
        User user=BmobUser.getCurrentUser(User.class);
        BmobQuery<Type_Like> query = new BmobQuery<Type_Like>();
        query.order("-updatedAt");
        query.addWhereEqualTo("UserId",user.getObjectId());
        query.findObjects(new FindListener<Type_Like>() {
                    @Override
                    public void done(List<Type_Like> list, BmobException e) {
                        if(e==null){
                            typeList.clear();
                            typeList.addAll(list);
                            banFragmentAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }


    @Override
    public void onClick(View v) {


    }
}
