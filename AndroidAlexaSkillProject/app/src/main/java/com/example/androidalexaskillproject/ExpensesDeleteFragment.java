package com.example.androidalexaskillproject;

import android.content.Intent;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class ExpensesDeleteFragment extends Fragment {
    private String DeleteName;
    private String DeleteAmount;
    private String DeleteType;
    private String DeleteDate;
    private Expenses mExpenses;
    private EditText mNameField;
    private EditText mAmountField;
    private EditText mTypeField;
    private EditText mDatetField;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExpenses = new Expenses();
        // FAH2/24/2020: these intents are coming from mainlistfragment,
        //bassically it will auto fill when a user clicks on something from the list to delete

        DeleteName = getActivity().getIntent().getStringExtra("delete_name");
        DeleteType = getActivity().getIntent().getStringExtra("delete_type");
        DeleteAmount = getActivity().getIntent().getStringExtra("delete_amount");
        DeleteDate = getActivity().getIntent().getStringExtra("delete_date");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.expense_fragment_delete, container, false);

        mNameField = v.findViewById(R.id.expense_name_delete);
        mNameField.setEnabled(false);
        mNameField.setText(DeleteName);
        mExpenses.setmName(DeleteName);


        mTypeField = v.findViewById(R.id.expense_type_delete);
        mTypeField.setEnabled(false);
        mTypeField.setText(DeleteType);
        mExpenses.setmType(DeleteType);

        mAmountField = v.findViewById(R.id.expense_amount_delete);
        mAmountField.setEnabled(false);
        mAmountField.setText(DeleteAmount);
        mExpenses.setmAmount(DeleteAmount);


        mDatetField = v.findViewById(R.id.expense_date_delete);
        mDatetField.setEnabled(false);
        mDatetField.setText(DeleteDate);
        mExpenses.setmDate(DeleteDate);


        // FAH 2/16/2020 if add button is press uses custom url to delete what we want
        Button delbtn = v.findViewById(R.id.delete_btn);
        delbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    new ExpensesDeleteFragment.DeleteData().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });




        return v;
    }

    // FAH2/29/2020: done with fragment then return back to list
    public void ReturnList(){
        Intent GoBackintent = new Intent(getActivity(), MainListActivity.class);
        startActivity(GoBackintent);

    };




    public  class DeleteData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            String result = "";
            HttpResponseCache cache = HttpResponseCache.getInstalled();
            if (cache != null){
                cache.flush();

            }

            // FAH 2/29/2020:  Timer so we can update the sheet and then list will be able to read newly updated sheet
            new Timer().schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    ReturnList();

                }
            }, 4000 );
            Intent intentClick = new Intent(Intent.ACTION_VIEW, Uri.parse("https://script.google.com/macros/s/AKfycbyqn1fD46kgkDbscsaJ61pTG9ln9lKqE4pS9ZzaLCe2oVILr_Wg/exec?sheetname=expenses&AddDelete=delete&Name=" + mExpenses.getmName() + "&Type=" + mExpenses.getmType()
                    +"&expense=" + mExpenses.getmAmount() +"&Date=" + mExpenses.getmDate()));
            startActivity(intentClick);

            return "";

        }


    }


}
