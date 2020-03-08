package com.example.androidalexaskillproject;

import androidx.fragment.app.Fragment;

public class ExpenseListActivity extends SingleFragmentActivty {


    //   finish();
    //  overridePendingTransition(0, 0);



    @Override
    protected Fragment createFragment() {


        return new ExpenseListFragment();
    }




}
