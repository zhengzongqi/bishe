package com.example.administrator.shudong.view.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shudong.activity.user.Mine_Fagment.MyMessageActivity;
import com.example.administrator.shudong.activity.user.Mine_Fagment.MyNoteActivity;
import com.example.administrator.shudong.view.fragment.BaseFragment;
import com.example.administrator.shudong.R;
import com.example.administrator.shudong.bean.User;
import com.makeramen.roundedimageview.RoundedImageView;

import cn.bmob.v3.BmobUser;

/**
 * Created by zzq
 * on         2019/4/6.
 * function:  个人fragment
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{

    private RoundedImageView profile_mine;
    private TextView username;
    private TextView EditUser;
    private TextView sex;
    private TextView age;
    private TextView mynote;
    private TextView mymessage;
    private TextView modifypassword;
    private TextView feedback;
    private TextView about;
    private TextView update;
    private TextView signout;
    private TextView exit;

    private boolean thesex;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
        return view;
    }

    private void initView(View view){
        profile_mine=(RoundedImageView)view.findViewById(R.id.profile_image);
        username=(TextView)view.findViewById(R.id.tv_username);
        EditUser=(TextView)view.findViewById(R.id.edit_user);
        sex=(TextView)view.findViewById(R.id.tv_sex);
        age=(TextView)view.findViewById(R.id.tv_age);;
        mynote=(TextView)view.findViewById(R.id.tv_sell);;
        mymessage=(TextView)view.findViewById(R.id.tv_message);;
        modifypassword=(TextView)view.findViewById(R.id.tv_modify);
        feedback=(TextView)view.findViewById(R.id.tv_back);
        about=(TextView)view.findViewById(R.id.tv_about);
        update=(TextView)view.findViewById(R.id.tv_update);
        signout=(TextView)view.findViewById(R.id.tv_sign_out);
        exit=(TextView)view.findViewById(R.id.tv_exit_system);

        EditUser.setOnClickListener(this);
        mynote.setOnClickListener(this);
        mymessage.setOnClickListener(this);
        modifypassword.setOnClickListener(this);
        feedback.setOnClickListener(this);
        about.setOnClickListener(this);
        update.setOnClickListener(this);
        signout.setOnClickListener(this);
        exit.setOnClickListener(this);

        User user=BmobUser.getCurrentUser(User.class);
        username.setText(user.getUsername()+"");
        thesex=user.getSex();
        if(thesex==true){
            sex.setText("男");
        }
        else {
            sex.setText("女");
        }
        age.setText(user.getAge()+"");


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sell:
                startActivity(new Intent(getActivity(), MyNoteActivity.class));
                break;
            case R.id.tv_message:
                startActivity(new Intent(getActivity(), MyMessageActivity.class));
                break;

        }
    }
}
