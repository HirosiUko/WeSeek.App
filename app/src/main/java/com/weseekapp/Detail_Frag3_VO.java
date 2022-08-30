package com.weseekapp;

import android.graphics.drawable.Drawable;

public class Detail_Frag3_VO {

    private Drawable profile;
    private String tag, review;
    private int rate;

    public Detail_Frag3_VO(Drawable profile, String tag, String review) {
        this.profile = profile;
        this.tag = tag;
        this.review = review;
        this.rate = rate;
    }

    public Drawable getProfile() {
        return profile;
    }

    public void setProfile(Drawable profile) {
        this.profile = profile;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}

