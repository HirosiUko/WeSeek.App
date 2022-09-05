package com.weseekapp;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreInfo {

    String evl_fda; // evl_fda
    String storeName; // store_name
    String latitude; // store_long
    String longitude; // store_lat
    String address; // store_addr
    String star_of_cleanliness; // evl_grade
    String store_tel; // store_tel
    String store_id; // store_id
    String store_hours; // store_hours
    ArrayList<String> store_menu = new ArrayList<>();
    ArrayList<String> img_list = new ArrayList<>();
    ArrayList<String> comments = new ArrayList<>();

    public StoreInfo(String evl_fda, String storeName, String latitude, String longitude, String address, String star_of_cleanliness, String store_tel, String store_id, String store_hours) {
        this.evl_fda = evl_fda;
        this.storeName = storeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.star_of_cleanliness = star_of_cleanliness;
        this.store_tel = store_tel;
        this.store_id = store_id;
        this.store_hours = store_hours;
    }

    @Override
    public String toString() {
        return "StoreInfo{" +
                "evl_fda='" + this.evl_fda + '\'' +
                ", storeName='" + storeName + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", address='" + address + '\'' +
                ", star_of_cleanliness='" + star_of_cleanliness + '\'' +
                ", store_tel='" + store_tel + '\'' +
                ", store_id='" + store_id + '\'' +
                ", store_hours='" + store_hours + '\'' +
                '}';
    }
}
