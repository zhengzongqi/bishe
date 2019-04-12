package com.example.administrator.shudong.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.bean.Note;
import com.example.administrator.shudong.bean.Type;

import java.util.List;

/**
 * Created by zzq
 * on         2019/4/9
 * function:  兴趣圈adapter
 */
public class BanAdapter  extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<Type> mList;
    private Type type;

    public BanAdapter(Context mContext, List<Type> mList) {
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
        BanAdapter.ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new BanAdapter.ViewHolder();

            //inflater绑定item的XML布局
            convertView = inflater.inflate(R.layout.item_searchban, null);
            viewHolder.ban_name=(TextView)convertView.findViewById(R.id.item_searchban_banname);
            viewHolder.ban_people=(TextView)convertView.findViewById(R.id.item_searchban_banpeople);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (BanAdapter.ViewHolder) convertView.getTag();
        }

        //开始设置数据
        type=mList.get(position);
        viewHolder.ban_name.setText(type.getTypeName()+"");
        viewHolder.ban_people.setText(type.getNum_People()+"人参与");

        return convertView;
    }


    class ViewHolder{
        public TextView ban_name;
        public TextView ban_people;
    }

}
