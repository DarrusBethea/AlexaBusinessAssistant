package com.example.androidalexaskillproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GrossProfitMainList extends Fragment {

    // FAH 4/17/2020 : this class is to organize all the data in the list view for the gross profit list.
    private RecyclerView mProfitRecyclerView;

    private MainAdapter mAdapter;

    Spinner  GrossProfitspinner;

    String _listselected = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.grossprofit_mainlist_fragment, container, false);

        mProfitRecyclerView = view.findViewById(R.id.grossprofits_recycler_view);
        mProfitRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        String[] items = new String[]{
                "Yearly Profits", "Yearly Expenses", "Yearly Total"
        };
        GrossProfitspinner = (Spinner) view.findViewById(R.id.grossprofit_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter< String>(this.getActivity(),
                android.R.layout.simple_spinner_item, items);
        GrossProfitspinner.setAdapter(adapter);


        GrossProfitspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                _listselected =  (String) parent.getItemAtPosition(position);

                // FAH 4/17/2020: setting the spinner to one grabs the info needed
                // to the yearly profits, collumn.
                if (_listselected  == "Yearly Profits"){

                    GrossProfit.setSpinnerChange(1);
                    updateUI();
                }



                else if (_listselected == "Yearly Expenses"){

                    GrossProfit.setSpinnerChange(2);
                    updateUI();

                }

                else if (_listselected == "Yearly Total"){

                    GrossProfit.setSpinnerChange(3);
                    updateUI();

                }

                else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        return view;
    }



    private void updateUI() {

        try {
            new GrossProfitsListInfo.GetData().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GrossProfitsListInfo  grossprofitslistInfo = GrossProfitsListInfo.get(getActivity());


        List<GrossProfit> grossprofit = grossprofitslistInfo.getInfo();
        for(int i = 0; i < grossprofit.size(); i++){
            System.out.println(grossprofit.get(i).getmAmountGrossProfit());

        }
        mAdapter = new MainAdapter(grossprofit);
        mProfitRecyclerView.setAdapter(mAdapter);
    }

    private class Viewholder extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        private TextView MValueGrossProfits;

        private TextView MMonthGrossProfits;

        private GrossProfit mGrossProfit;

        public Viewholder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_gross_profits_list, parent, false));
            itemView.setOnClickListener(this);
            MValueGrossProfits = itemView.findViewById(R.id.grossprofit_amount);
            MMonthGrossProfits = itemView.findViewById(R.id.grossprofit_Month);


        }

        public void bind(GrossProfit grossprofit) {
            mGrossProfit = grossprofit;
            MValueGrossProfits.setText(mGrossProfit.getmAmountGrossProfit());
            MMonthGrossProfits.setText(mGrossProfit.getMonth());

        }

        @Override
        public void onClick(View v) {

        }
    }

    private class MainAdapter extends RecyclerView.Adapter<Viewholder> {
        private List<GrossProfit> mGrossProfit;



        public MainAdapter(List<GrossProfit> _grossprofits) {
            mGrossProfit = _grossprofits;
        }




        @Override
        public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new Viewholder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(Viewholder holder, int position) {
            final GrossProfit grossprofit = mGrossProfit.get(position);
            holder.bind(grossprofit);



        }

        @Override
        public int getItemCount() {
            return mGrossProfit.size();
        }
    }

}
