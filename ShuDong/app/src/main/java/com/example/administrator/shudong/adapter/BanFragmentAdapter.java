package com.example.administrator.shudong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.bean.Type_Like;

import java.util.List;

/**
 * Created by zzq
 * on         2019/4/9
 * function:  首页兴趣圈adapter
 */
public class BanFragmentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<Type_Like> mList;
    private Type_Like type_like;

    public BanFragmentAdapter(Context mContext, List<Type_Like> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取inflater对象
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BanFragmentAdapter.ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new BanFragmentAdapter.ViewHolder();

            //inflater绑定item的XML布局
            convertView = inflater.inflate(R.layout.item_ban, null);
            viewHolder.ban_name=(TextView)convertView.findViewById(R.id.item_ban_banname);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (BanFragmentAdapter.ViewHolder) convertView.getTag();
        }

        //开始设置数据
        type_like =mList.get(position);
        viewHolder.ban_name.setText(type_like.getTypeName()+"");

        return convertView;
    }

    class ViewHolder{
       public TextView ban_name;
    }
}
