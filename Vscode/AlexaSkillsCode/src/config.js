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
            spreadsheetId: '1GemD0R-AkjLy1EltDUmyBJaSc5LGv4riNhWHhgh69Y8',
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