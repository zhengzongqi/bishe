package com.example.administrator.shudong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.shudong.bean.Comment;
import com.example.administrator.shudong.R;


import java.util.List;


/**
 * Created by zzq
 * on         2019/4/7
 * function:  回复适配器
 */
public class CommentAdapter extends BaseAdapter implements View.OnClickListener{

    private Context mContext;
    private LayoutInflater inflater;
    private List<Comment> mList;
    private Comment comment;
    private Callback mCallback;

    public interface Callback {
        public void click(View v);
    }

    public CommentAdapter(Context mContext, List<Comment> mList, Callback mCallback) {
        this.mContext = mContext;
        this.mList = mList;
        this.mCallback = mCallback;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CommentAdapter(Context mContext, List<Comment> mList){
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){

            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_comment, null);
            viewHolder.Content=(TextView)convertView.findViewById(R.id.item_comment_content);
            viewHolder.Num_Like =(TextView)convertView.findViewById(R.id.item_comment_zancount);
            viewHolder.time=(TextView)convertView.findViewById(R.id.item_comment_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        comment=mList.get(position);

        viewHolder.Content.setText(comment.getContent());
        viewHolder.time.setText(comment.getUpdatedAt().substring(0,10));
        viewHolder.Num_Like.setText(""+comment.getNum_Like());

        return convertView;
    }


    class ViewHolder{
        public TextView Content;
        public TextView Num_Like;
        public TextView time;
    }


    @Override
    public void onClick(View v) {

    }
}
