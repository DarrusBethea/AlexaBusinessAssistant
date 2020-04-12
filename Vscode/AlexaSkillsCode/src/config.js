// ------------------------------------------------------------------
// APP CONFIGURATION
// ------------------------------------------------------------------

module.exports = {
    logging: true,
 
    intentMap: {
       'AMAZON.StopIntent': 'END',
    },
 
    db: {
         FileDb: {
             pathToFile: '../db/db.json',
         }
     },
   
     cms: {
        GoogleSheetsCMS: {
            spreadsheetId: '1KE91K1eYxlfSV9gHfI1LBNaCtAS_c-o8rTF92NlEWvg',
            access: 'public',
            sheets: [
                {
                    name: '<responses>',
                    type: 'Responses',
                    position: 1,
                },
                         // disable caching for this sheet
            ],
            caching: false,                 // disable caching for all sheets
        }
    },

    // ...

};