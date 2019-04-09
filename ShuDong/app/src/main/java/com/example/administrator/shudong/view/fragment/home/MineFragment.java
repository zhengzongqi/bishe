package com.example.administrator.shudong.view.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.administrator.shudong.view.fragment.BaseFragment;
import com.example.administrator.shudong.R;

/**
 * Created by zzq
 * on         2019/4/6.
 * function:  个人fragment
 */
public class MineFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        return view;
    }
}
