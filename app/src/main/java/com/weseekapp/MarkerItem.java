package com.weseekapp;

public class MarkerItem {
    String storeName;
    String storeLocation;
    String stars;

    double lat;
    double lon;

    public MarkerItem(String storeName, String storeLocation, String stars, double lat, double lon){
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.stars = stars;
        this.lat = lat;
        this.lon = lon;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

}
