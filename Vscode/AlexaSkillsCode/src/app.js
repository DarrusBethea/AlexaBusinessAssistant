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

app.use(
    new Alexa(),
    new GoogleAssistant(),
    new JovoDebugger(),
    new FileDb(),
    new GoogleSheetsCMS
);


// ------------------------------------------------------------------
// APP LOGIC
// ------------------------------------------------------------------

app.setHandler({
    LAUNCH() {
        return this.toIntent('HelloWorldIntent');
    },

    HelloWorldIntent() {
        this.ask(this.t('welcome.speech'), 'Please tell me your name.');
      
    },

    TotalProfitsIntent() {
       this.ask(this.t('get.total'));
    
    },


    MyNameIsIntent() {
        this.tell('Hey ' + this.$inputs.name.value + ', nice to meet you!');
    },
ls
    getQuarterOneIntent() {
        this.ask(this.t('get.quarterOne'));
     
     },

     getQuarterTwoIntent() {
        this.ask(this.t('get.quarterTwo'));
     

     },
     
     getQuarterThreeIntent() {
        this.ask(this.t('get.quarterThree'));
     

     },

     getQuarterFourIntent() {
        this.ask(this.t('get.quarterFour'));
     
     },

});

module.exports.app = app;
