package com.example.parasrawat.projectinfroid.ModelClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class Report implements Serializable {

    String byuser,title,type,desc,place,time,status,key;
    ArrayList<String> imguris=new ArrayList<>();

    public Report(){
        status="sent";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<String> getImguris() {
        return imguris;
    }

    public void setImguris(ArrayList<String> imguris) {
        this.imguris = imguris;
    }

    public String getByuser() {
        return byuser;
    }

    public void setByuser(String byuser) {
        this.byuser = byuser;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
