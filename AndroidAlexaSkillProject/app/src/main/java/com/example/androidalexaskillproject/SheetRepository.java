package com.example.androidalexaskillproject;


/*  M. Marinaro 3/6/20
    Singleton Repository for all sheets and sheet information
    TODO this should entirely house the current sheet, using a list of column names and proceeding rows?
    TODO Find out if that would work
 */

import java.util.ArrayList;
import java.util.List;

public class SheetRepository {

    private static SheetRepository repository = null;

    private List<Sheet> sheetList;

    private String sheetName;   //the sheet name can be changed by way of public methods after construction
    private String sheetCodeUrl = "https://script.google.com/macros/s/AKfycbxKx3h5nkb6LF840GZhNeFJNjWZVP08Lm4HiqUWcmYSdMoZDjU/exec"; //Url for GoogleScript code to call for functions
    private String sheetUrl = "1KE91K1eYxlfSV9gHfI1LBNaCtAS_c-o8rTF92NlEWvg"; //Url of sheet to edit

    private SheetRepository(){
        sheetName = test(); //TODO: Remove this test
    }

    public static SheetRepository getInstance(){
        if (repository == null)
            repository = new SheetRepository();

        return repository;
    }

    public void changeSheet(){
        List<Sheet> newSheet = new ArrayList<>();
        sheetList = newSheet;

    }

    //M.Marinaro : we dont want that done anywhere else for now
    public void setSheetUrl(String s) {
        this.sheetUrl = s;
    }

    public String getSheetUrl(){
        return sheetUrl;
    }

    public String getSheetCodeUrl(){
        return sheetCodeUrl;
    }

    //M. Marinaro : we dont want that done anywhere else for now
    public void setSheetName(String s) {
        this.sheetName = s;
    }

    public String getSheetName(){
        return sheetName;
    }


    //Tests to change to currently read list
    public String test(){
        return "profits";
    }

    public String changeTest(){
        return "expenses";
    }



    //methods to switch between sheets
    public void setSheetProfits() {
        sheetName = "profits";
    }

    public void setSheetExpenses() {
        sheetName = "expenses";
    }
}
