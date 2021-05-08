package com.example.BOOKED;


public class Info {



    private String email;
    private String phone, org, purp,hall,date,time,approv,doc;

    public Info() {

    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getApprov() {
        return approv;
    }

    public void setApprov(String approv) {
        this.approv = approv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getPurp() {
        return purp;
    }

    public void setPurp(String purp) {
        this.purp = purp;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }





    public Info(String email, String phone, String org, String purp, String hall, String date, String time, String approv, String doc) {
        this.email = email;
        this.phone = phone;
        this.org = org;
        this.purp = purp;
        this.hall = hall;
        this.date = date;
        this.time = time;
        this.approv = approv;
        this.doc = doc;
       // this.doc = doc;
    }



}
