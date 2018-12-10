package javafxscheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import jfxtras.scene.control.CalendarPicker;

public class FXMLMainCalendarController implements Initializable {

    /*
    * All items in Main Calendar Scene
    */
    @FXML private AnchorPane mainPane; 
    @FXML private ColorPicker colorPicker; 

    @FXML private Label currentUsernameInfo; 
    @FXML private DatePicker apptDatePicker;
    @FXML private DatePicker oneDateApptDatePicker;
    @FXML private TextField apptNameTextField;
    @FXML private ComboBox startTimeHourComboBox; 
    @FXML private ComboBox startTimeMinComboBox; 
    @FXML private ComboBox endTimeHourComboBox; 
    @FXML private ComboBox endTimeMinComboBox;
    @FXML private Button saveApptButton;
    @FXML private Button selectOneDateButton;
    @FXML private Button removeEventButton; 
    @FXML private Button changeEventButton; 
    
            
        //Items in the Menu
    @FXML private MenuBar menuBar; 
    @FXML private Menu accountMenu; 
        @FXML private MenuItem changeEmailMenuItem; 
        @FXML private MenuItem changePasswordMenuItem;
        @FXML private MenuItem changeFirstMenuItem;
        @FXML private MenuItem changeLastMenuItem;
        @FXML private MenuItem accountDetailMenuItem; 
        @FXML private MenuItem logoutMenuItem;
    @FXML private Menu appointmentMenu;
        @FXML private MenuItem exportAllAppointmentMenuItem;
        @FXML private MenuItem importAppointmentMenuItem;
    @FXML private Menu settingMenu; 
        @FXML private MenuItem changeToPhoneMenuItem;
        @FXML private MenuItem changeToEmailMenuItem;
        @FXML private MenuItem setCalendarColorMenuItem;
    @FXML private Menu helpMenu; 
   
    /* Items relating to Calendar */
    @FXML private Label currentMonthLabel;
    @FXML private Button previousMonthButton;
    @FXML private Button nextMonthButton;
    @FXML private Button todayButton;
    @FXML private TableView<Event> oneDayTableView; 
    @FXML private TableColumn oneDayNameTableColumn; 
    @FXML private TableColumn oneDayStartTimeTableColumn; 
    @FXML private TableColumn oneDayEndTimeTableColumn; 
    
    /* Calendar part */
    @FXML private TableView<Event> t1; @FXML private TableView<Event> t2; @FXML private TableView<Event> t3; @FXML private TableView<Event> t4; @FXML private TableView<Event> t5;
    @FXML private TableView<Event> t6; @FXML private TableView<Event> t7; @FXML private TableView<Event> t8; @FXML private TableView<Event> t9; @FXML private TableView<Event> t10;
    @FXML private TableView<Event> t11; @FXML private TableView<Event> t12; @FXML private TableView<Event> t13; @FXML private TableView<Event> t14; @FXML private TableView<Event> t15;    
    @FXML private TableView<Event> t16; @FXML private TableView<Event> t17; @FXML private TableView<Event> t18; @FXML private TableView<Event> t19; @FXML private TableView<Event> t20;    
    @FXML private TableView<Event> t21; @FXML private TableView<Event> t22; @FXML private TableView<Event> t23; @FXML private TableView<Event> t24; @FXML private TableView<Event> t25;    
    @FXML private TableView<Event> t26; @FXML private TableView<Event> t27; @FXML private TableView<Event> t28; @FXML private TableView<Event> t29; @FXML private TableView<Event> t30;
    @FXML private TableView<Event> t31; @FXML private TableView<Event> t32; @FXML private TableView<Event> t33; @FXML private TableView<Event> t34; @FXML private TableView<Event> t35;    
    @FXML private TableColumn<Event, String> d1; @FXML private TableColumn d2; @FXML private TableColumn d3; @FXML private TableColumn d4; @FXML private TableColumn d5;
    @FXML private TableColumn d6; @FXML private TableColumn d7; @FXML private TableColumn d8; @FXML private TableColumn d9; @FXML private TableColumn d10;
    @FXML private TableColumn d11; @FXML private TableColumn d12; @FXML private TableColumn d13; @FXML private TableColumn d14; @FXML private TableColumn d15;
    @FXML private TableColumn d16; @FXML private TableColumn d17; @FXML private TableColumn d18; @FXML private TableColumn d19; @FXML private TableColumn d20;
    @FXML private TableColumn d21; @FXML private TableColumn d22; @FXML private TableColumn d23; @FXML private TableColumn d24; @FXML private TableColumn d25;
    @FXML private TableColumn d26; @FXML private TableColumn d27; @FXML private TableColumn d28; @FXML private TableColumn d29; @FXML private TableColumn d30;
    @FXML private TableColumn d31; @FXML private TableColumn d32; @FXML private TableColumn d33; @FXML private TableColumn d34; @FXML private TableColumn d35;
    private List<TableColumn> dayArray;
    private List<TableView> eventInDayArray = new ArrayList<>();
    
    
    /* Other fields */
    private User signedInUser; 
    private YearMonth currentYearMonth;
    private YearMonth todayYearMonth;
    private ApptManipulator appointmentManipulator;

