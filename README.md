# JavaFXScheduler_Team

The libraries used in this project are: 

1) mysql-connector-java-8.0.13.jar - Platform Independent - https://dev.mysql.com/downloads/connector/j/
- This is to connect to mySQL database.
- Store user's information and appointments.
2) Apache POI 4.0.1 - https://poi.apache.org/download.html
- This it generate excel file for import and export calendar events.
3) Javax Mail 1.6.2 - https://javaee.github.io/javamail/#Download_JavaMail_Release
- This is to send email reminder to user. 

The project was written in Javafx FXML style.

Note: 
- After the user logs in, the program will run a daemon thread to get appointments of today 
and check if any event is within  user specified reminder time to send email. 
This thread will shut down when the main program shuts down. 
- The program is not as stable as expected. 

Future Improvements: 
- A master controller for switching scene would make this code much more stable. 
In this build, majority of scene switching is accomplished by getting controller in the previous scene. 
From my observation, this causes spaghetti code and unstable scene switching. 
- A master controller for data processing. Since there are many data passing between scenes, relying on controller to pass
data is only temporary solution. 
- Database design - the database should be checked to be in third normal form. Due to time constaint, database was mostly basic. 

