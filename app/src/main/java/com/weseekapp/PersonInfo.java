package com.weseekapp;

import com.android.volley.RequestQueue;

public class PersonInfo {

    private String name;
    private String nickName;
    private String email;
    private String createDate;
    private String userProfile;

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public String getUserPic() {
        return userPic;
    }

    private String userPic;
    public boolean isLogin = false;

    public void setEveryThing(String name, String nickName, String email, String createDate, String userProfile, String userPic) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.createDate = createDate;
        this.userProfile = userProfile;
        this.userPic = userPic;
        isLogin = true;
    }

    // private construct
    private PersonInfo() {
        this.name = "아냐포저";
        this.nickName = "Anya";
        this.email = "Anya.taylor@gmail.com";
        this.createDate = "";
        this.userProfile = "";
        this.userPic = "";
    }

    private static class InnerInstanceClazz {
        private static final PersonInfo instance = new PersonInfo();
    }

    public static RequestQueue requestQueue;

    public static PersonInfo getInstance() {
        return InnerInstanceClazz.instance;
    }
}
