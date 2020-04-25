package com.example.androidalexaskillproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;

import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.Toast;

//  Darrus Bethea
public class LoginScreenFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login_screen, container, false);

        Button loginButton = v.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "login successful", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(getActivity(), HomeScreenReturnFragment.class);
                startActivity(loginIntent);
            }
        });

        return v;
    }


}
