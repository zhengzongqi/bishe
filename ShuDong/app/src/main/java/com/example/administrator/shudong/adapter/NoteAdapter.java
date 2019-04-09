package com.example.administrator.shudong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shudong.bean.Note;
import com.example.administrator.shudong.R;

import java.util.List;

/**
 * Created by zzq
 * on         2019/4/6
 * function:  帖子适配器
 */

public class NoteAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater inflater;
    private List<Note> mList;
    private Note note;

    public NoteAdapter(Context mContext, List<Note> mList) {
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
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();

            //inflater绑定item的XML布局
            convertView = inflater.inflate(R.layout.item_note, null);
            viewHolder.type=(TextView)convertView.findViewById(R.id.item_note_type);
            viewHolder.time=(TextView)convertView.findViewById(R.id.item_note_time);
            viewHolder.content=(TextView)convertView.findViewById(R.id.item_note_content);
            viewHolder.zan=(ImageView) convertView.findViewById(R.id.item_note_zan);
            viewHolder.zancount=(TextView)convertView.findViewById(R.id.item_note_zancount);
            viewHolder.replay=(ImageView)convertView.findViewById(R.id.item_note_replay);
            viewHolder.replaycount=(TextView)convertView.findViewById(R.id.item_note_replaycount);


            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //开始设置数据
        note=mList.get(position);
        viewHolder.type.setText(note.getTypeId());
        viewHolder.time.setText(note.getUpdatedAt().substring(0,10));
        viewHolder.content.setText(note.getContent());

        viewHolder.zancount.setText(""+note.getNum_Like());
        viewHolder.replaycount.setText(""+note.getNum_Reply());


        return convertView;
    }


    //每个ITEM里一连串的组件
    class ViewHolder{
        public TextView title;
        public TextView type;
        public TextView time;
        public TextView content;
        public ImageView zan;
        public TextView zancount;
        public ImageView replay;
        public TextView replaycount;
    }


}
