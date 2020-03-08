package com.example.androidalexaskillproject;

import androidx.fragment.app.Fragment;

public class ExpenseReturnEditFragment extends SingleFragmentActivty {


    @Override
    protected Fragment createFragment() {
        // returns new frgament when user clicks on an item from list view
        return new ExpenseEditFragment();
    }
}