package com.example.androidalexaskillproject;

import androidx.fragment.app.Fragment;

public class PieChartReturnFragment extends SingleFragmentActivty {


    @Override
    protected Fragment createFragment() {


        return new PieChartData();
    }

}
