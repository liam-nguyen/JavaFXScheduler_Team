package javafxscheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.stage.Stage;
//import jfxtras.scene.control.CalendarPicker;

public class FXMLMainCalendarController implements Initializable {

    /*
    * All items in Main Calendar Scene
    */
    @FXML private Label currentUsernameInfo; 
    @FXML private DatePicker apptDatePicker;
    @FXML private TextField apptNameTextField;
    @FXML private ComboBox startTimeHourComboBox; 
    @FXML private ComboBox startTimeMinComboBox; 
    @FXML private ComboBox endTimeHourComboBox; 
    @FXML private ComboBox endTimeMinComboBox;
    @FXML private Button saveApptButton;
    
            
        //Items in the Menu
    @FXML private MenuBar menuBar; 
    @FXML private Menu accountMenu; 
        @FXML private MenuItem changeUsernameMenuItem; 
        @FXML private MenuItem changePasswordMenuItem; 
        @FXML private MenuItem accountDetailMenuItem; 
        @FXML private MenuItem logoutMenuItem;
    @FXML private Menu appointmentMenu;
        @FXML private MenuItem makeAppointmentMenuItem;
        @FXML private MenuItem cancelAppointmentMenuItem;
        @FXML private MenuItem changeAppointmentMenuItem;
    @FXML private Menu settingMenu; 
        @FXML private MenuItem setCalenarRangeMenuItem;
        @FXML private MenuItem setCalendarColorMenuItem;
    @FXML private Menu helpMenu; 
   
        //Items relating to Calendar
    @FXML private Label currentMonthLabel;
    @FXML private Button previousMonthButton;
    @FXML private Button nextMonthButton;
    @FXML private Button todayButton;
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
    /*
     * Other fields
     */
    private User signedInUser = new User(); 
    private YearMonth currentYearMonth;
    private YearMonth todayYearMonth;

    /* Constructor */
    public FXMLMainCalendarController() {
        this.dayArray = new ArrayList<>();
    }
    
