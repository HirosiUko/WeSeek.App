package com.weseekapp;

import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;

public class Page2VO {
    int vo_store_pic;
    String vo_store_id;
    String vo_store_addr;

    public Page2VO(int vo_store_pic, String vo_store_id, String vo_store_addr) {
        this.vo_store_pic = vo_store_pic;
        this.vo_store_id = vo_store_id;
        this.vo_store_addr = vo_store_addr;
    }

    public int getVo_store_pic() {
        return vo_store_pic;
    }

    public void setVo_store_pic(int vo_store_pic) {
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
}