# CoffeeProject
Androdi Studio Project
Welcome to our Android Application Project created by Agron Asani and Yann Jollien ad the HES-SO Valais/Wallis.

The goal of the application is to have an application which provides a management system for Coffe Storage which also includes
the plantation management.

The application is based on the live data architecture model, and is currently working with RoomDatabase.

#Database
As we don't have direct links between the storage and the plantation we decided to create two different DB's for them. In case 
one of them is not working, the app is still running for the other one. Furthermore the whole login process is not based on live data,
we assume the is no need for the process to work with live data, the user never sees the data from the user DB.

#Functionalities
- The user has to create an acount to log in to the application. 
- Add Storage
- Edit Storage
- Delete Storage

- Add Plantation (Addin Storage with qr scanner -- to create a qr code for the application use format Text ("Type",amout,"date))
  example : Arabica,23,30.03.2019
- Edit Plantation
- Delete Plantation

- Change language of the application -- EN to DE
- See profile informations 
- See informations about application

- See some stats about storage and plantation

#Edit
- Welcome notification is working but can't be disabled from the settings. We leave the toggle and the ringtone choser and we wil
  try to work on it for later releases.
- The application is not finished, it's a first release

