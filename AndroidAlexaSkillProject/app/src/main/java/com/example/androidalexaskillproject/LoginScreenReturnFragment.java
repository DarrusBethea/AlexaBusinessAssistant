package com.example.androidalexaskillproject;

import androidx.fragment.app.Fragment;

public class LoginScreenReturnFragment extends SingleFragmentActivty {

    @Override
    protected Fragment createFragment() {
        // returns new fragment when user clicks on an item from list view
        return new LoginScreenFragment();
    }
}