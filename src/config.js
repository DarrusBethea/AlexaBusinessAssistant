// ------------------------------------------------------------------
// APP CONFIGURATION
// ------------------------------------------------------------------

module.exports = {
    logging: true,

    cms: {
        GoogleSheetsCMS: {
            spreadsheetId: '1GemD0R-AkjLy1EltDUmyBJaSc5LGv4riNhWHhgh69Y8',
            access: 'public',
            sheets: [
                {
                    name: 'responses',
                    type: 'Responses',
                    position: 1,
                },
                {
                    name: 'pl_year20',
                    position: 2,
                    range: 'A:Z',
               
                },
                {
                    name: 'pl_year19',
                    position: 8,
                    range: 'A:Z',
               
                },
            ]
        }
    },
 
    intentMap: {
       'AMAZON.StopIntent': 'END',
    },
 
    db: {
         FileDb: {
             pathToFile: '../db/db.json',
         }
     },
 };
 