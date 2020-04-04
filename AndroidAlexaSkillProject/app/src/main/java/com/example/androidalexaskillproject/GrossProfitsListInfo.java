package com.example.androidalexaskillproject;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

public class GrossProfitsListInfo  {



    // FAH 2/22/2020: this list is where we will store the sheet data
    public static ArrayList<String> InfoListView = new ArrayList<>();

    public static ArrayList<String> InfoListProfts = new ArrayList<>();

    public static ArrayList<String> InfoListExpenses = new ArrayList<>();

    public static ArrayList<String> InfoListGrossProfit = new ArrayList<>();

    public static ArrayList<String> InfoListGrossProfitMonth = new ArrayList<>();
    public static int counter = 0;
    private static GrossProfitsListInfo sListInfo;
    private List<GrossProfit> mGrossProfit;





    private GrossProfitsListInfo(Context context) {
        mGrossProfit = new ArrayList<>();


        if (GrossProfit.getSpinnerChange() == 1){


            for (int i = 0; i < InfoListProfts.size() - 1; i++) {
                GrossProfit _grossprofits = new GrossProfit();
                _grossprofits.setmAmountGrossProfit(InfoListProfts.get(i));
                _grossprofits.setMonth(InfoListGrossProfitMonth.get(i));
                mGrossProfit.add(_grossprofits);
            }
        }

        else if (GrossProfit.getSpinnerChange() == 2 ){

            for (int i = 0; i < InfoListExpenses.size() - 1; i++) {
                GrossProfit _grossprofits = new GrossProfit();
                _grossprofits.setmAmountGrossProfit(InfoListExpenses.get(i));
                _grossprofits.setMonth(InfoListGrossProfitMonth.get(i));

                mGrossProfit.add(_grossprofits);
            }
        }

        else if (GrossProfit.getSpinnerChange() == 3 ){

            for (int i = 0; i < InfoListGrossProfit.size() - 1; i++) {
                GrossProfit _grossprofits = new GrossProfit();
                _grossprofits.setmAmountGrossProfit(InfoListGrossProfit.get(i));
                _grossprofits.setMonth(InfoListGrossProfitMonth.get(i));

                mGrossProfit.add(_grossprofits);
            }
        }

    }

    public static GrossProfitsListInfo get (Context context){

        if (sListInfo == null) {
            sListInfo = new GrossProfitsListInfo(context);
        }
        return sListInfo;
    }


    public List<GrossProfit> getInfo () {

        return mGrossProfit;

    }


    public static class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            String result = "";
            HttpResponseCache cache = HttpResponseCache.getInstalled();
            while (cache != null) {
                cache.flush();

            }


            try {
                URL url = new URL(
                        "https://script.google.com/macros/s/AKfycbzRJecRXqinxLQxHRix6F3JmjHso5NyxNgXABdWrDIhwjM4UvY/exec?id=1KE91K1eYxlfSV9gHfI1LBNaCtAS_c-o8rTF92NlEWvg&sheet=gross_profit");
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();

                if (code == 200) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    if (in != null) {

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                        String line = "";

                        while ((line = bufferedReader.readLine()) != null)
                            result += line;
                        System.out.println(result);
                    }
                    in.close();
                }

                //FAH2/29/2020: clearing the list good for when the list needs to update
                InfoListView.clear();
                InfoListProfts.clear();
                InfoListExpenses.clear();
                InfoListGrossProfit.clear();
                InfoListGrossProfitMonth.clear();
                //FAH 2/23/2020 when called method will wait to finsih
                //good for adding in the data to the arraylist real quick
                String[] separated = result.split("\\{");
                for (int i = 2; i < separated.length; i++) {
                    InfoListView.add(separated[i].replaceAll("\\},", "").replaceAll("\\}\\]\\}", ""));


                }


                for (int i = 0; i < InfoListView.size(); i++) {
                    String line = InfoListView.get(i);
                    String[] separatedNewList = line.split("\"");
                    InfoListGrossProfitMonth.add(separatedNewList[3].replaceAll(",", "").replaceAll(":", ""));
                    InfoListProfts.add( separatedNewList[6].replaceAll(",", "").replaceAll(":", ""));
                    InfoListExpenses.add(separatedNewList[8].replaceAll(",", "").replaceAll(":", ""));
                    InfoListGrossProfit.add( separatedNewList[10].replaceAll(",", "").replaceAll(":", ""));

                }


                // FAH 2/29/2020: empty the list so it will refresh the UI
                sListInfo = null;
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            if (cache != null) {
                cache.flush();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
        }
    }

}

