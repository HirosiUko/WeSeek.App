package com.weseekapp;

import java.util.ArrayList;

public class StoreInfo {
    String storeName;
    float latitude;
    float longitude;
    String address;
    String star_of_cleanliness;
    int star_of_review;
    ArrayList<String> comments = new ArrayList<>();

    public StoreInfo(String storeName, float latitude, float longitude, String address, String star_of_cleanliness) {
        this.storeName = storeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.star_of_cleanliness = star_of_cleanliness;
    }

    @Override
    public String toString() {
        return "StoreInfo{" +
                "storeName='" + storeName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", star_of_cleanliness='" + star_of_cleanliness + '\'' +
                ", star_of_review=" + star_of_review +
                ", comments=" + comments +
                '}';
    }
}
