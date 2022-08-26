package com.weseekapp;

import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;

public class page2VO {

    private Drawable page2_store_pic;
    private String page2_store_name;
    private String page2_store_addr;


    public page2VO(Drawable page2_store_pic, String page2_store_name, String page2_store_addr) {
        this.page2_store_pic = page2_store_pic;
        this.page2_store_name = page2_store_name;
        this.page2_store_addr = page2_store_addr;

    }

    public Drawable getPage2_store_pic() {
        return page2_store_pic;
    }

    public void setPage2_store_pic(Drawable page2_store_pic) {
        this.page2_store_pic = page2_store_pic;
    }

    public String getPage2_store_name() {
        return page2_store_name;
    }

    public void setPage2_store_name(String page2_store_name) {
        this.page2_store_name = page2_store_name;
    }

    public String getPage2_store_addr() {
        return page2_store_addr;
    }

    public void setPage2_store_addr(String page2_store_addr) {
        this.page2_store_addr = page2_store_addr;
    }

}
