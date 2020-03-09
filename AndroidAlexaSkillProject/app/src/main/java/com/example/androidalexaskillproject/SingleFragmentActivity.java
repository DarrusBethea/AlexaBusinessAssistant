package com.example.androidalexaskillproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public abstract class SingleFragmentActivity extends AppCompatActivity {
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


    //Options Menu
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

            //M.Marinaro 3/4/20 : TODO Implament general add button
//            case R.id.Add_item:
////                //Intent AddItemintent = new Intent(this, ProfitsAddFragments.class);
////                //startActivity(AddItemintent);
////                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
