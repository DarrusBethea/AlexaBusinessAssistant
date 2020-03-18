package com.example.androidalexaskillproject;

import android.os.AsyncTask;

import androidx.fragment.app.Fragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SingleFragmentActivty {



    @Override
    protected Fragment createFragment() {
        // returns new frgament when user clicks on an item from list view
        return new ProfitsDeleteFragment();
    }



}
