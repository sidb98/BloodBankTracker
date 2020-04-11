package com.example.android.bbtracker;

public class LastUpdate {

    public String BBName;
    public String Date;


    public LastUpdate(){

    }
    public LastUpdate(String BBName,String Date){
        this.BBName=BBName;
        this.Date=Date;
    }

    public String getBBName() {
        return BBName;
    }

    public void setBBName(String BBName) {
        this.BBName = BBName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}