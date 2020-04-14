package com.example.androidalexaskillproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutionException;


public class LoginScreenFragment extends Fragment {

    // user taps 'login'
    // toast with confirmation
    // return to home_screen.xml

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/*
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){

                case R.id.loginButton:
                    Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show();
                    //Intent loginIntent = new Intent(this, HomeScreenReturnFragment.class);
                    //startActivity(loginIntent);

                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }

        }
*/
        View v = inflater.inflate(R.layout.login_screen, container, false);

        Button loginButton = v.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.home_screen);
            }
        });

        return v;

    }
}


