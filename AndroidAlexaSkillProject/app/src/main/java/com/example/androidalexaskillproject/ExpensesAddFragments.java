package com.example.androidalexaskillproject;

import android.content.Intent;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import androidx.fragment.app.Fragment;

// FAH 2/16/202: this class is  mainly for the add profts fragment
public class ExpensesAddFragments extends Fragment {
    private Expenses mExpenses;
    private EditText mNameField;
    private EditText mAmountField;
    private EditText mTypeField;
    private EditText mDateField;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExpenses = new Expenses();


    }


    // TODO FAH 2/24/2020: this fragment will open where the add button clicks click still need to create add button to the toolbar
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.expense_fragment_add, container, false);
        // FAH 2/16/2020: if the text if change in the text box then it will be set here
        mNameField = v.findViewById(R.id.expense_add_Name);
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mExpenses.setmName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });


        mTypeField = v.findViewById(R.id.expense_type_add);
        mTypeField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mExpenses.setmType(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mAmountField = v.findViewById(R.id.expense_amount_add);
        mAmountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mExpenses.setmAmount(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mDateField = v.findViewById(R.id.expense_date_add);
        mDateField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mExpenses.setmDate(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        // FAH 2/16/2020 if add button is press uses custom url to add what we want
        // code for url adding can be found in the google app script editor
        Button addbtn = v.findViewById(R.id.expense_add_btn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                try {
                    new ExpensesAddFragments.AddeData().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });


        return v;
    }

    public void ReturnList() {
        Intent GoBackintent = new Intent(getActivity(), MainListActivity.class);
        startActivity(GoBackintent);

    }




    public class AddeData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            String result = "";
            HttpResponseCache cache = HttpResponseCache.getInstalled();
            if (cache != null) {
                cache.flush();

            }

            // FAH 2/29/2020:  Timer so we can update the sheet and then list will be able to read newly updated sheet
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    ReturnList();

                }
            }, 4000);
            Intent intentClick = new Intent(Intent.ACTION_VIEW, Uri.parse("https://script.google.com/macros/s/AKfycbyqn1fD46kgkDbscsaJ61pTG9ln9lKqE4pS9ZzaLCe2oVILr_Wg/exec?sheetname=expenses&AddDelete=add&Name=" + mExpenses.getmName() + "&Type=" + mExpenses.getmType()
                    + "&expense=" + mExpenses.getmAmount() + "&Date=" + mExpenses.getmDate()));
            startActivity(intentClick);

            return "";

        }

    }
}
