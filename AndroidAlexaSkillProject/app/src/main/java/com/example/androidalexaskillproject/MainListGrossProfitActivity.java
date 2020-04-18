package com.example.androidalexaskillproject;

import androidx.fragment.app.Fragment;

public class MainListGrossProfitActivity  extends SingleFragmentActivty {



    @Override
    protected Fragment createFragment() {


        return new GrossProfitMainList();
    }




}
