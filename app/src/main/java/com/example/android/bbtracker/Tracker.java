package com.example.android.bbtracker;

public class Tracker {

    public String BloodGroup;
    public int BGCount;


    public Tracker(String BloodGroup, int BGcount) {
        this.BloodGroup=BloodGroup;
        this.BGCount=BGcount;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public int getBGCount() {
        return BGCount;
    }

    public void setBGCount(int BGCount) {
        this.BGCount = BGCount;
    }
}
