package com.example.androidalexaskillproject;

import androidx.fragment.app.Fragment;

public class BarChartReturnFragment extends SingleFragmentActivty {


    @Override
    protected Fragment createFragment() {


        return new BarChartFragment();
    }

}