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


// FAH 3/1/2020: edit class
public class ExpenseEditFragment extends Fragment {
    private Expenses mExpenses;
    private EditText mEditNameField;
    private EditText mEditAmountField;
    private EditText mEditLastNameField;
    private EditText mEditDateField;

    private String OldName;
    private String OldAmount;
    private String OldLastName;
    private String OldDate;

    private EditText mOldEditNameField;
    private EditText mOldEditAmountField;
    private EditText mOldEditLastNameField;
    private EditText mOldEditDateField;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExpenses = new Expenses();

        //FAH 3/1/2020: retriving the old data
        OldName = getActivity().getIntent().getStringExtra("edit_name");
        OldAmount = getActivity().getIntent().getStringExtra("edit_amount");
        OldLastName = getActivity().getIntent().getStringExtra("edit_type");
        OldDate = getActivity().getIntent().getStringExtra("edit_date");


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {





        View v = inflater.inflate(R.layout.expense_fragment_edit, container, false);


        //FAH 3/1/2020: getting the old data
        mOldEditNameField = v.findViewById(R.id.expense_edit_Name_old);
        mOldEditNameField.setEnabled(false);
        mOldEditNameField.setText(OldName);


        mOldEditLastNameField = v.findViewById(R.id.expense_type_edit_old);
        mOldEditLastNameField.setEnabled(false);
        mOldEditLastNameField.setText(OldLastName);

        mOldEditAmountField = v.findViewById(R.id.expense__amount_edit_old);
        mOldEditAmountField.setEnabled(false);
        mOldEditAmountField.setText(OldAmount);

        mOldEditDateField = v.findViewById(R.id.expense_date_edit_old);
        mOldEditDateField.setEnabled(false);
        mOldEditDateField.setText(OldDate);


        // FAH 2/16/2020: if the text if change in the text box then it will be set here
        mEditNameField = v.findViewById(R.id.expense_edit_Name);
        mEditNameField.addTextChangedListener(new TextWatcher() {
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


        mEditLastNameField = v.findViewById(R.id.expense_type_edit);
        mEditLastNameField.addTextChangedListener(new TextWatcher() {
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

        mEditAmountField = v.findViewById(R.id.expense_amount_edit);
        mEditAmountField.addTextChangedListener(new TextWatcher() {
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

        mEditDateField = v.findViewById(R.id.expense_date_edit);
        mEditDateField.addTextChangedListener(new TextWatcher() {
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
        Button addbtn = v.findViewById(R.id.expense_edit_btn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                try {
                    new ExpenseEditFragment.EditData().execute().get();
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




    public class EditData extends AsyncTask<String, Void, String> {

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
            Intent intentClick = new Intent(Intent.ACTION_VIEW, Uri.parse("https://script.google.com/macros/s/AKfycbwI9wvauddFIGMMUNrqqtb5aXXURM3_Jo468jxTsWGe/dev?sheetname=expenses&AddDelete=edit&Name="+
                    OldName.toString() +"&Type=" + OldLastName.toString() + "&expense=" + OldAmount.toString() +
                    "&Date=" + OldDate.toString() + "&EditName="+ mExpenses.getmName()+
                    "&EditType=" + mExpenses.getmType() +  "&EditExpense=" + mExpenses.getmAmount()+"&EditDate=" + mExpenses.getmDate()));
            startActivity(intentClick);

            return "";

        }

    }
}