    /* Constructor */
    public FXMLMainCalendarController() {
        this.dayArray = new ArrayList<>();
    }
    
    /************************************** VISUAL **************************************/
    /**
     * When this method is called from previous scene, this method will initialize some fields 
     * in the Main Calendar scenes.
     */
    public void initializeMainCalendar (String u, String p) 
    {
        //Initialize data.
        signedInUser = new User (u, p);
        
        //Filled in user's data
        fillInSignedInUserData();
        
        todayYearMonth = YearMonth.from(LocalDate.now());
        
        //Initialize Scene elements.
        String[] hourList = new String[] {"12AM","1AM","2AM","3AM","4AM","5AM","6AM","7AM","8AM",
                "9AM","10AM","11AM","12PM","1PM","2PM","3PM","4PM","5PM",
                "6PM","7PM","8PM","9PM","10PM","11PM","12PM"};
        String[] intList = new String[60]; 
        DecimalFormat formatter = new DecimalFormat("00"); //set minutes to two digit
        
        //initialize minutes
        for (int i = 0; i <= 59; i++) 
        {
            intList[i] = formatter.format(i); 
        } 
        
        startTimeHourComboBox.getItems().addAll(hourList);
        startTimeMinComboBox.getItems().addAll(intList);
        endTimeHourComboBox.getItems().addAll(hourList);
        endTimeMinComboBox.getItems().addAll(intList);
        
        /** Create a thread to send email to user */ 

            
        // Run in a second
        final long INTERVAL = 60000; //Every 1 minutes 
        
        /* Email address to send to */
        String sendTo = signedInUser.getEmail();
        if (signedInUser.getPreference().equals("phone")) {
             sendTo = signedInUser.getPhone() + "@tmomail.net"; 
        }
        SendEmail sendEmail = new SendEmail(sendTo);
        
        /* Create a running thread to send the email */
        Runnable runnable = new Runnable() {
            public void run() {
                while(true) {
                    System.out.println("Wake up");
                    /* Get array of events for today */
                    LocalDate now = LocalDate.now();
                    ArrayList <Event> eventsArrayList = getEventArrayList(now); 
                    for (int i = 0; i < eventsArrayList.size(); i++) {
                        System.out.println("Loop #: " + i);
                        Event ev = eventsArrayList.get(i);
                        /* Start time - ReminderTime */
                        int rewindTime = Integer.parseInt(ev.getStartTime()) - Integer.parseInt(signedInUser.getReminderTime());
                        System.out.println("difference " + rewindTime);
                        Calendar rightNow = Calendar.getInstance();
                        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                        int min = rightNow.get(Calendar.MINUTE); 
                        int currentMins = hour * 60 + min; 
                        System.out.println("currentMins " + currentMins);
                        /* Only send reminder if the event hasn't occured yet. */
                        System.out.println("ev.getStartTime(): " + ev.getStartTime());
                        if (currentMins > rewindTime && currentMins < Integer.parseInt(ev.getStartTime())) {
                            System.out.println("About to send ");
                             /* Send email to that email address */
                             String message = "Reminder. You have an appointment. Here is the detail: \n" + ev.getEventName() + " - " + ev.getRealStartTime() + " - " + ev.getRealEndTime();
                             sendEmail.send(message); 
                             System.out.println("Sent Email ");
                        }
                    }
 
                try {
                    System.out.println("Sleep");
                    Thread.sleep(INTERVAL);
                }  catch (InterruptedException e) {	
                     e.printStackTrace();
                }
                }
            }
        };
               
        Thread thread = new Thread(runnable); 
        thread.setDaemon(true);
        thread.start(); 
        
    }
    
    
     /**
     * Populate the labels on the calendar. 
     * @param yearMonth: the current YearMonth of today. Format: yyyy-mm
     */
    public void populateCalendar(YearMonth yearMonth) {
        currentYearMonth = yearMonth;
        
        /* Get the first date of the month. LocalDate format: yyyy-mm--dd */ 
        LocalDate firstDateOfTheMonth = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        
        /* Set the currentMonth Label */
        currentMonthLabel.setText(String.valueOf(firstDateOfTheMonth.getMonth().toString()) + " " + firstDateOfTheMonth.getYear());
        
        /* Decrememnt firstDateOfTheMonth until the date is on the first SUNDAY */
        LocalDate firstDateOnTheCalendar = firstDateOfTheMonth;
        while (!firstDateOnTheCalendar.getDayOfWeek().toString().equals("SUNDAY")) {
            firstDateOnTheCalendar = firstDateOnTheCalendar.minusDays(1);
        }
        
        /* Format the date labels on 35 tableview based on firstDateOnTheCalendar value */
        for (int i = 0; i < 35; i++) {
            String e = Integer.toString(firstDateOnTheCalendar.getDayOfMonth());
            dayArray.get(i).setText(e);
            firstDateOnTheCalendar = firstDateOnTheCalendar.plusDays(1);
        }
        
        clearEventsInCalendar();
        retrieveExistingEvents(yearMonth);
    }
    
