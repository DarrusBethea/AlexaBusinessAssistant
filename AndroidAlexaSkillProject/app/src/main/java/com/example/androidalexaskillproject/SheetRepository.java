package com.example.androidalexaskillproject;


/*  M. Marinaro 3/9/20
    Singleton Repository for all sheets and sheet information
    For now just hold all variables used elsewhere for the many sheets we can access
    TODO this should entirely house the current sheet, using a list of column names and proceeding rows?
    TODO Find out if that would work
 */

public class SheetRepository {

    private static SheetRepository repository = null;

    private final String SHEET_CODE_URL = "https://script.google.com/macros/s/AKfycbxKx3h5nkb6LF840GZhNeFJNjWZVP08Lm4HiqUWcmYSdMoZDjU/exec"; //Url for GoogleScript code to call for functions
    private final String SHEET_URL = "1KE91K1eYxlfSV9gHfI1LBNaCtAS_c-o8rTF92NlEWvg"; //Url of sheet to edit

    //Individual sheet info
    private String sheetName;

    private String column_1;    //M.Marinaro 3/0/20 : columns named here will be called as necessary elsewhere to modify strings based on the currently selected sheet
    private String column_2;    //                      These are just the text names of the columns, NOT the variable names on the sheets
    private String column_3;
    private String column_4;
    private String units;       //M.Marinaro 4/13/20 : Units for the value related column in each sheet (currently the second to last)




    //M.Marinaro 3/0/20 : Constructor, initializes as profit sheet

    public static SheetRepository getInstance(){
        if (repository == null) {
            repository = new SheetRepository();
            repository.setSheetProfits(); //initialize to profits sheet
        }

        return repository;
    }

    //M.Marinaro 3/0/20 : Getter methods. Avoiding use of set methods to keep everything in this class for now
    public String getSheetUrl(){
        return SHEET_URL;
    }

    public String getSheetCodeUrl(){
        return SHEET_CODE_URL;
    }

    public String getSheetName(){
        return sheetName;
    }

    public String getColumn_1() {
        return column_1;
    }

    public String getColumn_2() {
        return column_2;
    }

    public String getColumn_3() {
        return column_3;
    }

    public String getColumn_4() {
        return column_4;
    }

    public String getUnits() {
        return units;
    }



    //TODO These set methods should be turned into arraylists stored in a list within the repository
    //methods to switch between sheets
    public void setSheetProfits() {
        sheetName = "profits";
        column_1 ="First Name";
        column_2 ="Last Name";
        column_3 ="Profits Amount";
        column_4 ="Date";
        units = " $";
    }

    public void setSheetExpenses() {
        sheetName = "expenses";
        column_1 ="Name";
        column_2 ="Type";
        column_3 ="Expense Amount";
        column_4 ="Date";
        units = " $";
    }

    public void setSheetEmployees() {
        sheetName = "employees";
        column_1 ="First Name";
        column_2 ="Last Name";
        column_3 ="Hours Worked";
        column_4 ="Date";
        units = " hrs";
    }

    public void setSheetInventory() {
        sheetName = "Inventory";
        column_1 ="Item Name";
        column_2 ="Quantity";
        column_3 ="Unit Price";
        column_4 ="Date";
        units = " ";
    }


//M.Marinaro 3/9/20 : the lack of a date as the fourth column restricts this from working
//    public void setSheetInventory() {
//        sheetName = "Inventory";
//        column_1 ="Name";
//        column_2 ="Quantity";
//        column_3 ="Unit Price";
//        column_4 ="Total Cost";
//    }
}
