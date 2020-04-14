package com.example.androidalexaskillproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public abstract class SingleFragmentActivty extends AppCompatActivity {
    protected abstract Fragment createFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.drop_down_menu_list:
                Toast.makeText(this, "drop down menu selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Profits_list:
                //M.Marinaro 3/4/20 : By selecting this menu item the current sheet name is changed
                SheetRepository.getInstance().setSheetProfits();
                //M.Marinaro 3/4/20 : Then refresh the page
                Intent ProfitListintent = new Intent(this, MainListActivity.class);
                startActivity(ProfitListintent);
                return true;

            //M.Marinaro 3/4/20 : Same Here
            case R.id.Expenses_list:
                SheetRepository.getInstance().setSheetExpenses();
                Intent ExpensesListIntent = new Intent(this, MainListActivity.class);
                startActivity(ExpensesListIntent);
                return true;


            //M.Marinaro 3/9/20 : Option to select the employees list
            case R.id.Employees_list:
                SheetRepository.getInstance().setSheetEmployees();
                Intent EmployeeListIntent = new Intent(this, MainListActivity.class);
                startActivity(EmployeeListIntent);
                return true;

            case R.id.Inventory_list:
                SheetRepository.getInstance().setSheetInventory();
                Intent InventoryListIntent = new Intent(this, MainListActivity.class);
                startActivity(InventoryListIntent);
                return true;

            case R.id.GrossProfit_list:
                Intent GrossProfitListintent = new Intent(this, MainListGrossProfitActivity.class);
                startActivity(GrossProfitListintent);
                return true;


            //M.Marinaro 3/9/20 : General Add Button
            case R.id.Add_item:
                Intent AddItemintent = new Intent(this, ProfitReturnAddFragment.class);
                startActivity(AddItemintent);
                return true;

            case R.id.select_stats:
                Toast.makeText(this, "stats selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Pie_chart:
                Toast.makeText(this, "pie chart selected", Toast.LENGTH_SHORT).show();
                Intent PieChartItent = new Intent(this, PieChartReturnFragment.class);
                startActivity(PieChartItent);

                return true;

            case R.id.Bar_chart:
                Toast.makeText(this, "bar chart selected", Toast.LENGTH_SHORT).show();
                Intent BarChartItent = new Intent(this, BarChartReturnFragment.class);
                startActivity(BarChartItent);

                return true;

            case R.id.Line_chart:
                Toast.makeText(this, "line chart selected", Toast.LENGTH_SHORT).show();
                Intent LineChartItent = new Intent(this, LineChartReturnFragment.class);
                startActivity(LineChartItent);

                return true;

            case R.id.SignIn_option:
                Intent SigninIntent = new Intent(this, LoginScreenReturnFragment.class);
                startActivity(SigninIntent);

                return true;

            case R.id.loginButton:
                Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show();
                //Intent loginIntent = new Intent(this, HomeScreenReturnFragment.class);
                //startActivity(loginIntent);

                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
