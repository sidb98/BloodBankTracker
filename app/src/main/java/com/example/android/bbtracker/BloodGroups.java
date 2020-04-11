package com.example.android.bbtracker;

public class BloodGroups {

    public int AP;
    public int AN;
    public int BP;
    public int BN;
    public int ABP;
    public int ABN;
    public int OP;
    public int ON;

    public BloodGroups(){

    }

    public BloodGroups(int AP,int AN,int BP,int BN,int ABP,int ABN,int OP,int ON ){
        this.ABN=ABN;
        this.ABP=ABP;
        this.AN=AN;
        this.AP=AP;
        this.BN=BN;
        this.BP=BP;
        this.ON=ON;
        this.OP=OP;

    }


}