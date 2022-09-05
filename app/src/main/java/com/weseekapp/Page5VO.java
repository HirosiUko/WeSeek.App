package com.weseekapp;

import android.widget.ImageView;

public class Page5VO {
    int vo_profile;
    String vo_st_id;
    String vo_st_ad;
    String vo_st_date;
    String vo_rv_count;

    public Page5VO(int vo_profile, String vo_st_id, String vo_st_ad, String vo_st_date, String vo_rv_count){
        this.vo_profile = vo_profile;
        this.vo_st_id = vo_st_id;
        this.vo_st_ad = vo_st_ad;
        this.vo_st_date = vo_st_date;
        this.vo_rv_count = vo_rv_count;
    }

    public int getVo_profile() {
        return vo_profile;
    }

    public void setVo_profile(int vo_profile) {
        this.vo_profile = vo_profile;
    }

    public String getVo_st_id() {
        return vo_st_id;
    }

    public void setVo_st_id(String vo_st_id) {
        this.vo_st_id = vo_st_id;
    }

    public String getVo_st_ad() {
        return vo_st_ad;
    }

    public void setVo_st_ad(String vo_st_ad) {
        this.vo_st_ad = vo_st_ad;
    }

    public String getVo_st_date() {
        return vo_st_date;
    }

    public void setVo_st_date(String vo_st_date) {
        this.vo_st_date = vo_st_date;
    }

    public String getVo_rv_count() {
        return vo_rv_count;
    }

    public void setVo_rv_count(String vo_rv_count) {
        this.vo_rv_count = vo_rv_count;
    }
}