package com.example.androidalexaskillproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.logging.ConsoleHandler;

// FAH 2/16/202: this class is  mainly for the add profts fragment
public class ProfitsAddFragments extends Fragment {
    private Profits mProfits;
    private EditText mNameField;
    private EditText mAmountField;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfits = new Profits();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profits_add, container, false);
        // FAH 2/16/2020: if the text if change in the text box then it will be set here
        mNameField = v.findViewById(R.id.profit_name);
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProfits.setmName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mAmountField = v.findViewById(R.id.profit_amount);
        mAmountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProfits.setmAmount(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });
        // FAH 2/16/2020 if add button is press uses custom url to add what we want
        // code for url adding can be found in the google app script editor
        Button addbtn = v.findViewById(R.id.profit_sumbit_btn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentClick = new Intent(Intent.ACTION_VIEW, Uri.parse("https://script.google.com/macros/s/AKfycbyqn1fD46kgkDbscsaJ61pTG9ln9lKqE4pS9ZzaLCe2oVILr_Wg/exec?sheetname=profits&AddDelete=add&name="+mProfits.getmName() +"&profit="+mProfits.getmAmount()));

                startActivity(intentClick);
            }
        });
        // FAH 2/16/2020 if add button is press uses custom url to delete what we want
        Button delbtn = v.findViewById(R.id.delete_btn);
        delbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentClick = new Intent(Intent.ACTION_VIEW, Uri.parse("https://script.google.com/macros/s/AKfycbyqn1fD46kgkDbscsaJ61pTG9ln9lKqE4pS9ZzaLCe2oVILr_Wg/exec?sheetname=profits&AddDelete=delete&name="+mProfits.getmName() +"&profit="+mProfits.getmAmount()));

                startActivity(intentClick);
            }
        });

        return v;
    }


}
