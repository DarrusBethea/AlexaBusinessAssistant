package com.example.androidalexaskillproject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LineChartFragment extends Fragment {


    Spinner myspinner;
    LineChart lineChart;
    String _listselected = "";
    Date CurrentDate;
    Date Q1;
    Date Q2;
    Date Q3;
    Date Q4;

    int Num_Q1 = 0;
    int Num_Q2 = 0;
    int Num_Q3 = 0;
    int Num_Q4 = 0;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.linechart_fragment, container, false);
        lineChart  = v.findViewById(R.id.Line_chart_object);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);

        // Mayank 3/3/2020: creating spinner used to select which list data to use with the pie chart
        myspinner = (Spinner) v.findViewById(R.id.LineChart_spinner);



        // Mayank 3/3/2020: add more list here for pie chart
        String[] items = new String[]{
                "profits list", "expense list","employee list", "Inventory list"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        myspinner.setAdapter(adapter);

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                _listselected =  (String) parent.getItemAtPosition(position);

                if (_listselected  == "profits list"){

                    double Num_Q1 = 0;
                    double Num_Q2 = 0;
                    double Num_Q3 = 0;
                    double Num_Q4 = 0;

                    // Mayank 3/3/2020: setting up date ranges for profits sheet
                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Q1 = DateFormat.parse("2020-01-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q2 = DateFormat.parse("2020-04-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q3 = DateFormat.parse("2020-07-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q4 = DateFormat.parse("2020-10-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SheetRepository.getInstance().setSheetProfits();
                    // Mayank 3/32020: getting the profits data
                    // then add totals by monthing quater
                    try {
                        new ListInfo.GetData().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ListInfo  listinfo = ListInfo.get(getActivity());

                    List<Profits> profits = listinfo.getInfo();
                    for(int i = 0; i < profits.size(); i++){

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CurrentDate.after(Q1) && CurrentDate.before(Q2)){
                            Num_Q1 = Num_Q1 +  Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q2) && CurrentDate.before(Q3)){

                            Num_Q2 = Num_Q2 +  Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q3) && CurrentDate.before(Q4)){
                            Num_Q3 = Num_Q3 +  Double.parseDouble(profits.get(i).getmAmount());

                        }

                        else if (CurrentDate.after(Q4)){

                            Num_Q4 = Num_Q4 +  Double.parseDouble(profits.get(i).getmAmount());
                        }
                        else{

                        }



                    }

                    //Mayank 3.3.2020: creating description for Profits pie chart
                    Description desc = new Description();
                    desc.setText("Quarterly Year Profits");
                    desc.setTextSize(30f);
                    lineChart.setDescription(desc);

                    List<Entry> value = new ArrayList<>();

                    value.add(new Entry(1, (float) Num_Q1, "Q1"));
                    value.add(new Entry(2, (float) Num_Q2, "Q2"));
                    value.add(new Entry(3, (float) Num_Q3, "Q3"));
                    value.add(new Entry(4, (float) Num_Q4, "Q4"));

                    LineDataSet LinedataSet1 = new LineDataSet(value,"data set 1");
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(LinedataSet1);
                    LineData data = new LineData(dataSets);
                    lineChart.setData(data);
                    lineChart.invalidate();


                }
                //TODO Mayank 3/32020: add next list data here when it is created
                else if (_listselected == "expense list"){


                    double Num_Q1 = 0;
                    double Num_Q2 = 0;
                    double Num_Q3 = 0;
                    double Num_Q4 = 0;
                    lineChart.clear();
                    // Mayank 3/3/2020: setting up date ranges for profits sheet
                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Q1 = DateFormat.parse("2020-01-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q2 = DateFormat.parse("2020-04-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q3 = DateFormat.parse("2020-07-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q4 = DateFormat.parse("2020-10-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SheetRepository.getInstance().setSheetExpenses();
                    // Mayank 3/32020: getting the profits data
                    // then add totals by monthing quater
                    try {
                        new ListInfo.GetData().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ListInfo  listinfo = ListInfo.get(getActivity());

                    List<Profits> profits = listinfo.getInfo();

                    for(int i = 0; i < profits.size(); i++){


                        System.out.println( Double.parseDouble(profits.get(i).getmAmount()));

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();}

                        if (CurrentDate.after(Q1) && CurrentDate.before(Q2)){
                            Num_Q1 = Num_Q1 +  Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q2) && CurrentDate.before(Q3)){

                            Num_Q2 = Num_Q2 +  Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q3) && CurrentDate.before(Q4)){
                            Num_Q3 = Num_Q3 +  Double.parseDouble(profits.get(i).getmAmount());

                        }

                        else if (CurrentDate.after(Q4)){

                            Num_Q4 = Num_Q4 +  Double.parseDouble(profits.get(i).getmAmount());
                        }
                        else{

                        }



                    }

                    //Mayank 3.3.2020: creating description for Profits pie chart
                    Description desc1 = new Description();
                    desc1.setText("Quarterly Year Expenses");
                    desc1.setTextSize(30f);
                    lineChart.setDescription(desc1);

                    List<Entry> value = new ArrayList<>();

                    value.add(new Entry(1, (float) Num_Q1, "Q1"));
                    value.add(new Entry(2, (float) Num_Q2, "Q2"));
                    value.add(new Entry(3, (float) Num_Q3, "Q3"));
                    value.add(new Entry(4, (float) Num_Q4, "Q4"));

                    LineDataSet LinedataSet1 = new LineDataSet(value,"data set 1");
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(LinedataSet1);
                    LineData data = new LineData(dataSets);
                    lineChart.setData(data);
                    lineChart.invalidate();

           }


                // FAH 4/10/2020: add employee list
                else if (_listselected  == "employee list"){

                    double Num_Q1 = 0;
                    double Num_Q2 = 0;
                    double Num_Q3 = 0;
                    double Num_Q4 = 0;

                    // Mayank 3/3/2020: setting up date ranges for profits sheet
                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Q1 = DateFormat.parse("2020-01-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q2 = DateFormat.parse("2020-04-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q3 = DateFormat.parse("2020-07-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q4 = DateFormat.parse("2020-10-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SheetRepository.getInstance().setSheetEmployees();
                    // Mayank 3/32020: getting the profits data
                    // then add totals by monthing quater
                    try {
                        new ListInfo.GetData().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ListInfo  listinfo = ListInfo.get(getActivity());

                    List<Profits> profits = listinfo.getInfo();
                    for(int i = 0; i < profits.size(); i++){

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CurrentDate.after(Q1) && CurrentDate.before(Q2)){
                            Num_Q1 = Num_Q1 +  Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q2) && CurrentDate.before(Q3)){

                            Num_Q2 = Num_Q2 +  Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q3) && CurrentDate.before(Q4)){
                            Num_Q3 = Num_Q3 +  Double.parseDouble(profits.get(i).getmAmount());

                        }

                        else if (CurrentDate.after(Q4)){

                            Num_Q4 = Num_Q4 +  Double.parseDouble(profits.get(i).getmAmount());
                        }
                        else{

                        }



                    }

                    //Mayank 3.3.2020: creating description for Profits pie chart
                    Description desc = new Description();
                    desc.setText("Yearly Hours Worked");
                    desc.setTextSize(30f);
                    lineChart.setDescription(desc);

                    List<Entry> value = new ArrayList<>();

                    value.add(new Entry(1, (float) Num_Q1, "Q1"));
                    value.add(new Entry(2, (float) Num_Q2, "Q2"));
                    value.add(new Entry(3, (float) Num_Q3, "Q3"));
                    value.add(new Entry(4, (float) Num_Q4, "Q4"));

                    LineDataSet LinedataSet1 = new LineDataSet(value,"data set 1");
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(LinedataSet1);
                    LineData data = new LineData(dataSets);
                    lineChart.setData(data);
                    lineChart.invalidate();


                }

                //FAH2/10/2020: added incventory list
                else if (_listselected  == "Inventory list"){

                    double Num_Q1 = 0;
                    double Num_Q2 = 0;
                    double Num_Q3 = 0;
                    double Num_Q4 = 0;

                    // Mayank 3/3/2020: setting up date ranges for profits sheet
                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Q1 = DateFormat.parse("2020-01-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q2 = DateFormat.parse("2020-04-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q3 = DateFormat.parse("2020-07-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Q4 = DateFormat.parse("2020-10-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SheetRepository.getInstance().setSheetInventory();
                    // Mayank 3/32020: getting the profits data
                    // then add totals by monthing quater
                    try {
                        new ListInfo.GetData().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ListInfo  listinfo = ListInfo.get(getActivity());

                    List<Profits> profits = listinfo.getInfo();
                    for(int i = 0; i < profits.size(); i++){

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CurrentDate.after(Q1) && CurrentDate.before(Q2)){
                            Num_Q1 = Num_Q1 +  Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q2) && CurrentDate.before(Q3)){

                            Num_Q2 = Num_Q2 +  Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q3) && CurrentDate.before(Q4)){
                            Num_Q3 = Num_Q3 +  Double.parseDouble(profits.get(i).getmAmount());

                        }

                        else if (CurrentDate.after(Q4)){

                            Num_Q4 = Num_Q4 +  Double.parseDouble(profits.get(i).getmAmount());
                        }
                        else{

                        }



                    }

                    //Mayank 3.3.2020: creating description for Profits pie chart
                    Description desc = new Description();
                    desc.setText("Yearly Inventory Total Cost");
                    desc.setTextSize(30f);
                    lineChart.setDescription(desc);

                    List<Entry> value = new ArrayList<>();

                    value.add(new Entry(1, (float) Num_Q1, "Q1"));
                    value.add(new Entry(2, (float) Num_Q2, "Q2"));
                    value.add(new Entry(3, (float) Num_Q3, "Q3"));
                    value.add(new Entry(4, (float) Num_Q4, "Q4"));

                    LineDataSet LinedataSet1 = new LineDataSet(value,"data set 1");
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(LinedataSet1);
                    LineData data = new LineData(dataSets);
                    lineChart.setData(data);
                    lineChart.invalidate();


                }



                else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        return v;
    }



}