    /************************************** VISUAL **************************************/
    /**
     * When this method is called from previous scene, this method will initialize some fields 
     * in the Main Calendar scenes.
     * @param passingUser: User Object : current logged in user. 
     */
    public void initializeMainCalendar (User passingUser) 
    {
        //Initialize data.
        signedInUser.setUsername(passingUser.getUsername());
        signedInUser.setPassword(passingUser.getPassword());
        todayYearMonth = YearMonth.from(LocalDate.now());
        
        //Initialize Scene elements.
        String[] hourList = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12"};
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
     * This method verifies hour and minute input.
     * @param number: the value of hour or minute.
     * @param type: "h" for hour and "m" for minute.
     * @return boolean: true if valid. 
     */
    public boolean timeVerification (int number, String type) {
        boolean valid = false; 
        if (type.equals("h") && 0 <= number && number <= 24)  {
            return valid = true; 
        }
        else if (type.equals("m") && 0 <= number && number <= 60) {
            return valid = true; 
        }
        else 
            return false; 
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
                populateExistingEvents(rs.getDate("date").toLocalDate().getDayOfMonth(), rs.getString("event_name"));
            }
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
     
    /************************************** CLICK EVENTS **************************************/
    /**
     * This method is called when saveButton is clicked.
     */
    public void saveButtonPushed() {
        LocalDate apptLocalDate = apptDatePicker.getValue();
        String apptName = apptNameTextField.getText();
        startTimeHourComboBox.getSelectionModel().getSelectedIndex();

        //Save event infomation into Events table
        try {
        DatabaseHandler db = new DatabaseHandler();
                db.connect_CALENDAR();
                PreparedStatement pstmt;
                String query; 
                
                query = "INSERT INTO EVENTS (date, event_name, start_time, end_time, fk_username) "
                            + "VALUES (?, ?, ?, ?, ?)";
                    pstmt = db.conn.prepareStatement(query);
                    pstmt.setDate(1, java.sql.Date.valueOf(apptLocalDate));
                    pstmt.setString(2, apptNameTextField.getText());
                    pstmt.setString(3, startTimeHourComboBox.getValue() + ":" + startTimeMinComboBox.getValue() + ":" + "00");
                    pstmt.setString(4, endTimeHourComboBox.getValue() + ":" + endTimeMinComboBox.getValue() + ":" + "00");
                    pstmt.setString(5, signedInUser.getUsername());
                    pstmt.executeUpdate();
                    
                    //Notify user about successful registration
                    Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
                    confirmationAlert.setContentText("Successful Registration");
                    confirmationAlert.getButtonTypes().remove(1);
                    confirmationAlert.showAndWait();
                    
                    //Set the event into the calendar
                    setCalToDatePicker();
                    
                    } catch (SQLException e) {
                        System.err.println(e.getMessage() + "--- setup calendar table");
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
            acController.initCurrentUser(signedInUser);
            acController.fillInSignedInUserData();
            acController.populateUserInformation();
            
            //This line gets stage informaion
            Stage window = (Stage)((Node)menuBar).getScene().getWindow();
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
    public void changeUsernameMenuItemPushed(ActionEvent event) {
        try {
            TextInputDialog changeNameDialog = new TextInputDialog();
            changeNameDialog.setTitle("Username Change");
            changeNameDialog.setContentText("Please enter your new username");
            //Get response value
            Optional<String> changeNameresult = changeNameDialog.showAndWait();
            //If there is an input
            if(!changeNameDialog.getEditor().getText().equals("")) {
                //Get alert confirmation to make sure the user wants to the change. 
                Alert alert = new Alert(AlertType.CONFIRMATION); 
                alert.setTitle("Username change confirmation");
                alert.setContentText("Are you sure?");
                Optional<ButtonType> confirmationResult = alert.showAndWait();
                //If User clicks OK
                if (confirmationResult.get() == ButtonType.OK) {
                    DatabaseHandler db = new DatabaseHandler();
                    db.connect_CALENDAR();
                    String query = "UPDATE USERS SET USERNAME = ? WHERE USERNAME=? ";
                    PreparedStatement pstmt;
                    pstmt = db.conn.prepareStatement(query);
                    pstmt.setString(1, changeNameresult.get());
                    pstmt.setString(2, signedInUser.getUsername());
                    pstmt.executeUpdate();
                }
                //If User click Cancel
                else {changeNameDialog.close();}
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
            Optional<String> changeNameresult = changePassDialog.showAndWait();
             //If there is an input
            if(!changePassDialog.getEditor().getText().equals("")) {
                Alert alert = new Alert(AlertType.CONFIRMATION); 
                alert.setTitle("Password change confirmation");
                alert.setContentText("Are you sure?");
                Optional<ButtonType> confirmationResult = alert.showAndWait();
                DatabaseHandler db = new DatabaseHandler();
                if (confirmationResult.get() == ButtonType.OK) {
                    db.connect_CALENDAR();
                    String query = "UPDATE USERS SET PASSWORD = ? WHERE USERNAME=? ";
                    PreparedStatement pstmt;
                    pstmt = db.conn.prepareStatement(query);
                    pstmt.setString(1, changeNameresult.get());
                    pstmt.setString(2, signedInUser.getUsername());
                    pstmt.executeUpdate();
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
     * ?????
     * @param event 
     */
    public void tableViewClicked(MouseEvent event) {
        if (event.getPickResult().getIntersectedNode().getParent().getId() != null) {

        int year = currentYearMonth.getYear();
        int month = currentYearMonth.getMonthValue();
        int index = Integer.valueOf(event.getPickResult().getIntersectedNode().getParent().getId());
        int day = Integer.valueOf(dayArray.get(index).getText());
        
        if (index <= 6 && Integer.valueOf(dayArray.get(index).getText()) > 23) {
            //currentYearMonth = currentYearMonth.minusMonths(1);
        }
        else {
        apptDatePicker.setValue(LocalDate.of(year, month, day));
        }
        }  
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
