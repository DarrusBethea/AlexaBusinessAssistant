package com.example.androidalexaskillproject;

import java.util.Date;
import java.util.UUID;
// FAH 2/16/2020 : basic profit class for are profit sheets just vars would like to add or delete
public class Profits {

    private String mName;
    private Date mDate;
    private String mAmount;


    public String getmAmount() {
        return mAmount;
    }

    public void setmAmount(String mAmount) {
        this.mAmount = mAmount;
    }


    public String getmName() {
        return mName;
    }

    public  void setmName(String mName) {
        this.mName = mName;
    }





    public Profits()
    {
        mDate = new Date();    }
}