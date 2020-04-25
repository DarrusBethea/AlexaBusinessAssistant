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
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.ConsoleHandler;

public class ScatterPlotData extends Fragment{

    Spinner myspinner;
    ScatterChart _ScatterChart;
    String _listselected = "";
    Date CurrentDate;
    Date _Date01;
    Date _Date02;
    Date _Date03;
    Date _Date04;
    Date _Date05;
    Date _Date06;
    Date _Date07;
    Date _Date08;
    Date _Date09;
    Date _Date10;
    Date _Date11;
    Date _Date12;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.scatterplot_fragment, container, false);
        _ScatterChart  = v.findViewById(R.id.Scatter_Plot_object);
        _ScatterChart.setTouchEnabled(true);
        _ScatterChart.setPinchZoom(true);

        // Mayank 3/3/2020: creating spinner used to select which list data to use with the pie chart
        myspinner = (Spinner) v.findViewById(R.id.ScatterPlot_spinner);



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
                _listselected = (String) parent.getItemAtPosition(position);

                List<Entry> value = new ArrayList<>();
                double _date01Avg = 0;
                int _date01Counter = 0;
                double _date02Avg = 0;
                int _date02Counter = 0;
                double _date03Avg = 0;
                int _date03Counter = 0;
                double _date04Avg = 0;
                int _date04Counter = 0;
                double _date05Avg = 0;
                int _date05Counter = 0;
                double _date06Avg = 0;
                int _date06Counter = 0;
                double _date07Avg = 0;
                int _date07Counter = 0;
                double _date08Avg = 0;
                int _date08Counter = 0;
                double _date09Avg = 0;
                int _date09Counter = 0;
                double _date10Avg = 0;
                int _date10Counter = 0;
                double _date11Avg = 0;
                int _date11Counter = 0;
                double _date12Avg = 0;
                int _date12Counter = 0;


                // Mayank 3/3/2020: setting up date ranges for profits sheet
                SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    _Date01 = DateFormat.parse("2020-01-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    _Date02 = DateFormat.parse("2020-02-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    _Date03 = DateFormat.parse("2020-03-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    _Date04 = DateFormat.parse("2020-04-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    _Date05 = DateFormat.parse("2020-05-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    _Date06 = DateFormat.parse("2020-06-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    _Date07 = DateFormat.parse("2020-07-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    _Date08 = DateFormat.parse("2020-08-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    _Date09 = DateFormat.parse("2020-09-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    _Date10 = DateFormat.parse("2020-10-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    _Date11 = DateFormat.parse("2020-11-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    _Date12 = DateFormat.parse("2020-12-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (_listselected == "profits list") {

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
                    ListInfo listinfo = ListInfo.get(getActivity());

                    List<Profits> profits = listinfo.getInfo();
                    for (int i = 0; i < profits.size(); i++) {

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CurrentDate.after(_Date01) && CurrentDate.before(_Date02)) {
                            _date01Avg = _date01Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date01Counter++;

                        } else if (CurrentDate.after(_Date02) && CurrentDate.before(_Date03)) {

                            _date02Avg = _date02Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date02Counter++;

                        }
                        else if (CurrentDate.after(_Date03) && CurrentDate.before(_Date04)) {
                            _date03Avg = _date03Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date03Counter++;

                        }

                        else if (CurrentDate.after(_Date04) && CurrentDate.before(_Date05)) {
                            _date04Avg = _date04Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date04Counter++;

                        }

                        else if (CurrentDate.after(_Date05) && CurrentDate.before(_Date06)) {
                            _date05Avg = _date05Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date05Counter++;

                        }

                        else if (CurrentDate.after(_Date06) && CurrentDate.before(_Date07)) {
                            _date06Avg = _date06Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date06Counter++;

                        }

                        else if (CurrentDate.after(_Date07) && CurrentDate.before(_Date08)) {
                            _date07Avg = _date07Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date07Counter++;

                        }

                        else if (CurrentDate.after(_Date08) && CurrentDate.before(_Date09)) {
                            _date08Avg = _date08Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date08Counter++;

                        }

                        else if (CurrentDate.after(_Date09) && CurrentDate.before(_Date10)) {
                            _date09Avg = _date09Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date09Counter++;

                        }

                        else if (CurrentDate.after(_Date10) && CurrentDate.before(_Date11)) {
                            _date10Avg = _date10Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date10Counter++;

                        }

                        else if (CurrentDate.after(_Date11) && CurrentDate.before(_Date12)) {
                            _date11Avg = _date11Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date11Counter++;

                        }
                        else if (CurrentDate.after(_Date12)) {
                            _date12Avg = _date12Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date12Counter++;

                        }

                        else {

                        }


                    }


                    _date01Avg = _date01Avg / _date01Counter;
                    _date02Avg = _date02Avg / _date02Counter;
                    _date03Avg = _date03Avg / _date03Counter;
                    _date04Avg = _date04Avg / _date04Counter;
                    _date05Avg = _date05Avg / _date05Counter;
                    _date06Avg = _date06Avg / _date06Counter;
                    _date07Avg = _date07Avg / _date07Counter;
                    _date08Avg = _date08Avg / _date08Counter;
                    _date09Avg = _date09Avg / _date09Counter;
                    _date10Avg = _date10Avg / _date10Counter;
                    _date11Avg = _date11Avg / _date11Counter;
                    _date12Avg = _date12Avg / _date12Counter;
                    //Mayank 3.3.2020: creating description for Profits pie chart
                    Description desc = new Description();
                    desc.setText("Avg Yearly Profits");
                    desc.setTextSize(30f);
                    _ScatterChart.setDescription(desc);



                    value.add(new Entry(1, (float) _date01Avg));
                    value.add(new Entry(2, (float) _date02Avg));
                    value.add(new Entry(3, (float) _date03Avg));
                    value.add(new Entry(4, (float) _date04Avg));
                    value.add(new Entry(5, (float) _date05Avg));
                    value.add(new Entry(6, (float) _date06Avg));
                    value.add(new Entry(7, (float) _date07Avg));
                    value.add(new Entry(8, (float) _date08Avg));
                    value.add(new Entry(9, (float) _date09Avg));
                    value.add(new Entry(10, (float) _date10Avg));
                    value.add(new Entry(11, (float) _date11Avg));
                    value.add(new Entry(12, (float) _date12Avg));



                    ScatterDataSet LinedataSet1 = new ScatterDataSet(value, "data set 1");
                    ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
                    dataSets.add(LinedataSet1);
                    ScatterData data = new ScatterData(dataSets);
                    _ScatterChart.setData(data);
                    _ScatterChart.invalidate();


                }
                else  if (_listselected == "expense list") {

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
                    ListInfo listinfo = ListInfo.get(getActivity());

                    List<Profits> profits = listinfo.getInfo();
                    for (int i = 0; i < profits.size(); i++) {

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CurrentDate.after(_Date01) && CurrentDate.before(_Date02)) {
                            _date01Avg = _date01Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date01Counter++;

                        } else if (CurrentDate.after(_Date02) && CurrentDate.before(_Date03)) {

                            _date02Avg = _date02Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date02Counter++;

                        }
                        else if (CurrentDate.after(_Date03) && CurrentDate.before(_Date04)) {
                            _date03Avg = _date03Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date03Counter++;

                        }

                        else if (CurrentDate.after(_Date04) && CurrentDate.before(_Date05)) {
                            _date04Avg = _date04Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date04Counter++;

                        }

                        else if (CurrentDate.after(_Date05) && CurrentDate.before(_Date06)) {
                            _date05Avg = _date05Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date05Counter++;

                        }

                        else if (CurrentDate.after(_Date06) && CurrentDate.before(_Date07)) {
                            _date06Avg = _date06Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date06Counter++;

                        }

                        else if (CurrentDate.after(_Date07) && CurrentDate.before(_Date08)) {
                            _date07Avg = _date07Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date07Counter++;

                        }

                        else if (CurrentDate.after(_Date08) && CurrentDate.before(_Date09)) {
                            _date08Avg = _date08Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date08Counter++;

                        }

                        else if (CurrentDate.after(_Date09) && CurrentDate.before(_Date10)) {
                            _date09Avg = _date09Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date09Counter++;

                        }

                        else if (CurrentDate.after(_Date10) && CurrentDate.before(_Date11)) {
                            _date10Avg = _date10Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date10Counter++;

                        }

                        else if (CurrentDate.after(_Date11) && CurrentDate.before(_Date12)) {
                            _date11Avg = _date11Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date11Counter++;

                        }
                        else if (CurrentDate.after(_Date12)) {
                            _date12Avg = _date12Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date12Counter++;


                        }

                        else {

                        }


                    }


                    _date01Avg = _date01Avg / _date01Counter;
                    _date02Avg = _date02Avg / _date02Counter;
                    _date03Avg = _date03Avg / _date03Counter;
                    _date04Avg = _date04Avg / _date04Counter;
                    _date05Avg = _date05Avg / _date05Counter;
                    _date06Avg = _date06Avg / _date06Counter;
                    _date07Avg = _date07Avg / _date07Counter;
                    _date08Avg = _date08Avg / _date08Counter;
                    _date09Avg = _date09Avg / _date09Counter;
                    _date10Avg = _date10Avg / _date10Counter;
                    _date11Avg = _date11Avg / _date11Counter;
                    _date12Avg = _date12Avg / _date12Counter;
                    //Mayank 3.3.2020: creating description for Profits pie chart
                    Description desc = new Description();
                    desc.setText("Avg Yearly Expenses");
                    desc.setTextSize(30f);
                    _ScatterChart.setDescription(desc);



                    value.add(new Entry(1, (float) _date01Avg));
                    value.add(new Entry(2, (float) _date02Avg));
                    value.add(new Entry(3, (float) _date03Avg));
                    value.add(new Entry(4, (float) _date04Avg));
                    value.add(new Entry(5, (float) _date05Avg));
                    value.add(new Entry(6, (float) _date06Avg));
                    value.add(new Entry(7, (float) _date07Avg));
                    value.add(new Entry(8, (float) _date08Avg));
                    value.add(new Entry(9, (float) _date09Avg));
                    value.add(new Entry(10, (float) _date10Avg));
                    value.add(new Entry(11, (float) _date11Avg));
                    value.add(new Entry(12, (float) _date12Avg));



                    ScatterDataSet LinedataSet1 = new ScatterDataSet(value, "data set 1");
                    ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
                    dataSets.add(LinedataSet1);
                    ScatterData data = new ScatterData(dataSets);
                    _ScatterChart.setData(data);
                    _ScatterChart.invalidate();

                }

                else  if (_listselected == "employee list"){
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
                    ListInfo listinfo = ListInfo.get(getActivity());

                    List<Profits> profits = listinfo.getInfo();
                    for (int i = 0; i < profits.size(); i++) {

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CurrentDate.after(_Date01) && CurrentDate.before(_Date02)) {
                            _date01Avg = _date01Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date01Counter++;

                        } else if (CurrentDate.after(_Date02) && CurrentDate.before(_Date03)) {

                            _date02Avg = _date02Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date02Counter++;

                        }
                        else if (CurrentDate.after(_Date03) && CurrentDate.before(_Date04)) {
                            _date03Avg = _date03Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date03Counter++;

                        }

                        else if (CurrentDate.after(_Date04) && CurrentDate.before(_Date05)) {
                            _date04Avg = _date04Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date04Counter++;

                        }

                        else if (CurrentDate.after(_Date05) && CurrentDate.before(_Date06)) {
                            _date05Avg = _date05Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date05Counter++;

                        }

                        else if (CurrentDate.after(_Date06) && CurrentDate.before(_Date07)) {
                            _date06Avg = _date06Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date06Counter++;

                        }

                        else if (CurrentDate.after(_Date07) && CurrentDate.before(_Date08)) {
                            _date07Avg = _date07Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date07Counter++;

                        }

                        else if (CurrentDate.after(_Date08) && CurrentDate.before(_Date09)) {
                            _date08Avg = _date08Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date08Counter++;

                        }

                        else if (CurrentDate.after(_Date09) && CurrentDate.before(_Date10)) {
                            _date09Avg = _date09Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date09Counter++;

                        }

                        else if (CurrentDate.after(_Date10) && CurrentDate.before(_Date11)) {
                            _date10Avg = _date10Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date10Counter++;

                        }

                        else if (CurrentDate.after(_Date11) && CurrentDate.before(_Date12)) {
                            _date11Avg = _date11Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date11Counter++;

                        }
                        else if (CurrentDate.after(_Date12)) {
                            _date12Avg = _date12Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date12Counter++;


                        }

                        else {

                        }


                    }


                    _date01Avg = _date01Avg / _date01Counter;
                    _date02Avg = _date02Avg / _date02Counter;
                    _date03Avg = _date03Avg / _date03Counter;
                    _date04Avg = _date04Avg / _date04Counter;
                    _date05Avg = _date05Avg / _date05Counter;
                    _date06Avg = _date06Avg / _date06Counter;
                    _date07Avg = _date07Avg / _date07Counter;
                    _date08Avg = _date08Avg / _date08Counter;
                    _date09Avg = _date09Avg / _date09Counter;
                    _date10Avg = _date10Avg / _date10Counter;
                    _date11Avg = _date11Avg / _date11Counter;
                    _date12Avg = _date12Avg / _date12Counter;
                    //Mayank 3.3.2020: creating description for Profits pie chart
                    Description desc = new Description();
                    desc.setText("Avg Yearly Employee Hours Worked");
                    desc.setTextSize(30f);
                    _ScatterChart.setDescription(desc);



                    value.add(new Entry(1, (float) _date01Avg));
                    value.add(new Entry(2, (float) _date02Avg));
                    value.add(new Entry(3, (float) _date03Avg));
                    value.add(new Entry(4, (float) _date04Avg));
                    value.add(new Entry(5, (float) _date05Avg));
                    value.add(new Entry(6, (float) _date06Avg));
                    value.add(new Entry(7, (float) _date07Avg));
                    value.add(new Entry(8, (float) _date08Avg));
                    value.add(new Entry(9, (float) _date09Avg));
                    value.add(new Entry(10, (float) _date10Avg));
                    value.add(new Entry(11, (float) _date11Avg));
                    value.add(new Entry(12, (float) _date12Avg));



                    ScatterDataSet LinedataSet1 = new ScatterDataSet(value, "data set 1");
                    ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
                    dataSets.add(LinedataSet1);
                    ScatterData data = new ScatterData(dataSets);
                    _ScatterChart.setData(data);
                    _ScatterChart.invalidate();

                }

                else  if (_listselected == "Inventory list"){

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
                    ListInfo listinfo = ListInfo.get(getActivity());

                    List<Profits> profits = listinfo.getInfo();
                    for (int i = 0; i < profits.size(); i++) {

                        try {
                            CurrentDate = DateFormat.parse(profits.get(i).getmDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (CurrentDate.after(_Date01) && CurrentDate.before(_Date02)) {
                            _date01Avg = _date01Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date01Counter++;

                        } else if (CurrentDate.after(_Date02) && CurrentDate.before(_Date03)) {

                            _date02Avg = _date02Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date02Counter++;

                        }
                        else if (CurrentDate.after(_Date03) && CurrentDate.before(_Date04)) {
                            _date03Avg = _date03Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date03Counter++;

                        }

                        else if (CurrentDate.after(_Date04) && CurrentDate.before(_Date05)) {
                            _date04Avg = _date04Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date04Counter++;

                        }

                        else if (CurrentDate.after(_Date05) && CurrentDate.before(_Date06)) {
                            _date05Avg = _date05Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date05Counter++;

                        }

                        else if (CurrentDate.after(_Date06) && CurrentDate.before(_Date07)) {
                            _date06Avg = _date06Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date06Counter++;

                        }

                        else if (CurrentDate.after(_Date07) && CurrentDate.before(_Date08)) {
                            _date07Avg = _date07Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date07Counter++;

                        }

                        else if (CurrentDate.after(_Date08) && CurrentDate.before(_Date09)) {
                            _date08Avg = _date08Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date08Counter++;

                        }

                        else if (CurrentDate.after(_Date09) && CurrentDate.before(_Date10)) {
                            _date09Avg = _date09Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date09Counter++;

                        }

                        else if (CurrentDate.after(_Date10) && CurrentDate.before(_Date11)) {
                            _date10Avg = _date10Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date10Counter++;

                        }

                        else if (CurrentDate.after(_Date11) && CurrentDate.before(_Date12)) {
                            _date11Avg = _date11Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date11Counter++;

                        }
                        else if (CurrentDate.after(_Date12)) {
                            _date12Avg = _date12Avg + Double.parseDouble(profits.get(i).getmAmount());
                            _date12Counter++;


                        }

                        else {

                        }


                    }


                    _date01Avg = _date01Avg / _date01Counter;
                    _date02Avg = _date02Avg / _date02Counter;
                    _date03Avg = _date03Avg / _date03Counter;
                    _date04Avg = _date04Avg / _date04Counter;
                    _date05Avg = _date05Avg / _date05Counter;
                    _date06Avg = _date06Avg / _date06Counter;
                    _date07Avg = _date07Avg / _date07Counter;
                    _date08Avg = _date08Avg / _date08Counter;
                    _date09Avg = _date09Avg / _date09Counter;
                    _date10Avg = _date10Avg / _date10Counter;
                    _date11Avg = _date11Avg / _date11Counter;
                    _date12Avg = _date12Avg / _date12Counter;
                    //Mayank 3.3.2020: creating description for Profits pie chart
                    Description desc = new Description();
                    desc.setText("Avg Yearly Inventory Cost");
                    desc.setTextSize(30f);
                    _ScatterChart.setDescription(desc);



                    value.add(new Entry(1, (float) _date01Avg));
                    value.add(new Entry(2, (float) _date02Avg));
                    value.add(new Entry(3, (float) _date03Avg));
                    value.add(new Entry(4, (float) _date04Avg));
                    value.add(new Entry(5, (float) _date05Avg));
                    value.add(new Entry(6, (float) _date06Avg));
                    value.add(new Entry(7, (float) _date07Avg));
                    value.add(new Entry(8, (float) _date08Avg));
                    value.add(new Entry(9, (float) _date09Avg));
                    value.add(new Entry(10, (float) _date10Avg));
                    value.add(new Entry(11, (float) _date11Avg));
                    value.add(new Entry(12, (float) _date12Avg));



                    ScatterDataSet LinedataSet1 = new ScatterDataSet(value, "data set 1");
                    ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
                    dataSets.add(LinedataSet1);
                    ScatterData data = new ScatterData(dataSets);
                    _ScatterChart.setData(data);
                    _ScatterChart.invalidate();
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