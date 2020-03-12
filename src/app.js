'use strict';

// ------------------------------------------------------------------
// APP INITIALIZATION
// ------------------------------------------------------------------

const { App } = require('jovo-framework');
const { Alexa } = require('jovo-platform-alexa');
const { GoogleAssistant } = require('jovo-platform-googleassistant');
const { JovoDebugger } = require('jovo-plugin-debugger');
const { FileDb } = require('jovo-db-filedb');
const { GoogleSheetsCMS } = require('jovo-cms-googlesheets');

const app = new App();
//-------------------------
//DICTIONARY
//-------------------------
let dict = {
    monthColumn : 0,
    incomeColumn : 1,
    cogsColumn : 2,
    gpColumn : 3,
    expensesColumn : 4,
    oiColumn : 5,
    netColumn : 6,
    totalsRow : 13
};

app.use(
    new GoogleSheetsCMS(),
    new Alexa(),
    new GoogleAssistant(),
    new JovoDebugger(),
    new FileDb()
);

//----------------------]]]]]]
//Functions
//----------------------]]]]]]

/*
Switch to return the int value for a given input Month
month: month value returned from this.$inputs.month.value
*/
function getMonth(month){
    var value;
    switch(month){
        case 'January': 
            value = 1;
            break;
        case 'February': 
            value = 2;
            break;
        case 'March': 
            value = 3;
            break;
        case 'April': 
            value = 4;
            break;
        case 'may': 
            value = 5;
            break;
        case 'June': 
            value = 6;
            break;
        case 'July': 
            value = 7;
            break;
        //SOME OF THESE NEED TO BE lowercase ???????
        case 'august': 
            value = 8;
            break;
        case 'September': 
            value = 9;
            break;
        case 'October': 
            value = 10;
            break;
        case 'November': 
            value = 11;
            break;
        case 'December': 
            value = 12;
            break;
    }
    return value;
}

/*
Returns the sheet for a given year and type
Type: either "pl" or "bs" (more in the future just need elseif)
year: given year that corresponds to a sheet
*/
function getSheet(year, type){
    var sheet;
    if(type == "pl"){
        switch(year){
            case 2020:
                sheet = app.$cms.pl_year20.slice();
                break;
            case 2019:
                sheet = app.$cms.pl_year19.slice();
                break;
            //ADD MORE TO CREATE MORE YEARS
            
            default:
                //FIND FIX FOR IF A UNSTORED SHEET YEAR IS GIVEN 
                break;
        }
    }
    /*
    else{
        switch(year){
            case 2020:
                sheet = app.$cms.bs_year20.slice();
                break;
            default:
                break;
        }
    }
    */
    return sheet;
}

/*
Returns the cell value of a sheet in the pl format 
sheetValue: the sheet slice given from getSheet
rowValue: row of the value you are returning (usually month)
columnValue: the column of the cell you are returning (FROM COLUMN DICTIORNARY)
return: the value of the cell
*/
function getValue(sheetValue, rowValue, columnValue){
    var value;
    var sheet = sheetValue;
    var row = rowValue;
    var column = columnValue;
    value = sheet[row][column];
    return value;
}

// ------------------------------------------------------------------
// APP LOGIC
// ------------------------------------------------------------------

