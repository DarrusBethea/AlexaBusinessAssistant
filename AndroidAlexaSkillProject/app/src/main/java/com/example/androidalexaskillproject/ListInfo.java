package com.example.androidalexaskillproject;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;

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

public class ListInfo {


    // FAH 2/22/2020: this list is where we will store the sheet data
    public static ArrayList<String> InfoListView = new ArrayList<>();
public static  int counter = 0;
    private static ListInfo sListInfo;
    private List<Profits> mProfits;


    private ListInfo(Context context) {
        mProfits = new ArrayList<>();

        for (int i = 0; i < InfoListView.size() - 1; i++) {
            Profits _profits = new Profits();
            //FAH2/23/2020: getting the sheet info this will update the ui list
            String line = InfoListView.get(i);
            //FAH2/23/2020: spliting the list by a "
            String[] splitted = line.split("\"");
            String[] Datesplitted = splitted[13].split(":") ;
            Datesplitted[0] = Datesplitted[0].substring(0,Datesplitted[0].length() - 3);

            //Fah 2/23/2020: this is name and value of the row in the list then will add it to
            //mProfits
            _profits.setmName(splitted[3]);
            _profits.setmLastname(splitted[7]);
            _profits.setmAmount(splitted[10].replaceAll(":", "").replaceAll("\\," , ""));
            _profits.setmDate(Datesplitted[0]);
            mProfits.add(_profits);
        }


    }

    public static ListInfo get(Context context) {

        if (sListInfo == null) {
            sListInfo = new ListInfo(context);
        }
        return sListInfo;
    }


    public List<Profits> getInfo() {

        return mProfits;

    }

    // FAH 4/17/2020: this method gets the sheets data and then pushes it to a list.
    public static class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            String result = "";
            HttpResponseCache cache = HttpResponseCache.getInstalled();
            while (cache != null){
                cache.flush();

            }


            try {
                URL url = new URL(
                        "https://script.google.com/macros/s/AKfycbzRJecRXqinxLQxHRix6F3JmjHso5NyxNgXABdWrDIhwjM4UvY/exec?id="+ SheetRepository.getInstance().getSheetUrl()+"&sheet=" + SheetRepository.getInstance().getSheetName());
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
                //FAH 2/23/2020 when called method will wait to finsih
                //good for adding in the data to the arraylist real quick
                String[] separated = result.split("\\{");
                for (int i = 2; i < separated.length; i++) {
                    InfoListView.add(separated[i].replaceAll("\\},", "").replaceAll("\\}\\]\\}", ""));



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
