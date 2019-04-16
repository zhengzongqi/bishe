package com.example.administrator.shudong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.shudong.R;
import com.example.administrator.shudong.bean.Comment;
import com.example.administrator.shudong.bean.Note;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zzq
 * on         2019/4/16
 * function:  我的消息适配器
 */
public class MyMessageAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater inflater;
    private List<Comment> mList;
    private Note mNote;
    private Comment comment;
    private ViewHolder viewHolder=null;

    public MyMessageAdapter(Context mContext, List<Comment> mList) {
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
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //如果是第一次加载
        if(view==null){
            viewHolder=new ViewHolder();

            view=inflater.inflate(R.layout.item_mymessage,null);
            viewHolder.note=(TextView)view.findViewById(R.id.item_message_comment_tvnote);
            viewHolder.content=(TextView) view.findViewById(R.id.item_message_comment_tvcontent);
            //设置缓存
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }

        //设置数据
        comment=mList.get(position);

        BmobQuery<Note> query =new BmobQuery<Note>();
        query.addWhereEqualTo("objectId",comment.getNoteId());
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e==null){
                    mNote=list.get(0);
                    viewHolder.note.setText(mNote.getContent());
                    viewHolder.content.setText(comment.getContent());
                }
            }
        });
        viewHolder.note.setTag(position);
        return view;

    }

    private class ViewHolder{
        private TextView note;
        private TextView content;
    }
}
