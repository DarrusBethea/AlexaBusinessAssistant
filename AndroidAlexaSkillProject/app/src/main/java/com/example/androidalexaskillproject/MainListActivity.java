package com.example.androidalexaskillproject;

import androidx.fragment.app.Fragment;

public class MainListActivity extends SingleFragmentActivity {


    //   finish();
    //  overridePendingTransition(0, 0);



    @Override
    protected Fragment createFragment() {


        return new MainListFragment();
    }




}
