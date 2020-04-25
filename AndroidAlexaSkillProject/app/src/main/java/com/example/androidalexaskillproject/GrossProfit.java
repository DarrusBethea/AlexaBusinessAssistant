package com.example.androidalexaskillproject;

public class GrossProfit {
// FAH 4/17/2020: this is the gross profits class getter and setters,
    // used for all the variables the gross profits sheet would need

    private String mAmountGrossProfit;

    public static int getSpinnerChange() {
        return SpinnerChange;
    }

    public static void setSpinnerChange(int spinnerChange) {
        SpinnerChange = spinnerChange;
    }

    private static int SpinnerChange = 1;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    private  String month;


    public String getmAmountGrossProfit() {
        return mAmountGrossProfit;
    }

    public void setmAmountGrossProfit(String mAmount) {
        this.mAmountGrossProfit = mAmount + " $";
    }


    public GrossProfit()
    {
    }
}
