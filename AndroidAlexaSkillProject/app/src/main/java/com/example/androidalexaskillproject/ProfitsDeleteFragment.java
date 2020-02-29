package com.example.androidalexaskillproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class ProfitsDeleteFragment extends Fragment {
    private String DeleteName;
    private String DeleteAmount;
    private String DeleteLastName;
    private String DeleteDate;
    private Profits mProfits;
    private EditText mNameField;
    private EditText mAmountField;
    private EditText mLastNameField;
    private EditText mDatetField;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfits = new Profits();
        // FAH2/24/2020: these intents are coming from mainlistfragment,
        //bassically it will auto fill when a user clicks on something from the list to delete

        DeleteName = getActivity().getIntent().getStringExtra("delete_name");
        DeleteLastName = getActivity().getIntent().getStringExtra("delete_last_name");
        DeleteAmount = getActivity().getIntent().getStringExtra("delete_amount");
        DeleteDate = getActivity().getIntent().getStringExtra("delete_date");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_delete_profits, container, false);

        mNameField = v.findViewById(R.id.profit_name_delete);
        mNameField.setEnabled(false);
        mNameField.setText(DeleteName);
        mProfits.setmName(DeleteName);


        mLastNameField = v.findViewById(R.id.profit_lastname_delete);
        mLastNameField.setEnabled(false);
        mLastNameField.setText(DeleteLastName);
        mProfits.setmLastname(DeleteLastName);

        mAmountField = v.findViewById(R.id.profit_amount_delete);
        mAmountField.setEnabled(false);
        mAmountField.setText(DeleteAmount);
        mProfits.setmAmount(DeleteAmount);


        mDatetField = v.findViewById(R.id.profit_date_delete);
        mDatetField.setEnabled(false);
        mDatetField.setText(DeleteDate);
        mProfits.setmDate(DeleteDate);


        // FAH 2/16/2020 if add button is press uses custom url to delete what we want
        Button delbtn = v.findViewById(R.id.delete_btn);
        delbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentClick = new Intent(Intent.ACTION_VIEW, Uri.parse("https://script.google.com/macros/s/AKfycbyqn1fD46kgkDbscsaJ61pTG9ln9lKqE4pS9ZzaLCe2oVILr_Wg/exec?sheetname=profits&AddDelete=delete&Firstname=" + mProfits.getmName() + "&LastName=" + mProfits.getmLastname()
                        +"&profit=" + mProfits.getmAmount() +"&Date=" + mProfits.getmDate()));


                startActivity(intentClick);
            }
        });

        return v;
    }


}
