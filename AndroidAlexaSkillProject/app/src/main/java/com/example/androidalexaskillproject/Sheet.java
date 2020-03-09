package com.example.androidalexaskillproject;


/*  M.Marinaro 3/4/20 : A sheet class to replace the specific Profits class
    TODO Still needs further abstraction, to include only a list of column values
    A better concept might be sheet_row

    CURRENTLY UNUSED
*/
public class Sheet {

    private String mName;
    private String mAmount;
    private String mDate;
    private String mLastname;

    public String mSheetname = "profits";

    public String getmSheetname(){ return mSheetname;}
    public void setmSheetname(String s) {this.mSheetname = s;}

    public String getmDate() { return mDate; }

    public void setmDate(String mDate) { this.mDate = mDate; }

    public String getmLastname() { return mLastname; }

    public void setmLastname(String mLastname) { this.mLastname = mLastname; }

    public String getmAmount() { return mAmount; }

    public void setmAmount(String mAmount) {
        this.mAmount = mAmount;
    }

    public String getmName() {
        return mName;
    }

    public  void setmName(String mName) {
        this.mName = mName;
    }


    public Sheet() {}


}