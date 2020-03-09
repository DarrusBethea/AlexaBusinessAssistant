
//Fah 3/7/2020
function Totalprofits(){
var runningtotal = 0;
var profitsheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("profits");
var responsesheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("responses");
if (profitsheet != null) {

var data = profitsheet.getDataRange().getValues();

for (var i = 1; i < data.length; i++) {

runningtotal= parseInt(runningtotal)+data[i][2];

}

 return responsesheet.getRange("B5").setValue("your total profit is "+runningtotal+ " dollars");
}
}

//FAH 3/7/2020
function updatecalls (){
Totalprofits();
}

// Mike 3/7/2020: calacuate expenses sheet profits
function TotalprofitsExpenses(){
var runningtotal = 0;
var profitsheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("expenses");
var responsesheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("responses");
if (profitsheet != null) {

var data = profitsheet.getDataRange().getValues();

for (var i = 1; i < data.length; i++) {

runningtotal= parseInt(runningtotal)+data[i][2];

}
// TODo add in return collumn
// return responsesheet.getRange("B5").setValue("your total profit is "+runningtotal+ " dollars");
}
}

//AD 03/07/2020
function TotalHoursWorked(){
  var runningtotal = 0;
  var employeesheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("employees");
  var responsesheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("responses");
  if (employeesheet != null) {
    var data = employeesheet.getDataRange().getValues();
    
    for (var i = 1; i < data.length; i++){
      runningtotal = parseInt(runningtotal)+data[i][2];
    }
    
    return responsesheet.getRange("B7").setValue("Total employees hours worked is " + runningtotal + " hours");
    
  }
  
}

//AD 03/07/2020
function TotalInventoryUnits(){
  var runningtotal = 0;
  var inventorysheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("inventory");
  var responsesheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("responses");
  if (inventorysheet != null) {
    var data = inventorysheet.getDataRange().getValues();
    
    for (var i = 1; i < data.length; i++){
      runningtotal = parseInt(runningtotal)+data[i][1];
    }
    
    return responsesheet.getRange("B8").setValue("Total inventory units is " + runningtotal + " units");
    
  }
  
}

//William Geary 03/07/2020

function getQuarterOne(){

  var Q2 = 0;
  var profits = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("profits");
  var responsesheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("responses");
  // Will unsure why but data needs to be set at 02 to get 01
  var Date1 = new Date("2020-01-07");
  var Date2 = new Date("2020-03-12");
  
  
  if (profits != null) {
   Logger.log(Date1);
    Logger.log(Date2);
   var data = profits.getDataRange().getValues();
  for (var i = 1; i < data.length; i++) {

    if (data[i][3] > Date1 && data[i][3] < Date2){
      Q2 = parseInt(Q2) + data[i][2];
   
    }
}

 return responsesheet.getRange("B9").setValue("The total for quarter one is " + Q2 + " dollars");
}
}

//William Geary 03/07/2020

function getQuarterTwo(){

  var Q3 = 0;
  var profits = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("profits");
  var responsesheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("responses");
  var Date1 = new Date("2020-04-02");
  var Date2 = new Date("2020-06-18");
  
  
  if (profits != null) {
   Logger.log(Date1);
    Logger.log(Date2);
   var data = profits.getDataRange().getValues();
  for (var i = 1; i < data.length; i++) {

    if (data[i][3] > Date1 && data[i][3] < Date2){
      Q3 = parseInt(Q3) + data[i][2];
   
    }
}

 return responsesheet.getRange("B10").setValue("The total for quarter two is " + Q3 + " dollars");
}
}

//William Geary 03/07/2020

function getQuarterThree(){

  var Q4 = 0;
  var profits = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("profits");
  var responsesheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("responses");
  var Date1 = new Date("2020-07-08");
  var Date2 = new Date("2020-09-04");
  
  
  if (profits != null) {
   Logger.log(Date1);
    Logger.log(Date2);
   var data = profits.getDataRange().getValues();
  for (var i = 1; i < data.length; i++) {

    if (data[i][3] > Date1 && data[i][3] < Date2){
      Q4 = parseInt(Q4) + data[i][2];
   
    }
}

 return responsesheet.getRange("B11").setValue("The total for quarter three is " + Q4 + " dollars");
}
}

//William Geary 03/07/2020

function getQuarterFour(){

  var Q5 = 0;
  var profits = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("profits");
  var responsesheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("responses");
  var Date1 = new Date("2020-10-08");
  var Date2 = new Date("2020-12-24");
  
  
  if (profits != null) {
   Logger.log(Date1);
    Logger.log(Date2);
   var data = profits.getDataRange().getValues();
  for (var i = 1; i < data.length; i++) {

    if (data[i][3] > Date1 && data[i][3] < Date2){
      Q5 = parseInt(Q5) + data[i][2];
   
    }
}

 return responsesheet.getRange("B12").setValue("The total for quarter four is " + Q5 + " dollars");
}
}