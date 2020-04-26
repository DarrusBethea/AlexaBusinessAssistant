# senior-project-alexa-skill
This project is to make an android app, and Alexa skill that takes data from google sheets.

The purpose of this application is to provide business owners a convenient means to access their company’s financial information and statistics by connecting their phone and Alexa to that information

The android app lays out each sheet inside the app. Telling the user all the information they would need to know. The app also has data visualizations to view your data in different ways.

On the Alexa side, we used the Jovo framework to get info from the google sheets, this would then give us data to work with and let Alexa respond with that data.

# File structure of the project 

# AlexaIntentsUtterances&Slots
In the folder there are Two text files 
The first text file "AlexaIntents&utterances.txt" is what we as a group had in our amazon developer account when creating Alexa utterances

The second folder, "SlotTypesUsed" is the slot types we created in are amazon developer account.

# AndroidAlexaSkillProject 
This folder contains the entire android app, which uses android studio to compile

# Vscode
This folder contains the JavaScript using the Jovo frame work to control the Alexa skills.

# google appscript code

In this folder there are three folders 
# the first folder "alexafunctions"  
stores "alexafunctions.txt" which has various functions that update the sheets and the response sheet. The response sheet allows Alexa to say different responses to the user.

The next file is “WebDevAddEditDelete.txt" allows to take in custom url to add, edit, or delete anything from profits sheet is used for the android app.

The next “grossprofits.txt" is a class to update the gross profits list

The last one “titlepage.txt" was an html file that redirected the user when an item was edited, deleted, or added in a sheet.

# The second folder "jsonlib"
This folder contains a library that we used to turn are sheets into Json. 
The library was created by GitHub user ronaldsmartin and can be found here https://gist.github.com/ronaldsmartin/47f5239ab1834c47088e

# The third folder "mikesfunction"
the first file "titlepage.txt" was an html file that redirected the user when an item was edited deleted or added in a sheet.

The second file "webdevScript.txt" is used for the rest of the sheets to add edit and delete data.

# googlesheetused.pdf
This Is just a pdf of the google sheets we used in this project.
