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

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.shudong.R;
import wikikii.bannerlib.banner.IndicatorLocation;
import wikikii.bannerlib.banner.LoopLayout;
import wikikii.bannerlib.banner.LoopStyle;
import wikikii.bannerlib.banner.OnDefaultImageViewLoader;
import wikikii.bannerlib.banner.bean.BannerInfo;
import wikikii.bannerlib.banner.listener.OnBannerItemClickListener;
import wikikii.bannerlib.banner.view.BannerBgContainer;


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
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<String> listp=new ArrayList<>();

    private List<Note> listNote=new ArrayList<>();
    private NoteAdapter noteadapter;

    BannerBgContainer bannerBgContainer;
    LoopLayout loopLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, null);
        initView(view);
        initBanner(view);
        return view;
    }

    private void initBanner(View view){
        //setFullScreen();
        loopLayout = view.findViewById(R.id.loop_layout);
        bannerBgContainer = view.findViewById(R.id.banner_bg_container);
        loopLayout.setLoop_ms(3000);//轮播的速度(毫秒)
        loopLayout.setLoop_duration(500);//滑动的速率(毫秒)
        loopLayout.setScaleAnimation(true);// 设置是否需要动画
        loopLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        loopLayout.setIndicatorLocation(IndicatorLocation.Center);//指示器位置-中Center
        loopLayout.initializeData(getActivity());
        // 准备数据
        ArrayList<BannerInfo> bannerInfos = new ArrayList<>();
        List<Object> bgList = new ArrayList<>();
        bannerInfos.add(new BannerInfo(R.mipmap.banner_01, "1"));
        bannerInfos.add(new BannerInfo(R.mipmap.banner_02, "2"));
        bannerInfos.add(new BannerInfo(R.mipmap.banner_03, "3"));
        //bannerInfos.add(new BannerInfo(R.mipmap.banner_04, "4"));
        //bannerInfos.add(new BannerInfo(R.mipmap.banner_05, "5"));
        bgList.add(R.mipmap.banner_bg1);
        bgList.add(R.mipmap.banner_bg2);
        // 设置监听
        loopLayout.setOnLoadImageViewListener(new OnDefaultImageViewLoader() {
            @Override
            public void onLoadImageView(ImageView view, Object object) {
                Glide.with(view.getContext())
                        .load(object)
                        .into(view);
            }
        });
        if (bannerInfos.size() == 0) {
            return;
        }
        loopLayout.setLoopData(bannerInfos);
        bannerBgContainer.setBannerBackBg(getActivity(), bgList);
        loopLayout.setBannerBgContainer(bannerBgContainer);
        loopLayout.startLoop();
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



    void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getActivity().getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
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
