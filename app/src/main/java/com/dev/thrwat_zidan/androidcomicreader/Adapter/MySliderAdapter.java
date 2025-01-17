package com.dev.thrwat_zidan.androidcomicreader.Adapter;

import com.dev.thrwat_zidan.androidcomicreader.Model.Banner;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MySliderAdapter extends SliderAdapter {
    private List<Banner> bannerList;

    public MySliderAdapter(List<Banner> banners) {
        this.bannerList = banners;
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(bannerList.get(position).getLink());

    }
}
