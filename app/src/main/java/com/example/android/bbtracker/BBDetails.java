package com.example.android.bbtracker;

public class BBDetails {

    public String Name;
    public int PhoneNumber;
    public String Address;
    public String Zone;

    public BBDetails(){

    }

    public BBDetails(String Name, int PhoneNumber, String Address, String Zone){
        this.Name=Name;
        this.PhoneNumber= PhoneNumber;
        this.Address=Address;
        this.Zone=Zone;
    }
}
