package com.example.androidalexaskillproject;

import androidx.fragment.app.Fragment;

public class ScatterPlotReturnFragment extends SingleFragmentActivty  {



    @Override
    protected Fragment createFragment() {


        return new ScatterPlotData();
    }
}