    /************************************** LOGIC **************************************/   
     /**
      * This method gets all the events of a day and return an ArrayList of Events
      */
   public ArrayList<Event> getEventArrayList (LocalDate givenDate) {
        ArrayList <Event> eventsArrayList = new ArrayList<>(); 
        try {
            DatabaseHandler db = new DatabaseHandler();
            db.connect_CALENDAR();
            String query = "SELECT * FROM EVENTS WHERE DATE = ? AND FK_USERNAME = ?";
            PreparedStatement pstmt;
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, givenDate.toString());
            pstmt.setString(2, signedInUser.getUsername());
            ResultSet rs = pstmt.executeQuery();
            /* Create an ArrayList to store all the events. */ 
            while (rs.next()) {
                /* Get data from database of the user on a specific date  */
                    //Dealing with date. 
                Date tempDate = rs.getDate("date");
                LocalDate date = tempDate.toLocalDate();
                String dateString =  date.getYear() + "-" + date.getMonth().getValue() + "-" + date.getDayOfMonth();
                    //Other data
                String tempEventName = rs.getNString("event_name"); 
                int tempStartTime = rs.getInt("start_time");
                int tempEndTime = rs.getInt("end_time");
                String tempUserName = rs.getNString("fk_username");
                /* Create an object array to hold all the information */
                Event event = new Event(dateString, tempEventName, 
                    Integer.toString(tempStartTime), Integer.toString(tempEndTime), tempUserName); 
                /* Add that event into the ArrayList */
                eventsArrayList.add(event);
            }
            /* Close connection */
            db.close_JDBC();
        } catch (SQLException ex) {
            System.out.println("selectOneDateButtonClicked Error: " + ex);
        }
        return eventsArrayList;
   }
    /**
     * This method populate data into the signedInUser
     */
    public void fillInSignedInUserData() {
        try {
            /**
             * Get user's data from the database
             */ 
            DatabaseHandler db = new DatabaseHandler();
            db.connect_CALENDAR();
            String query = "SELECT * FROM USERS WHERE USERNAME=?";
            PreparedStatement pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, signedInUser.getUsername());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //initialize User variables
                signedInUser.setFirstName(rs.getString("first_name"));
                signedInUser.setLastName(rs.getString("last_name"));
                signedInUser.setUsername(rs.getString("username"));
                signedInUser.setPassword(rs.getString("password"));
                signedInUser.setEmail(rs.getString("email"));
                signedInUser.setPhone(rs.getString("phone"));
                signedInUser.setPreference(rs.getString("preference"));
                signedInUser.setReminderTime(rs.getString("remindertime"));
                signedInUser.setProvider(rs.getString("provider"));
            }
            /* Close connection */
             db.close_JDBC();
        }
        
        catch (SQLException ex) {
            System.out.println("initCurrentUser error: " + ex);
        }
    }


    //Set the calendar to DatePicker's date
    public void setCalToDatePicker() {
        //Use apptDatePicker.getValue().getmonnth and stuff to set the event to the calendar
        int dayofmonthDP = apptDatePicker.getValue().getDayOfMonth();
        int monthDP = apptDatePicker.getValue().getMonthValue();
        int yearDP = apptDatePicker.getValue().getYear();
        
        //Set calendar label to the DatePicker's month and year
        currentMonthLabel.setText(String.valueOf(apptDatePicker.getValue().getMonth().toString()) + " " + yearDP);
        
        //Set the year to the DatePicker's year
        int futureOrPastYear = yearDP - currentYearMonth.getYear();
        if (futureOrPastYear > 0) { //future year
            currentYearMonth = currentYearMonth.plusYears(futureOrPastYear);
        }
        else { //past year
            currentYearMonth = currentYearMonth.minusYears(-1 * futureOrPastYear);
        }
        
        //Set month to DatePicker's month
        int futureOrPastMonth = monthDP - currentYearMonth.getMonthValue();
        if (futureOrPastMonth > 0) {
            currentYearMonth = currentYearMonth.plusMonths(futureOrPastMonth);
        }
        else {
            currentYearMonth = currentYearMonth.minusMonths(-1 * futureOrPastMonth);
        }
        populateCalendar(currentYearMonth); //refresh calendar to Datepicker's date
        clearEventsInCalendar();
        retrieveExistingEvents(currentYearMonth);
    }
    
    //Clears calendar events
    public void clearEventsInCalendar() {
        
        for (int j = 0; j < 35; j++) {
            eventInDayArray.get(j).getItems().clear();
                }
    }
    
    //Retrieve events from database and calls to populate existing events
    public void retrieveExistingEvents(YearMonth yearMonth) {
        DatabaseHandler eventDB = new DatabaseHandler();
        try {
            String month = yearMonth.getMonth().toString();
            String year = String.valueOf(yearMonth.getYear());
            eventDB.connect_CALENDAR();
            
            LocalDate firstDayOfTheMonth = yearMonth.atDay(1); //date format of first day of the month
            LocalDate lastDayOfTheMonth = yearMonth.atEndOfMonth(); //date format of last day of month 
            String dayRange = "'" + firstDayOfTheMonth + "'" + " AND " + "'" + lastDayOfTheMonth + "'";
            String query = "SELECT * FROM EVENTS WHERE FK_USERNAME=? AND DATE BETWEEN " + dayRange; //Find all events made by user in that month
            PreparedStatement pstmt;
            pstmt = eventDB.conn.prepareStatement(query); 
            pstmt.setString(1, signedInUser.getUsername());
            ResultSet rs = pstmt.executeQuery(); 
            /* All events made by user in a given yearMonth */
            while (rs.next()) { 
                int monthValue = rs.getDate("date").toLocalDate().getDayOfMonth(); 
                String eventName = rs.getString("event_name"); 
                populateExistingEvents(monthValue, eventName);
            }
             /* Close connection */
             eventDB.close_JDBC();
        } 
            catch (SQLException ex) {
                System.out.println("retrieveExistingEvents error: " + ex);
            }
    }

    /** Populate calendar with event using the day and eventName 
     * @param dayOfMonth: the day that has an event with eventName.
     * @param eventName: the names of the events
     */
    public void populateExistingEvents(int dayOfMonth, String eventName) {
        int firstDayOfMonth = 0;
        /* Get the first day of this month */
        while(!"1".equals(dayArray.get(firstDayOfMonth).getText())) { 
                firstDayOfMonth++;
            }
        /* Find the day on the Calendar that matches with the input dayOfMonth and then populate the events into the table */
        for (int i = firstDayOfMonth; i < 35; i++) { 
            //if (dayArray.get(i).getText() == null ? Integer.toString(dayOfMonth) == null : dayArray.get(i).getText().equals(Integer.toString(dayOfMonth))) {
            if (dayArray.get(i).getText().equals(Integer.toString(dayOfMonth))) {
                eventInDayArray.get(i).getItems().add(new Event(eventName));
                break;
           }
        }
    }

    /* This method deletes the events in the user's database */
    public void deleteData() {
        try {
        DatabaseHandler db = new DatabaseHandler();
        db.connect_CALENDAR();
        PreparedStatement pstmt;
        String query; 
        Event ev = t1.getSelectionModel().getSelectedItem();
        for (int i = 0; i < 35; i++) {
            if (!eventInDayArray.get(i).getSelectionModel().isEmpty()) {
                 ev = (Event) eventInDayArray.get(i).getSelectionModel().getSelectedItem();
                 break;
        }
        }
        query = "DELETE FROM EVENTS WHERE event_name = ? AND fk_username=?";
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, ev.getEventName());
            pstmt.setString(2, signedInUser.getUsername());
            pstmt.executeUpdate();

            /* Close connection */
             db.close_JDBC();
             
            } catch (SQLException e) {
                System.err.println(e.getMessage() + "--- setup calendar table");
            }
    }
    
    
    /************************************** CLICK EVENTS **************************************/
    /**
     * This method is called when saveButton is clicked.
     */
    public void saveAppointmentButtonPushed() {
        try {
            /*
            * Check if any required fields are empty.
            */
                //If datepicker is not chosen.
            if (apptDatePicker.getValue() == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Date required");
                alert.showAndWait();
            }
            //If event name is empty.
            else if (apptNameTextField.getText().trim().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Event Name required");
                alert.showAndWait();
            }
            //If event name is empty.
            else if (startTimeHourComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Start Time Required");
                alert.showAndWait();
            }
            //If event name is empty.
            else if (endTimeHourComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("End Time Required");
                alert.showAndWait();
            }
            else {
                /* Get all values from DatePicker */
                LocalDate apptLocalDate = apptDatePicker.getValue();
                String apptName = apptNameTextField.getText();
                int startTime = startTimeHourComboBox.getSelectionModel().getSelectedIndex() * 60 + startTimeMinComboBox.getSelectionModel().getSelectedIndex();
                int endTime = endTimeHourComboBox.getSelectionModel().getSelectedIndex() * 60 + endTimeMinComboBox.getSelectionModel().getSelectedIndex();
                /* Check if the input start and end time are valid */
                    boolean validTime = Event.validTime(startTime, endTime);

                    if (validTime == false) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setContentText("End Time can't be before Start Time");
                        alert.showAndWait();
                    }

                    else {
                        //Checking for appointment conflicts.
                        boolean conflicted = Event.apptConflict(signedInUser.getUsername(), apptLocalDate,startTime, endTime); 
                        if (conflicted == false) {
                            //Save data into database sql
                            //Save event infomation into Events table
                            DatabaseHandler db = new DatabaseHandler();
                            db.connect_CALENDAR();
                            PreparedStatement pstmt;
                            String query; 

                            query = "INSERT INTO EVENTS (date, event_name, start_time, end_time, fk_username) "
                                        + "VALUES (?, ?, ?, ?, ?)";
                            pstmt = db.conn.prepareStatement(query);
                            pstmt.setDate(1, java.sql.Date.valueOf(apptLocalDate));
                            pstmt.setString(2, apptName);
                            pstmt.setString(3, String.valueOf(startTime));
                            pstmt.setString(4, String.valueOf(endTime));
                            pstmt.setString(5, signedInUser.getUsername());
                            pstmt.executeUpdate();
                            //Close connection
                            db.close_JDBC();

                            //Notify user about successful registration
                            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
                            confirmationAlert.setContentText("Successful Registration");
                            confirmationAlert.getButtonTypes().remove(1);
                            confirmationAlert.showAndWait();

                            //Set the event into the calendar
                            setCalToDatePicker(); 
                            
                            /* Close connection */
                            db.close_JDBC();
                        }
                        else {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setContentText("Conflicted time with Existing Events");
                            alert.showAndWait();
                        }
                    }
            }
            
        }  catch (SQLException ex) {
            Logger.getLogger(FXMLMainCalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method gets the account detail scene is chosen.
     */
    public void accountDetailMenuItemPushed(ActionEvent event) {
        try {
            /*
             * Switch to Account Detail Scene.
             */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLAccountDetail.fxml"));
            Parent mainCalendarParent = loader.load();
            Scene accountDetailScene = new Scene(mainCalendarParent);
            
            //Access the controller and call a method
            FXMLAccountDetailController acController = loader.getController();
            
            //Call function in Account Detail Controller 
                //and populate the Scene with user's information
            acController.initCurrentUser(signedInUser.getUsername(), signedInUser.getPassword());
            acController.fillInSignedInUserData();
            acController.populateUserInformation();
            
            //This line gets stage informaion
            //Stage window = (Stage)((Node)menuBar).getScene().getWindow();
            Stage window = new Stage(); 
            window.setScene(accountDetailScene);
            window.show();
            
        }  catch (IOException ex) {
            System.out.println("accountDetailMenuItemPushed method: " + ex);
        } 
    }
    
    /**
     * This method logs user out.
     */
    public void logOutMenuItemPushed(ActionEvent event) {
        try {
            /*
            * Switch to Main Calendar Scene
            */
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(getClass().getResource("FXMLLogin.fxml"));
            Parent loginParent = loader.load();
            Scene mainCalendarScene = new Scene(loginParent);

            //This line gets stage informaion
            Stage window = (Stage)((Node)menuBar).getScene().getWindow();
            window.setScene(mainCalendarScene);
            window.show();
        } 
        catch (IOException ex) {
            System.out.println("User verified error: " + ex);
        } 
    }
    
    /**
     * This method changes username.
     * @param event
     */
    public void changeEmailMenuItemPushed(ActionEvent event) {
        try {
            TextInputDialog changeNameDialog = new TextInputDialog();
            changeNameDialog.setTitle("Email Change");
            changeNameDialog.setContentText("Please enter your new email");
            //Get response value
            changeNameDialog.showAndWait();
            String changeNameresult = changeNameDialog.getEditor().getText(); 
            
            //If there is an input
            if(!changeNameresult.equals("")) {
                //Get alert confirmation to make sure the user wants to the change. 
                Alert alert = new Alert(AlertType.CONFIRMATION); 
                alert.setTitle("Email change confirmation");
                alert.setContentText("Are you sure?");
                Optional<ButtonType> confirmationResult = alert.showAndWait();
                //If User clicks OK
                if (confirmationResult.get() == ButtonType.OK) {
                    DatabaseHandler db = new DatabaseHandler();
                    db.connect_CALENDAR();
                    String query = "UPDATE USERS SET email = ? WHERE username = ? ";
                    PreparedStatement pstmt;
                    pstmt = db.conn.prepareStatement(query);
                    pstmt.setString(1, changeNameresult);
                    pstmt.setString(2, signedInUser.getUsername());
                    pstmt.executeUpdate();
                    /* Update signedInUser email */
                    signedInUser.setEmail(changeNameresult);
                    
                    /* Close connection */
                    db.close_JDBC();
                }
                
            }
            // if there is no input
            else {
                //Get alert confirmation to make sure the user wants to the change. 
                Alert alert = new Alert(AlertType.WARNING); 
                alert.setContentText("Username can't be empty.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println("changeUsernameMenuItemPushed error: " + ex);
        }
   }
    
    /**
     * This method changes password.
     * @param event
     */
    public void changePasswordMenuItemPushed(ActionEvent event) {
        try {
            TextInputDialog changePassDialog = new TextInputDialog();
            changePassDialog.setTitle("Password Change");
            changePassDialog.setContentText("Please enter your new password");
            //Get response value
            changePassDialog.showAndWait();
            String newPassword = changePassDialog.getEditor().getText();            
            //If there is an input
            if(!newPassword.equals("")) {
                Alert alert = new Alert(AlertType.CONFIRMATION); 
                alert.setTitle("Password change confirmation");
                alert.setContentText("Are you sure?");
                Optional<ButtonType> confirmationResult = alert.showAndWait();
                DatabaseHandler db = new DatabaseHandler();
                if (confirmationResult.get() == ButtonType.OK) {
                    db.connect_CALENDAR();
                    String query = "UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?";
                    PreparedStatement pstmt;
                    pstmt = db.conn.prepareStatement(query);
                    pstmt.setString(1, newPassword);
                    pstmt.setString(2, signedInUser.getUsername());
                    pstmt.executeUpdate();
                    
                    /* Update internal user */
                    signedInUser.setPassword(newPassword);
                    
                    /* Close connection */
                    db.close_JDBC();
                }
                else {changePassDialog.close();}
            }
            //If there is no input
            else {
                //Get alert confirmation to make sure the user wants to the change. 
                Alert alert = new Alert(AlertType.WARNING); 
                alert.setContentText("Password can't be empty.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println("changePasswordMenuItemPushed error: " + ex);
        }
   }
    
    /**
     * This method changes password.
     * @param event
     */
    public void changeFirstNameMenuItemPushed(ActionEvent event) {
        try {
            TextInputDialog changeFirstDialog = new TextInputDialog();
            changeFirstDialog.setTitle("First Name Change");
            changeFirstDialog.setContentText("Please enter your new first name");
            //Get response value
            changeFirstDialog.showAndWait();
            String newFirst = changeFirstDialog.getEditor().getText();            
            //If there is an input
            if(!newFirst.equals("")) {
                Alert alert = new Alert(AlertType.CONFIRMATION); 
                alert.setTitle("First name change confirmation");
                alert.setContentText("Are you sure?");
                Optional<ButtonType> confirmationResult = alert.showAndWait();
                DatabaseHandler db = new DatabaseHandler();
                if (confirmationResult.get() == ButtonType.OK) {
                    db.connect_CALENDAR();
                    String query = "UPDATE USERS SET first_name = ? WHERE USERNAME = ?";
                    PreparedStatement pstmt;
                    pstmt = db.conn.prepareStatement(query);
                    pstmt.setString(1, newFirst);
                    pstmt.setString(2, signedInUser.getUsername());
                    pstmt.executeUpdate();
                    
                    /* Update internal user */
                    signedInUser.setFirstName(newFirst);
                }
                
                else {changeFirstDialog.close();}
                
                /* Close connection */
                db.close_JDBC();
            }
            //If there is no input
            else {
                //Get alert confirmation to make sure the user wants to the change. 
                Alert alert = new Alert(AlertType.WARNING); 
                alert.setContentText("First name can't be empty.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println("changeFirstNameMenuItemPushed error: " + ex);
        }
   }
    
       /**
     * This method changes password.
     * @param event
     */
    public void changeLastNameMenuItemPushed(ActionEvent event) {
        try {
            TextInputDialog changeLastDialog = new TextInputDialog();
            changeLastDialog.setTitle("First Name Change");
            changeLastDialog.setContentText("Please enter your new first name");
            //Get response value
            changeLastDialog.showAndWait();
            String newLast = changeLastDialog.getEditor().getText();            
            //If there is an input
            if(!newLast.equals("")) {
                Alert alert = new Alert(AlertType.CONFIRMATION); 
                alert.setTitle("First name change confirmation");
                alert.setContentText("Are you sure?");
                Optional<ButtonType> confirmationResult = alert.showAndWait();
                DatabaseHandler db = new DatabaseHandler();
                if (confirmationResult.get() == ButtonType.OK) {
                    db.connect_CALENDAR();
                    String query = "UPDATE USERS SET last_name = ? WHERE USERNAME = ?";
                    PreparedStatement pstmt;
                    pstmt = db.conn.prepareStatement(query);
                    pstmt.setString(1, newLast);
                    pstmt.setString(2, signedInUser.getUsername());
                    pstmt.executeUpdate();
                    
                    /* Update internal user */
                    signedInUser.setLastName(newLast);
                }
                else {changeLastDialog.close();}
                /* Close connection */
                db.close_JDBC();
            }
            //If there is no input
            else {
                //Get alert confirmation to make sure the user wants to the change. 
                Alert alert = new Alert(AlertType.WARNING); 
                alert.setContentText("Last name can't be empty.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println("changeLastNameMenuItemPushed error: " + ex);
        }
   }
    
    /**
     * This method changes password.
     * @param event
     */
    public void changePhonePreferenceMenuItemPushed(ActionEvent event) {
        try {
            DatabaseHandler db = new DatabaseHandler();
            db.connect_CALENDAR();
            String query = "SELECT PREFERENCE FROM USERS WHERE username = ?";
            PreparedStatement pstmt;
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, signedInUser.getUsername());
            ResultSet rs = pstmt.executeQuery();
            String userPref = ""; 
            while (rs.next()) {
                userPref = rs.getString("preference"); 
            }
            if (!"phone".equals(userPref)) {
                query = "UPDATE USERS SET PREFERENCE = ? WHERE username = ?";
                pstmt = db.conn.prepareStatement(query);
                pstmt.setString(1, "phone");
                pstmt.setString(2, signedInUser.getUsername());
                pstmt.executeUpdate();
                
                /* Change preferences for internal user */
                signedInUser.setPreference("phone");
                
                /* Notify user */
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Successfully update preference.");
                alert.showAndWait();
            }
            
            /* Close connection */
             db.close_JDBC();
        } catch (SQLException ex) {
            System.out.println("changePhonePreferenceMenuItemPushed error: " + ex);
        }
   }
    
    /**
     * This method changes password.
     * @param event
     */
    public void changeEmailPreferenceMenuItemPushed(ActionEvent event) {
        try {
            DatabaseHandler db = new DatabaseHandler();
            db.connect_CALENDAR();
            String query = "SELECT PREFERENCE FROM USERS WHERE username = ?";
            PreparedStatement pstmt;
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, signedInUser.getUsername());
            ResultSet rs = pstmt.executeQuery();
            String userPref = ""; 
            while (rs.next()) {
                userPref = rs.getString("preference"); 
            }
            if (!"email".equals(userPref)) {
                query = "UPDATE USERS SET PREFERENCE = ? WHERE username = ?";
                pstmt = db.conn.prepareStatement(query);
                pstmt.setString(1, "email");
                pstmt.setString(2, signedInUser.getUsername());
                pstmt.executeUpdate();
                
                /* Change preferences for internal user */
                signedInUser.setPreference("email");
                
                /* Notify user */
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Successfully update preference.");
                alert.showAndWait();
            }
            /* Close connection */
             db.close_JDBC();
        } catch (SQLException ex) {
            System.out.println("changePhonePreferenceMenuItemPushed error: " + ex);
        }
   }
    
    /**
     * This method moves the month back by one and 
     * re-populate the calendar with correct date.
     */
    public void previousMonthButtonPushed() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
    }
    
    /**
     * This method moves the month forward by one and 
     * re-populate the calendar with correct date.
     */
    public void nextMonthButtonPushed() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
    }
    
    /**
     * This method displays the current date on the calendar. 
     */
    public void todayButtonPushed() {
        populateCalendar(todayYearMonth);
    }
    
    /**
     * When clicked on table, get the date and set it to the date picker.
     * @param event 
     */
    public void tableViewClicked(MouseEvent event) {
        if (event.getPickResult().getIntersectedNode().getParent().getId() != null) {
            int year = currentYearMonth.getYear();
            int month = currentYearMonth.getMonthValue();
            int tableSelectedIndex = Integer.valueOf(event.getPickResult().getIntersectedNode().getParent().getId()); 
            int day = Integer.valueOf(dayArray.get(tableSelectedIndex).getText());

            if (tableSelectedIndex <= 6 && Integer.valueOf(dayArray.get(tableSelectedIndex).getText()) > 23) {
                //currentYearMonth = currentYearMonth.minusMonths(1);
            }
            else {
            apptDatePicker.setValue(LocalDate.of(year, month, day));
            }
        }  
    }    
    
    public void selectOneDateButtonClicked () {
        LocalDate input = oneDateApptDatePicker.getValue();
        if (input != null) {
            /* Search database all the events on that date */
            try {
                DatabaseHandler db = new DatabaseHandler();
                db.connect_CALENDAR();
                String query = "SELECT * FROM EVENTS WHERE DATE = ? AND FK_USERNAME = ?";
                PreparedStatement pstmt;
                pstmt = db.conn.prepareStatement(query);
                pstmt.setString(1, input.toString());
                pstmt.setString(2, signedInUser.getUsername());
                ResultSet rs = pstmt.executeQuery();
                /* Create an ArrayList to store all the events.
                 * Each event is considered to be an String array.
                 * The format of the String[] is : [date, event_name, start_time, end_time, fk_username] */

                ArrayList <Event> eventsArrayList = new ArrayList<>(); 
                while (rs.next()) {
                    /* Get data from database of the user on a specific date  */
                        //Dealing with date. 
                    Date tempDate = rs.getDate("date");
                    LocalDate date = tempDate.toLocalDate();
                    String dateString =  date.getYear() + "-" + date.getMonth().getValue() + "-" + date.getDayOfMonth();
                        //Other data
                    String tempEventName = rs.getNString("event_name"); 
                    int tempStartTime = rs.getInt("start_time");
                    int tempEndTime = rs.getInt("end_time");
                    String tempUserName = rs.getNString("fk_username");
                    /* Create an object array to hold all the information */
                    Event event = new Event(dateString, tempEventName, 
                        Integer.toString(tempStartTime), Integer.toString(tempEndTime), tempUserName); 
                    /* Add that event into the ArrayList */
                    eventsArrayList.add(event);
                }

                /* Convert ArrayList into ObservableList */
                ObservableList<Event> obsEventsArr = FXCollections.observableArrayList(eventsArrayList); //Tableview uses observableArrayList

                /* Add that array into the tableview */
                oneDayTableView.setItems(obsEventsArr);
                oneDayNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
                oneDayStartTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("RealStartTime"));
                oneDayEndTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("RealEndTime"));
                
                /* Close connection */
                db.close_JDBC();
            } catch (SQLException ex) {
                System.out.println("selectOneDateButtonClicked Error: " + ex);
            } 
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("A date is required!");
            alert.showAndWait();
        }
    } 
    
    /**
     * This method is for removeButton.
     */
    public void removeButtonPushed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText("This action is permanent. Are you sure?");
        Optional<ButtonType> confirmationResult = alert.showAndWait();
        //If User clicks OK
        if (confirmationResult.get() == ButtonType.OK) {
            try {
                DatabaseHandler db = new DatabaseHandler();
                db.connect_CALENDAR();
                PreparedStatement pstmt;
                String query; 
                Event ev = oneDayTableView.getSelectionModel().getSelectedItem();
                query = "DELETE FROM EVENTS WHERE event_name = ? AND fk_username=? "
                        + "AND start_time = ? AND end_time = ?";
                pstmt = db.conn.prepareStatement(query);
                pstmt.setString(1, ev.getEventName());
                pstmt.setString(2, signedInUser.getUsername());
                pstmt.setString(3, ev.getStartTime());
                pstmt.setString(4, ev.getEndTime());
                pstmt.executeUpdate();

                /* Update the tableview */
                selectOneDateButtonClicked ();
                
                /* Close connection */
                db.close_JDBC();
            } catch (SQLException ex) {
                System.err.println("removeButtonPushed: " + ex);
            }
        }
    }
    
    
    public void exportAllMenuItemPushed () {
        LocalDate input = oneDateApptDatePicker.getValue(); 
        /* Create an object ApptManipulator in this MainCalendar */
        appointmentManipulator = new ApptManipulator(signedInUser.getUsername()); 
        appointmentManipulator.setEventCollection(appointmentManipulator.allEventsOfUser());
        appointmentManipulator.writeToFile();
    }
    
    public void importAppointmentMenuItemPushed (ActionEvent event) {
        try {
            /*
            * Switch to Import Appointment Scene
            */
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(getClass().getResource("FXMLImportAppointments.fxml"));
            Parent importParent = loader.load();
            Scene importCalendarScene = new Scene(importParent);

            //Access the controller and call a method
            FXMLImportAppointmentsController iaController = loader.getController();


            //Call function in Main Calendar Controller
            iaController.initializeScene(signedInUser.getUsername(), signedInUser.getPassword());

            //This line gets stage informaion
            Stage window = new Stage(); 
            window.setScene(importCalendarScene);
            window.show();
        } catch (IOException ex) {
            System.out.println("Set Import scene error: " + ex);
        }
    }
    
    /**
     * This method is for change event Button. 
     */
    public void changeEventButton () {
        try {
            Event ev = oneDayTableView.getSelectionModel().getSelectedItem();
            
            /*
            * Switch to Import Appointment Scene
            */
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(getClass().getResource("FXMLEventChange.fxml"));
            Parent importParent = loader.load();
            Scene importCalendarScene = new Scene(importParent);

            //Access the controller and call a method
            FXMLEventChangeController evController = loader.getController();
            
            /* Pass event to the other scene */
            
            evController.initializeEvent(ev.getEventDate(), ev.getEventName(), ev.getStartTime(), ev.getEndTime(), ev.getFkUserName());
            //This line gets stage informaion
            Stage window = new Stage(); 
            window.setScene(importCalendarScene);
            window.show();
        } catch (IOException ex) {
            System.out.println("changeEventButton: " + ex);
        }
    }
    
    /**
     * This method is to open colorpicker scene.
     * @param event
     */
    public void colorPickerPushed(ActionEvent event) {
        Color chosen = colorPicker.getValue();
        
        mainPane.setBackground(new Background(new BackgroundFill(chosen, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    /************************************** INITIALIZATION **************************************/
    //create method for retreiving event data and refreshing it everything calendar changes.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
                //Populate the calendar with day numbers
        //Put the tablecolumns into the day array
        dayArray.add(d1); dayArray.add(d2); dayArray.add(d3); dayArray.add(d4); dayArray.add(d5); dayArray.add(d6); dayArray.add(d7);
        dayArray.add(d8); dayArray.add(d9); dayArray.add(d10); dayArray.add(d11); dayArray.add(d12); dayArray.add(d13); dayArray.add(d14);
        dayArray.add(d15); dayArray.add(d16); dayArray.add(d17); dayArray.add(d18); dayArray.add(d19); dayArray.add(d20); dayArray.add(d21);
        dayArray.add(d22); dayArray.add(d23); dayArray.add(d24); dayArray.add(d25); dayArray.add(d26); dayArray.add(d27); dayArray.add(d28);
        dayArray.add(d29); dayArray.add(d30); dayArray.add(d31); dayArray.add(d32); dayArray.add(d33); dayArray.add(d34); dayArray.add(d35);
        
        //Put tableviews into the eventInDay array
        eventInDayArray.add(t1); eventInDayArray.add(t2); eventInDayArray.add(t3); eventInDayArray.add(t4); eventInDayArray.add(t5);
        eventInDayArray.add(t6); eventInDayArray.add(t7); eventInDayArray.add(t8); eventInDayArray.add(t9); eventInDayArray.add(t10);
        eventInDayArray.add(t11); eventInDayArray.add(t12); eventInDayArray.add(t13); eventInDayArray.add(t14); eventInDayArray.add(t15);
        eventInDayArray.add(t16); eventInDayArray.add(t17); eventInDayArray.add(t18); eventInDayArray.add(t19); eventInDayArray.add(t20);
        eventInDayArray.add(t21); eventInDayArray.add(t22); eventInDayArray.add(t23); eventInDayArray.add(t24); eventInDayArray.add(t25);
        eventInDayArray.add(t26); eventInDayArray.add(t27); eventInDayArray.add(t28); eventInDayArray.add(t29); eventInDayArray.add(t30);
        eventInDayArray.add(t31); eventInDayArray.add(t32); eventInDayArray.add(t33); eventInDayArray.add(t34); eventInDayArray.add(t35);
        
        //clear all the tableviews so it doesn't show "No content in table"
        for (int j = 0; j < 35; j++) {
            eventInDayArray.get(j).setPlaceholder(new Label());
                }
        //set eventName to every tableviews
        for (int i = 0; i < 35; i++) {
            dayArray.get(i).setCellValueFactory(new PropertyValueFactory<>("eventName"));
        }
    }    
    
}
