package com.example.administrator.shudong.revealbanner;

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


import java.util.ArrayList;
import java.util.List;

/**
 * Banner指示图
 */
public class BannerActivity extends AppCompatActivity implements OnBannerItemClickListener {

    BannerBgContainer bannerBgContainer;
    LoopLayout loopLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        setFullScreen();
        loopLayout = findViewById(R.id.loop_layout);
        bannerBgContainer = findViewById(R.id.banner_bg_container);
        loopLayout.setLoop_ms(2000);//轮播的速度(毫秒)
        loopLayout.setLoop_duration(300);//滑动的速率(毫秒)
        loopLayout.setScaleAnimation(true);// 设置是否需要动画
        loopLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        loopLayout.setIndicatorLocation(IndicatorLocation.Center);//指示器位置-中Center
        loopLayout.initializeData(this);
        // 准备数据
        ArrayList<BannerInfo> bannerInfos = new ArrayList<>();
        List<Object> bgList = new ArrayList<>();
        bannerInfos.add(new BannerInfo(R.mipmap.banner_01, "1"));
        bannerInfos.add(new BannerInfo(R.mipmap.banner_02, "2"));
        bannerInfos.add(new BannerInfo(R.mipmap.banner_03, "3"));
        bannerInfos.add(new BannerInfo(R.mipmap.banner_04, "4"));
        bannerInfos.add(new BannerInfo(R.mipmap.banner_05, "5"));
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
        loopLayout.setOnBannerItemClickListener(this);
        loopLayout.setOnBannerItemClickListener(this);
        if (bannerInfos.size() == 0) {
            return;
        }
        loopLayout.setLoopData(bannerInfos);
        bannerBgContainer.setBannerBackBg(this, bgList);
        loopLayout.setBannerBgContainer(bannerBgContainer);
        loopLayout.startLoop();
    }

    @Override
    public void onBannerClick(int index, ArrayList<BannerInfo> banner) {

    }


    void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
