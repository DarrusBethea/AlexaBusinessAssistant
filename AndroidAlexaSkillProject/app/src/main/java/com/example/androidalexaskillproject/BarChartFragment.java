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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


// Mayank 4/19/2020: BarChartFragment class stores all the Bar chart data

public class BarChartFragment extends Fragment {
    Spinner myspinner;
    BarChart barChart;
    String _listselected = "";
    Date CurrentDate;
    Date Q1;
    Date Q2;
    Date Q3;
    Date Q4;

    double Num_Q1 = 0;
    double Num_Q2 = 0;
    double Num_Q3 = 0;
    double Num_Q4 = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.barchart_fragment, container, false);
        barChart  = v.findViewById(R.id.bar_chart_object);

        //Mayank 3/2/2020: setting grid and layout for barchart
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);

        // Mayank 3/3/2020: creating spinner used to select which list data to use with the pie chart
        myspinner = (Spinner) v.findViewById(R.id.Barchart_spinner);



        // Mayank 3/3/2020: add more list here for pie chart
        // FAH 4/10/2020: add employee list and inventory list.
        String[] items = new String[]{
                "profits list", "expense list", "employee list", "Inventory list"
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
                    SheetRepository.getInstance().setSheetProfits();

                    Num_Q1 = 0;
                    Num_Q2 = 0;
                    Num_Q3 = 0;
                    Num_Q4 = 0;

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
                            Num_Q1 = Num_Q1 + Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q2) && CurrentDate.before(Q3)){

                            Num_Q2 = Num_Q2 + Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q3) && CurrentDate.before(Q4)){
                            Num_Q3 = Num_Q3 + Double.parseDouble(profits.get(i).getmAmount());

                        }

                        else if (CurrentDate.after(Q4)){

                            Num_Q4 = Num_Q4 + Double.parseDouble(profits.get(i).getmAmount());
                        }
                        else{

                        }



                    }

                    //Mayank 3.3.2020: creating description for Profits pie chart
                    Description desc = new Description();
                    desc.setText("Quarterly Year Profits");
                    desc.setTextSize(10f);
                    barChart.setDescription(desc);

                    // Mayank 3/2/2020: adding quater values to list
                    ArrayList<BarEntry> value = new ArrayList<BarEntry>();
                   value.add(new BarEntry(0, (float) Num_Q1,"Q1"));
                    value.add(new BarEntry(1, (float) Num_Q2, "Q2"));
                    value.add(new BarEntry(2, (float) Num_Q3, "Q3"));
                    value.add(new BarEntry(3, (float) Num_Q4, "Q4"));

                    //Mayank 3/2/2020: setting data values
                    BarDataSet _bardataset = new BarDataSet(value, "Quarterly Year Profits" );
                    _bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
                    _bardataset.setValueTextSize(10f);

                    BarData _barData = new BarData(_bardataset);
                    barChart.setData(_barData);



                    // Mayank3/2/2020: setting Xaxis values
                    String[] months = new String[] {"Q1", "Q2", "Q3", "Q4"};
                    XAxis  xaxis = barChart.getXAxis();
                    xaxis.setValueFormatter(new IndexAxisValueFormatter(months));
                    xaxis.setCenterAxisLabels(false);
                     xaxis.setPosition(XAxis.XAxisPosition.TOP);
                     xaxis.setGranularity(1);
                     xaxis.setGranularityEnabled(true);


                    barChart.animateXY( 1400,1400);

                }
                //TODO Mayank 3/32020: add next list data here when it is created
                else if (_listselected == "expense list"){
                    SheetRepository.getInstance().setSheetExpenses();
                     Num_Q1 = 0;
                     Num_Q2 = 0;
                     Num_Q3 = 0;
                     Num_Q4 = 0;

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

                    for (int i = 0; i < profits.size(); i++){

                        System.out.println(profits.get(i));
                    }
                    for(int i = 0; i < profits.size(); i++){

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CurrentDate.after(Q1) && CurrentDate.before(Q2)){
                            Num_Q1 = Num_Q1 + Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q2) && CurrentDate.before(Q3)){

                            Num_Q2 = Num_Q2 + Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q3) && CurrentDate.before(Q4)){
                            Num_Q3 = Num_Q3 + Double.parseDouble(profits.get(i).getmAmount());

                        }

                        else if (CurrentDate.after(Q4)){

                            Num_Q4 = Num_Q4 + Double.parseDouble(profits.get(i).getmAmount());
                        }
                        else{

                        }



                    }

                    //Mayank 3.3.2020: creating description for Profits pie chart
                    Description desc = new Description();
                    desc.setText("Quarterly Year Expenses");
                    desc.setTextSize(10f);
                    barChart.setDescription(desc);

                    // Mayank 3/2/2020: adding quater values to list

                    ArrayList<BarEntry> value = new ArrayList<BarEntry>();
                    value.add(new BarEntry(0, (float) Num_Q1,"Q1"));
                    value.add(new BarEntry(1, (float) Num_Q2, "Q2"));
                    value.add(new BarEntry(2, (float) Num_Q3, "Q3"));
                    value.add(new BarEntry(3, (float) Num_Q4, "Q4"));

                    //Mayank 3/2/2020: setting data values
                    BarDataSet _bardataset = new BarDataSet(value, "Quarterly Year Expenses" );
                    _bardataset.setColors(ColorTemplate.PASTEL_COLORS);
                    _bardataset.setValueTextSize(20f);

                    BarData _barData = new BarData(_bardataset);
                    barChart.setData(_barData);



                    // Mayank3/2/2020: setting Xaxis values
                    String[] months = new String[] {"Q1", "Q2", "Q3", "Q4"};
                    XAxis  xaxis = barChart.getXAxis();


                    xaxis.setValueFormatter(new IndexAxisValueFormatter(months));
                    xaxis.setCenterAxisLabels(true);
                    xaxis.setPosition(XAxis.XAxisPosition.TOP);
                    xaxis.setGranularity(1);
                    xaxis.setGranularityEnabled(true);


                    barChart.animateXY( 1400,1400);

                }

                // FAH 4/10/2020: add employee list
                else if (_listselected == "employee list"){
                    SheetRepository.getInstance().setSheetEmployees();
                    Num_Q1 = 0;
                    Num_Q2 = 0;
                    Num_Q3 = 0;
                    Num_Q4 = 0;


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



                    try {
                        new ListInfo.GetData().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ListInfo  listinfo = ListInfo.get(getActivity());

                    List<Profits> profits = listinfo.getInfo();

                    for (int i = 0; i < profits.size(); i++){

                        System.out.println(profits.get(i));
                    }
                    for(int i = 0; i < profits.size(); i++){

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CurrentDate.after(Q1) && CurrentDate.before(Q2)){
                            Num_Q1 = Num_Q1 + Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q2) && CurrentDate.before(Q3)){

                            Num_Q2 = Num_Q2 + Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q3) && CurrentDate.before(Q4)){
                            Num_Q3 = Num_Q3 + Double.parseDouble(profits.get(i).getmAmount());

                        }

                        else if (CurrentDate.after(Q4)){

                            Num_Q4 = Num_Q4 + Double.parseDouble(profits.get(i).getmAmount());
                        }
                        else{

                        }



                    }

                    Description desc = new Description();
                    desc.setText("Yearly Hours Worked");
                    desc.setTextSize(10f);
                    barChart.setDescription(desc);


                    ArrayList<BarEntry> value = new ArrayList<BarEntry>();
                    value.add(new BarEntry(0, (float) Num_Q1,"Q1"));
                    value.add(new BarEntry(1, (float) Num_Q2, "Q2"));
                    value.add(new BarEntry(2, (float) Num_Q3, "Q3"));
                    value.add(new BarEntry(3, (float) Num_Q4, "Q4"));

                    BarDataSet _bardataset = new BarDataSet(value, "Quarterly Hours" );
                    _bardataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
                    _bardataset.setValueTextSize(20f);

                    BarData _barData = new BarData(_bardataset);
                    barChart.setData(_barData);



                    String[] months = new String[] {"Q1", "Q2", "Q3", "Q4"};
                    XAxis  xaxis = barChart.getXAxis();


                    xaxis.setValueFormatter(new IndexAxisValueFormatter(months));
                    xaxis.setCenterAxisLabels(true);
                    xaxis.setPosition(XAxis.XAxisPosition.TOP);
                    xaxis.setGranularity(1);
                    xaxis.setGranularityEnabled(true);


                    barChart.animateXY( 1400,1400);

                }


                //FAH2/10/2020: added Inventory list
                else if (_listselected == "Inventory list"){
                    SheetRepository.getInstance().setSheetInventory();
                    Num_Q1 = 0;
                    Num_Q2 = 0;
                    Num_Q3 = 0;
                    Num_Q4 = 0;


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




                    try {
                        new ListInfo.GetData().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ListInfo  listinfo = ListInfo.get(getActivity());

                    List<Profits> profits = listinfo.getInfo();

                    for (int i = 0; i < profits.size(); i++){

                        System.out.println(profits.get(i));
                    }
                    for(int i = 0; i < profits.size(); i++){

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CurrentDate.after(Q1) && CurrentDate.before(Q2)){
                            Num_Q1 = Num_Q1 + Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q2) && CurrentDate.before(Q3)){

                            Num_Q2 = Num_Q2 + Double.parseDouble(profits.get(i).getmAmount());
                        }

                        else if (CurrentDate.after(Q3) && CurrentDate.before(Q4)){
                            Num_Q3 = Num_Q3 + Double.parseDouble(profits.get(i).getmAmount());

                        }

                        else if (CurrentDate.after(Q4)){

                            Num_Q4 = Num_Q4 + Double.parseDouble(profits.get(i).getmAmount());
                        }
                        else{

                        }



                    }


                    Description desc = new Description();
                    desc.setText("Yearly Inventory Total Cost");
                    desc.setTextSize(10f);
                    barChart.setDescription(desc);

                    ArrayList<BarEntry> value = new ArrayList<BarEntry>();
                    value.add(new BarEntry(0, (float) Num_Q1,"Q1"));
                    value.add(new BarEntry(1, (float) Num_Q2, "Q2"));
                    value.add(new BarEntry(2, (float) Num_Q3, "Q3"));
                    value.add(new BarEntry(3, (float) Num_Q4, "Q4"));

                    BarDataSet _bardataset = new BarDataSet(value, "Quarterly TotalCost" );
                    _bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    _bardataset.setValueTextSize(20f);

                    BarData _barData = new BarData(_bardataset);
                    barChart.setData(_barData);

                    String[] months = new String[] {"Q1", "Q2", "Q3", "Q4"};
                    XAxis  xaxis = barChart.getXAxis();


                    xaxis.setValueFormatter(new IndexAxisValueFormatter(months));
                    xaxis.setCenterAxisLabels(true);
                    xaxis.setPosition(XAxis.XAxisPosition.TOP);
                    xaxis.setGranularity(1);
                    xaxis.setGranularityEnabled(true);


                    barChart.animateXY( 1400,1400);

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