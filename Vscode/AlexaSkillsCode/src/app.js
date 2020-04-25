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
const requestPromise = require('request-promise-native');

const app = new App();
//-------------------------
//DICTIONARY
//jake
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
    new Alexa(),
    new GoogleAssistant(),
    new JovoDebugger(),
    new FileDb(),
    new GoogleSheetsCMS
);

/*
jake(only works for pl sheet atm)
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
    return sheet;
}

/*
jake
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


// ------------------------------------------------------------------
// APP LOGIC
// ------------------------------------------------------------------
var CheckNameNum = 0;       //FAH 3/25/2020: these 4 vars are used in the profits sheet incase alexa cannot find the correct last name
var FirstnameSave = "";
var LastNameSave = "";
var ProfitSheetProfitSave = 0;

// use these for yes and no intents
var YesKey = 0;
var NoKey = 0;
app.setHandler({
    LAUNCH() {
      
     this.ask("Welcome, please ask what you would like to know about your current business, from your data sheets");
    },

    //FAH 3/25/2020: fall back intent used if error occurs so alexa does not crash out
    'AMAZON.FallbackIntent'(){
      this.ask("sorry i did not under stand that, please try again, or ask something else");
    },

        //FAH 3/25/2020: used to cofirm who the user is talking about to alexa
        //probably be good to use a key for this intent incase other people also need a yes intent later on
    YesIntent(){

        if (YesKey == 0){
            this.ask("please try asking a new command");

        }
        else if (YesKey == 1){
        this.ask( "the total amount that " + FirstnameSave + " "+ LastNameSave + ", sent you was " + ProfitSheetProfitSave + " dollars" +   ". is their anything else you would like to know?") ;
         FirstnameSave = "";
         LastNameSave = "";
         ProfitSheetProfitSave = 0;
         CheckNameNum = 0;
         YesKey = 0;
         NoKey = 0;
        }
    },

    //FAH 3/25/2020: if user saids no alexa will go back into the profit function the find next last name the first name fits with
    async NoIntent(){
        if (NoKey == 0){

            this.tell("GoodBye and have a great day");
       
        }
       else  if (NoKey == 1){
        const responsetotalexpense = await getSerachNameProfitSheet(FirstnameSave, "", CheckNameNum );
        this.ask(responsetotalexpense);
       }

    },


    TotalProfitsIntent() {
       this.ask(this.t('get.total') + ". is their anything else you would like to know?");
    
    },
    
        //FAH 3/25/2020: if user asks alexa what person last name and first name is will respond with the amount they gave from the progit sheet
    async  ProfitNameSearchIntent(){
        CheckNameNum = 0;
        var Firstname = this.$inputs.firstname.value ;
        var LastName = this.$inputs.lastname.value ;
        YesKey = 1;
        NoKey = 1;
         //FAH 3/25/2020: cap the first char in the name
        Firstname =   Firstname.charAt(0).toUpperCase() + Firstname.slice(1);
        const responsetotalexpense = await getSerachNameProfitSheet(Firstname, LastName);
        this.ask(responsetotalexpense);
   
    
    },
    //FAH 3/25/2020: when user ask alexa total expense from the month will return back with price from gross profits sheet
    async  MothlyExpensesIntent(){
        var month = this.$inputs.months.value ;
        var CheckGammar = ""
        for (var i = 0; i < month.length; i++) {
        
            CheckGammar = month[i];

        }
        // incase the month ends with an s
        if (CheckGammar == "s"){

            month = month.substring(0, month.length - 2);
        }

        month = month.charAt(0).toUpperCase() + month.slice(1)
        var key = "gross exepense"
        const responsetotalexpense = await getGrossProfitSheet(month, key.toLowerCase() );
        this.ask(responsetotalexpense + '. is their anything else you would like to know?');
  
    },


    //FAH 3/25/2020: when user ask alexa total profit from the month will return back with price from gross profits sheet
    async  MothlyProfitsIntent(){
     
        var month = this.$inputs.months.value ;
        var CheckGammar = ""
        for (var i = 0; i < month.length; i++) {
        
            CheckGammar = month[i];

        }

        if (CheckGammar == "s"){

            month = month.substring(0, month.length - 2);
        }

       month = month.charAt(0).toUpperCase() + month.slice(1)
        var key = "gross profit";
        const responsetotalprofit = await getGrossProfitSheet(month, key.toLowerCase() );
        this.ask(responsetotalprofit + '. is their anything else you would like to know?');
  
        


    },
   
    //FAH 3/25/2020: when user ask alexa total gross profit from the month will return back with price from gross profits sheet
    async  MothlyTotalIntent(){

        var month = this.$inputs.months.value ;
        var CheckGammar = ""
        for (var i = 0; i < month.length; i++) {
        
            CheckGammar = month[i];

        }

        if (CheckGammar == "s"){

            month = month.substring(0, month.length - 2);
        }

       month = month.charAt(0).toUpperCase() + month.slice(1)
        var key = "total"
        const responsetotalgrossprofit = await getGrossProfitSheet(month, key.toLowerCase());
       this.ask(responsetotalgrossprofit + '. is their anything else you would like to know?');
        
      
    },


 //FAH 3/25/2020: when user ask alexa total gross profit from the month will return back with price from gross profits sheet
 async  ExpenseTypeIntent(){

    var _expensetype = this.$inputs.expensetyperesponse.value;
    console.log(_expensetype);
    var CheckGammar = ""
    for (var i = 0; i < _expensetype.length; i++) {
    
        CheckGammar = _expensetype[i];

    }

    if (CheckGammar == "s"){

        _expensetype = _expensetype.substring(0, _expensetype.length - 2);
    }

    _expensetype = _expensetype.charAt(0).toUpperCase() + _expensetype.slice(1)
    const responseexpensetype = await getSerachExpensetype(_expensetype);
   this.ask(responseexpensetype + '. is their anything else you would like to know?');
    
  
},



    //AD 4/4/2020  If the user says a food name or asks how much inventory, respond with the amount of the requested item if any
    async InventoryTotalIntent(){
        var inventoryName = this.$inputs.food.value;
        const inventoryResponse = await getInventoryTotal(inventoryName);
        this.ask(inventoryResponse);
    },

    //AD 4/4/2020 Get the hours worked for an employee
    async HoursWorkedIntent(){
        var workerName = this.$inputs.person.value;
        const workerResponse = await getHoursWorked(workerName);
        this.ask(workerResponse);
    },

    MyNameIsIntent() {
        //this.tell(this.t('greeting.speech', {name: this.$inputs.name.value }));
       
    },

    // Will: get Q1 total profits from profit sheet
    getQuarterOneIntent() {
        this.ask(this.t('get.quarterOne')  + ". is their anything else you would like to know?");
     
     },

    // Will: get Q2 total profits from profit sheet
     getQuarterTwoIntent() {
        this.ask(this.t('get.quarterTwo')  + ". is their anything else you would like to know?");
     

     },
     
    // Will: get Q3 total profits from profit sheet
     getQuarterThreeIntent() {
        this.ask(this.t('get.quarterThree')  + ". is their anything else you would like to know?");
     

     },

    // Will: get Q4 total profits from profit sheet
     getQuarterFourIntent() {
        this.ask(this.t('get.quarterFour')  + ". is their anything else you would like to know?");
      
     
     },

    // Will: get Q1 total spendings
    quarterOneExpensesIntent() {
        this.ask(this.t('get.quarterOneExpense')  + ". is their anything else you would like to know?");
      
     
     }, 

    // Will: get Q2 total spendings
    quarterTwoExpensesIntent() {
        this.ask(this.t('get.quarterTwoExpense')  + ". is their anything else you would like to know?");
      
     
     },
     
    // Will: get Q3 total spendings
    quarterThreeExpensesIntent() {
        this.ask(this.t('get.quarterThreeExpense')  + ". is their anything else you would like to know?");
      
     
     }, 

    // Will: get Q4 total spendings
    quarterFourExpensesIntent() {
        this.ask(this.t('get.quarterFourExpense')  + ". is their anything else you would like to know?");
      
     
     },


    // Will: gets the person with the highest profit
    ProfitPersonMostIntent() {
        this.ask(this.t('get.personmostmoney')  + ". is their anything else you would like to know?");
      
     
     },

    // Will: gets the person with the least profit
    ProfitPersonLessIntent() {
        this.ask(this.t('get.personlessmoney')  + ". is their anything else you would like to know?");
      
     
     },

    // Will: gets the total gross profit
    TotalGrossProfitIntent() {
            this.ask(this.t('get.totalgrossprofit')  + ". is their anything else you would like to know?");
          
         
     },

    // Will: gets the total expenses 
    TotalExpensesIntent() {
            this.ask(this.t('get.totalexpenses')  + ". is their anything else you would like to know?");
          
         
     }, 


     //FAH 3/25/2020: ends convo with alexa
     EndTalkIntent(){

        this.tell("GoodBye and have a great day")
     },

    /*
    jake
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
    jake
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
    jake
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
    jake
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
    jake
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
    jake
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

    /*
    jake
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

});

//FAH 3/25/2020: this function gets data from gross profit sheet
async function getGrossProfitSheet( month, key) {
    const options = {
        uri: 'https://script.google.com/macros/s/AKfycbzRJecRXqinxLQxHRix6F3JmjHso5NyxNgXABdWrDIhwjM4UvY/exec?id=1KE91K1eYxlfSV9gHfI1LBNaCtAS_c-o8rTF92NlEWvg&sheet=gross_profit',
       json: true // Automatically parses the JSON string in the response
    };
    var StringSplitProfit = "";
    const data = await requestPromise(options);
    //FAH 3/25/2020: parsing the data that just came in
    var myJSON = JSON.stringify(data);
    var StringSplit = myJSON.split(",");
 

     for (var i = 0; i < StringSplit.length; i++) {
            //FAH 3/25/2020:checking to find the spot in the data of the month the user said
          if (StringSplit[i].includes(month) == true){
   
 
                 if (key == "gross profit"){

                    //FAH 3/25/2020: if true splits the data in the are of the spefic month, and gets the profit
                    StringSplitProfit =StringSplit[i + 1].split(":");
                    StringSplitProfit = StringSplitProfit[1];
                    return "this "+ month + "'s profit was " + StringSplitProfit + " dollars";
                }

                  else if (key == "gross exepense") {

                  console.log( StringSplit[i + 2] ); 
                  StringSplitProfit =StringSplit[i + 2].split(":");
                  StringSplitProfit = StringSplitProfit[1];
                  return "this "+ month + "'s expense was " + StringSplitProfit + " dollars";

                }

                  else if (key == "total"){

                     console.log( StringSplit[i + 3] ); 
                     StringSplitProfit =StringSplit[i + 3].split(":");
                     StringSplitProfit = StringSplitProfit[1].replace(/}/g, '');
                     return "this "+ month + "'s gross profit was " + StringSplitProfit + " dollars";
                }
    
        }
 
    }

    return "Sorry that was not found";
}


//FAH 3/25/2020: searchers through the profit sheet for first name and last name
async function getSerachNameProfitSheet( Firstname, Lastname) {

    var StringSplitProfit = "";
    
    const options = {
       
        uri: 'https://script.google.com/macros/s/AKfycbzRJecRXqinxLQxHRix6F3JmjHso5NyxNgXABdWrDIhwjM4UvY/exec?id=1KE91K1eYxlfSV9gHfI1LBNaCtAS_c-o8rTF92NlEWvg&sheet=profits',
       json: true // Automatically parses the JSON string in the response
    };

    const data = await requestPromise(options);
    var myJSON = JSON.stringify(data);
    var StringSplit = myJSON.split(",");

     for (var i = CheckNameNum; i < StringSplit.length; i++) {

        //FAH 3/25/2020: as of now there is no good way for alexa to find last names so this was the quickest way that i could think of
        // i tested alexa with last names and she never got one right, the best way would be to train her. however i dont feel like doing that 
        // basically this will just ask the user if ther person is the right person there looking for.
      if (StringSplit[i].includes(Firstname) == true || StringSplit[i].includes(Firstname.toLowerCase()) == true){
                    var StringSplitName = "";

                    StringSplitName = StringSplit[i + 1].split(":");
                    StringSplitProfit = StringSplit[i + 2].split(":");

                    FirstnameSave = Firstname;
                    LastNameSave = StringSplitName[1];
                    ProfitSheetProfitSave = StringSplitProfit[1];
                    CheckNameNum = i + 1;
                   return"Did you mean " + Firstname + " " + StringSplitName[1];
                 }
 
             }
  
             CheckNameNum = 0;
    return "Sorry that person was not found";
}

//AD Method that searches through the employees sheet to find an employee matching the requested name. TODO: Add case handling for employees that are not in the system.  
async function getHoursWorked( person ) {

    var StringSplitHours = "";
    var name = person.split(" ");

    
    const options = {

        uri: 'https://script.google.com/macros/s/AKfycbzRJecRXqinxLQxHRix6F3JmjHso5NyxNgXABdWrDIhwjM4UvY/exec?id=1KE91K1eYxlfSV9gHfI1LBNaCtAS_c-o8rTF92NlEWvg&sheet=employees',
       json: true // Automatically parses the JSON string in the response
    };

    const data = await requestPromise(options);
    var myJSON = JSON.stringify(data);
    var StringSplit = myJSON.split(",");

    var hours = 0;

    for (var i = 0; i < StringSplit.length; i++) {
        if (StringSplit[i].toUpperCase().includes(name[0].toUpperCase()) == true && StringSplit[i+1].toUpperCase().includes(name[1].toUpperCase()) == true){
            StringSplitHours = StringSplit[i + 2].split(":");
	    StringSplitHours = StringSplitHours[1];
	    hours = StringSplitHours;
        }

    }

    return person + " worked " + hours + " hours.";

}

//AD 4/04/2020 Method to tally the amount of a inventory product that we have.
async function getInventoryTotal( foodname ) {

    var StringSplitInventory = "";
    const options = {

        uri: 'https://script.google.com/macros/s/AKfycbzRJecRXqinxLQxHRix6F3JmjHso5NyxNgXABdWrDIhwjM4UvY/exec?id=1KE91K1eYxlfSV9gHfI1LBNaCtAS_c-o8rTF92NlEWvg&sheet=Inventory',
       json: true // Automatically parses the JSON string in the response
    };

    const data = await requestPromise(options);
    var myJSON = JSON.stringify(data);
    var StringSplit = myJSON.split(",");

    var total = 0;
    
    for (var i = 0; i < StringSplit.length; i++) {
        if (StringSplit[i].includes(foodname) == true){	    
	    StringSplitInventory =StringSplit[i + 1].split(":");
            StringSplitInventory = StringSplitInventory[1];
	    total = total + parseInt(StringSplitInventory);
	    //return "We have " + StringSplitInventory + " " + foodname + " left";
	}
	
    }

    if(total == 0){
	return "We do not have any " + foodname;
    } else {
	return "We have " + total + " " + foodname + " left.";    
    }
    
}


//FAH 3/25/2020: searchers through the ezpense type  in exepnses sheet and tells you the total amount of money spent on expense type
async function getSerachExpensetype( Expenstype) {

    var StringSplitExpenseTypePrice = "";
    var PriceSpent = 0;
    
    const options = {
       
        uri: 'https://script.google.com/macros/s/AKfycbzRJecRXqinxLQxHRix6F3JmjHso5NyxNgXABdWrDIhwjM4UvY/exec?id=1KE91K1eYxlfSV9gHfI1LBNaCtAS_c-o8rTF92NlEWvg&sheet=expenses',
       json: true // Automatically parses the JSON string in the response
    };
    const data = await requestPromise(options);
    var myJSON = JSON.stringify(data);
    var StringSplit = myJSON.split(",");
    console.log( myJSON);
     for (var i = 0; i < StringSplit.length; i++) {

        if (StringSplit[i].includes(Expenstype) == true && StringSplit[i].includes('ExpenseType') == true){
           
            StringSplitExpenseTypePrice =StringSplit[i + 1].split(":");
            PriceSpent = PriceSpent + parseInt(StringSplitExpenseTypePrice[1]);
        }
             }

             PriceSpent = PriceSpent * -1;

    return "The amount that was spent on "+ Expenstype + " supplies was, " + PriceSpent + " dollars";
}





module.exports.app = app;