app.setHandler({
    LAUNCH() {
        this.$speech.addT('welcome.speech');
        this.ask(this.$speech);
    },

    HelloWorldIntent() {
        this.ask(this.t('welcome.speech'), this.t('welcome.reprompt'));
    },

    MyNameIsIntent() {
        this.tell(this.t('greeting.speech', { name: this.$inputs.name.value }));
    },

    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //Total Intents for PL sheet
    //---------------------------------------------------------------
    //---------------------------------------------------------------

    /*
    Returns Total income for a given pl Spreadsheet
    input: year
    response.Income:
    */
    TotalIncomeIntent(){
        var year = Number(this.$inputs.year.value);
        var sheet = getSheet(year, "pl");
        var income = getValue(sheet, 13, 1)
        this.$speech.addT('response.Income',{income});
        this.ask(this.$speech);
    },

    /*
    Returns Total COGS for a given pl spreadsheet
    input: year
    response.Cogs:
    */
    TotalCOGSIntent(){
        var year = Number(this.$inputs.year.value);
        var sheet = getSheet(year, "pl");
        var COGS = getValue(sheet, 13, 2)
        this.$speech.addT('response.Cogs',{COGS});
        this.ask(this.$speech);
    },

    /*
    Returns Gross Profit for a given year
    slots: year
    response.GrossProfit:
    */
    TotalGPIntent(){
        var year = Number(this.$inputs.year.value);
        var sheet = getSheet(year, "pl");
        var GP = getValue(sheet, 13, 3);
        this.$speech.addT('response.GrossProfit',{GP});
        this.ask(this.$speech);
    },

    /*
    Returns total expenses for a given year
    input: year
    response.Expenses:
    */
    TotalExpensesIntent(){
        var year = Number(this.$inputs.year.value);
        var sheet = getSheet(year, "pl");
        var expenses = getValue(sheet, 13, 4);
        this.$speech.addT('response.Expenses',{expenses});
        this.ask(this.$speech);
    },

    /*
    Returns total ordinary income for a given year
    input: year
    response.Ordinary:
    */
    TotalOrdinaryIncomeIntent(){
        var year = Number(this.$inputs.year.value);
        var sheet = getSheet(year, "pl");
        var ordinary = getValue(sheet, 13, 5);
        this.$speech.addT('response.Ordinary',{ordinary});
        this.ask(this.$speech);
    },

    /*
    Returns the total Net Income for a given year
    input: year
    response.netIncome:
    */
    TotalNetIncomeIntent(){
        var year = Number(this.$inputs.year.value);
        var sheet = getSheet(year, "pl");
        var net = getValue(sheet, 13, 6);
        this.$speech.addT('response.netIncome',{net});
        this.ask(this.$speech);
    },


    //------------------------------------------------------------------
    //------------------------------------------------------------------
    //Monthly Intents for profit losses sheet
    //------------------------------------------------------------------
    //------------------------------------------------------------------

    /*
    XXXXXXXXXX NEEDS FIXING in Alexa dev console XXXXXXXXXXXX
    Returns a given cell in the spreadsheet for the income column based on month
    input: year, month
    response.MonthlyIncome: 
    */
    MonthlyIncomeIntent(){
        var year = Number(this.$inputs.year.value);
        var inputMonth = this.$inputs.month.value;
        var month = Number(getMonth(inputMonth));
        var sheet = getSheet(year, "pl");

        var net = getValue(sheet, month, 1);

        this.$speech.addT('response.MonthlyIncome', {inputMonth, year, net});
        this.ask(this.$speech);
    },

    /*
     XXXXXXXXXX NEEDS FIXING in Alexa dev console XXXXXXXXXXXX
    Returns a given cell in the spreadsheet for the income column based on month
    input: year, month
    response.MonthlyCOGS
    */
    MonthlyCOGSIntent(){
        var year = Number(this.$inputs.year.value);
        var inputMonth = this.$inputs.month.value;
        var month = Number(getMonth(inputMonth));
        var sheet = getSheet(year, "pl");

        var Cogs = getValue(sheet, month, 1);
        //Needs cells in the spreadsheet response.monthlyCOGS
        this.$speech.addT('response.MonthlyCOGS', {inputMonth, year, Cogs});
        this.ask(this.$speech);
    },

    /*
     XXXXXXXXXX NEEDS FIXING in Alexa dev console XXXXXXXXXXXX
    Returns a given cell in the spreadsheet for the GP column based on month
    input: year, month
    response.MonthlyGP
    */
   MonthlyGPIntent(){
    var year = Number(this.$inputs.year.value);
    var inputMonth = this.$inputs.month.value;
    var month = Number(getMonth(inputMonth));
    var sheet = getSheet(year, "pl");
    //random changes
    var GP = getValue(sheet, month, 1);
    //Needs cells in the spreadsheet response.monthlyGP
    this.$speech.addT('response.MonthlyGP', {inputMonth, year, GP});                                                
    this.ask(this.$speech);
},

    /*
     XXXXXXXXXX NEEDS FIXING in Alexa dev console XXXXXXXXXXXX
    Returns a given cell in the spreadsheet for the expenses column based on month
    input: year, month
    response.MonthlyExpenses
    */
   MonthlyExpensesIntent(){
    var year = Number(this.$inputs.year.value);
    var inputMonth = this.$inputs.month.value;
    var month = Number(getMonth(inputMonth));
    var sheet = getSheet(year, "pl");

    var Expenses = getValue(sheet, month, 1);
    //Needs cells in the spreadsheet response.monthlyExpenses
    this.$speech.addT('response.MonthlyExpenses', {inputMonth, year, Expenses});
    this.ask(this.$speech);
},

    /*
     XXXXXXXXXX NEEDS FIXING in Alexa dev console XXXXXXXXXXXX
    Returns a given cell in the spreadsheet for the Ordinart income column based on month
    input: year, month
    response.MonthlyOI
    */
   MonthlyOrdinaryIncomeIntent(){
    var year = Number(this.$inputs.year.value);
    var inputMonth = this.$inputs.month.value;
    var month = Number(getMonth(inputMonth));
    var sheet = getSheet(year, "pl");

    var OI = getValue(sheet, month, 1);
    //Needs cells in the spreadsheet response.monthlyOI
    this.$speech.addT('response.MonthlyOI', {inputMonth, year, OI});
    this.ask(this.$speech);
},

    /*
        XXXXXXXXXX NEEDS FIXING in Alexa dev console XXXXXXXXXXXX
    Returns a given cell in the spreadsheet for the Ordinart income column based on month
    input: year, month
    response.MonthlyNet
    */
   MonthlyNetIncomeIntent(){
    var year = Number(this.$inputs.year.value);
    var inputMonth = this.$inputs.month.value;
    var month = Number(getMonth(inputMonth));
    var sheet = getSheet(year, "pl");

    var net = getValue(sheet, month, 1);
    this.$speech.addT('response.MonthlyNet', {inputMonth, year, net});
    this.ask(this.$speech);
}


});

module.exports.app = app;
