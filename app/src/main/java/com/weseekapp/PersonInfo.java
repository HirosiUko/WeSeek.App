package com.weseekapp;

public class PersonInfo {

    private String name;
    private String nickName;
    private String email;
    private String lastVisit;
    private boolean isLogin = false;

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // private construct
    private PersonInfo() {
        this.name = "아냐포저";
        this.nickName = "Anya";
        this.email = "Anya.taylor@gmail.com";
        this.lastVisit = "";
    }

    private static class InnerInstanceClazz {
        private static final PersonInfo instance = new PersonInfo();
    }

    public static PersonInfo getInstance() {
        return InnerInstanceClazz.instance;
    }
}
