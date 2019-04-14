package wikikii.bannerlib.banner.listener;


import wikikii.bannerlib.banner.bean.BannerInfo;

import java.util.ArrayList;

public interface OnBannerItemClickListener {
    /**
     * banner click
     *
     * @param index  subscript
     * @param banner bean
     */
    void onBannerClick(int index, ArrayList<BannerInfo> banner);
}
