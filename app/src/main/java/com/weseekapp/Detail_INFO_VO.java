package com.weseekapp;

import java.io.Serializable;

public class Detail_INFO_VO implements Serializable {
    String adr; // 주소
    String hours; // 영업시간
    String tel; // 전화번호
    Double lat;
    Double lon;


    public Detail_INFO_VO(String adr, String hours, String tel, Double lat, Double lon){ // 생성자

        this.adr = adr;
        this.hours = hours;
        this.tel = tel;
        this.lat = lat;
        this.lon = lon;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

}
