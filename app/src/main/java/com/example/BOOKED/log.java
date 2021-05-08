package com.example.BOOKED;

public class log {
    String Uid;
    String Username;
    String password;

    public log(){

    }

    public log(String uid,String username, String password) {
        this.Uid = uid;
        this.Username = username;
        this.password = password;
    }

    public String getUid() {
        return Uid;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return password;
    }
}
