package com.weseekapp;

import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.RequestBuilder;

public class Page2VO {
    RequestBuilder vo_store_pic;
    String vo_store_id;
    String vo_store_addr;
    CompoundButton vo_btn_favorite;
    ImageView vo_coupon;
    RatingBar vo_stars;

    public Page2VO(RequestBuilder vo_store_pic, String vo_store_id, String vo_store_addr, CompoundButton vo_btn_favorite, ImageView vo_coupon, RatingBar vo_stars) {
        this.vo_store_pic = vo_store_pic;
        this.vo_store_id = vo_store_id;
        this.vo_store_addr = vo_store_addr;
        this.vo_btn_favorite = vo_btn_favorite;
        this.vo_coupon = vo_coupon;
        this.vo_stars = vo_stars;
    }

    public RequestBuilder getVo_store_pic() {
        return vo_store_pic;
    }

    public void setVo_store_pic(RequestBuilder vo_store_pic) {
        this.vo_store_pic = vo_store_pic;
    }

    public String getVo_store_id() {
        return vo_store_id;
    }

    public void setVo_store_id(String vo_store_id) {
        this.vo_store_id = vo_store_id;
    }

    public String getVo_store_addr() {
        return vo_store_addr;
    }

    public void setVo_store_addr(String vo_store_addr) {
        this.vo_store_addr = vo_store_addr;
    }

    public CompoundButton getVo_btn_favorite() { return vo_btn_favorite; }

    public void setVo_btn_favorite(CompoundButton vo_btn_favorite) { this.vo_btn_favorite = vo_btn_favorite; }

    public ImageView getVo_coupon() { return vo_coupon; }

    public void setVo_coupon(ImageView vo_coupon) { this.vo_coupon = vo_coupon; }

    public RatingBar getVo_stars() { return  vo_stars; }

    public void setVo_stars(RatingBar vo_stars) { this.vo_stars = vo_stars; }
}