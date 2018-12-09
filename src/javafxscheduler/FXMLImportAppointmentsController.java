
package javafxscheduler;

import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This FXML allows user to choose and import date from an excel file.
 *
 * @author chinhnguyen
 */
public class FXMLImportAppointmentsController implements Initializable {
    /* Fields */
    @FXML private Label existingApptLabel; 
    @FXML private Label newApptLabel; 
    @FXML private TableView<Event> existingApptTableView; 
    @FXML private TableView<Event> newApptTableView; 
    @FXML private Button importButton; 
    @FXML private Label statusLabel; 
    @FXML private Button populateUserEventButton; 
    @FXML private Button importFileButton; 
    @FXML private Button mergeEventButton; 
    
    @FXML private TableColumn eDateColumn;  @FXML private TableColumn eNameColumn; 
    @FXML private TableColumn eStartTimeColumn; @FXML private TableColumn eEndTimeColumn;
    @FXML private TableColumn eUsernameColumn;
    
    @FXML private TableColumn nDateColumn;  @FXML private TableColumn nNameColumn; 
    @FXML private TableColumn nStartTimeColumn; @FXML private TableColumn nEndTimeColumn;
    @FXML private TableColumn nUsernameColumn;
    
    /* Other data */
    private User signedInUser = new User(); 
    private ArrayList<Event> userEventsArrayList;
    private ArrayList<Event> otherEventsArrayList;
    
    
   
    
    
    /************************************** LOGIC **************************************/
    /**
     * This method passes username from previous scene to this scene. 
     */
    public void initializeScene (String username) {
        signedInUser.setUsername(username);
    }
    
        
    /**
     * This method populate the tableview with user's appointments.
     */
    public void populateUserTableView () {
        ApptManipulator apptManipulator = new ApptManipulator(signedInUser.getUsername()); 
        userEventsArrayList = apptManipulator.allEventsOfUser();
        ObservableList<Event> obsEventsArr = FXCollections.observableArrayList(userEventsArrayList); //Tableview uses observableArrayList
        System.out.println("Check List");
        for (int i = 0; i < obsEventsArr.size(); i++) {
             Event tempt = obsEventsArr.get(i); 
             System.out.println(tempt.getEventDate()+ " " + tempt.getEventName() + " " + tempt.getRealStartTime()+ " " + tempt.getRealEndTime()+ " " + tempt.getFkUserName());
        }
        existingApptTableView.setItems(obsEventsArr);
        eDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        eNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("RealStartTime"));
        eEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("RealEndTime"));
        eUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("FkUserName"));
    } 
    
    /**
     * This method populate the tableview with user's appointments.
     */
    public void populateOtherUserTableView () {
        /* Convertint */
        ObservableList<Event> obsEventsArr = FXCollections.observableArrayList(otherEventsArrayList); //Tableview uses observableArrayList
        
        /* Populate table */
        newApptTableView.setItems(obsEventsArr);
        nDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        nNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        nStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("RealStartTime"));
        nEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("RealEndTime"));
        nUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("FkUserName"));
    } 
    
    

    /************************************** ACTION EVENTS **************************************/
    /**
     * This method activates when populate user's events is pushed.
     */
    public void populateUserEventPushed (ActionEvent event) {
        populateUserTableView(); 
    }
    
    /**
     * This method activates when import file is pushed. 
     */
    public void importFilePushed (ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open an Excel file");
        
        Stage newStage = new Stage(); 
        File file = fileChooser.showOpenDialog(newStage); 
        if (file != null) {
            System.out.println("Checking file" + file);

        ApptManipulator apptManipulator = new ApptManipulator();
        
        /* Get an arrayList of events from the file input */
        otherEventsArrayList = apptManipulator.readFile(file);
        
        populateOtherUserTableView(); 
        }
    }
        
    /**
     * This method activates when the merge event button pushed. 
     */

    public void mergeEventButtonPushed (ActionEvent event) {
        /* Get item selected from newApptTableView */
        Event e = newApptTableView.getSelectionModel().getSelectedItem();
        System.out.println("Event e " + e.getEventDate() + " " + e.getEventName() + " " + e.getStartTime() + " " + e.getEndTime() + " " + e.getFkUserName());
        /* Get LocalDate from String EventDate from Event e */
        String eDateString = e.getEventDate(); 
        String[] parseDateString = eDateString.split("-");
        LocalDate eLocalDate = LocalDate.of(Integer.parseInt(parseDateString[0]), Integer.parseInt(parseDateString[1]), Integer.parseInt(parseDateString[2])); 
        System.out.println("mergeEventButtonPushed:LocalDate: " + eLocalDate);

        boolean isNewAppointmentConfliced = Event.apptConflict(signedInUser.getUsername(), eLocalDate, Integer.parseInt(e.getStartTime()), Integer.parseInt(e.getEndTime()));
        System.out.println("mergeEventButtonPushed confliced ? " + isNewAppointmentConfliced);
        /* If the new event is not conflicted */
        if (isNewAppointmentConfliced == false) {
            /* Add to the database */
            try {
                /* Add it into our user's event database */
                //Save event infomation into Events table
                DatabaseHandler db = new DatabaseHandler();
                db.connect_CALENDAR();
                PreparedStatement pstmt;
                String query;
                query = "INSERT INTO EVENTS (date, event_name, start_time, end_time, fk_username) "
                        + "VALUES (?, ?, ?, ?, ?)";
                pstmt = db.conn.prepareStatement(query);
                pstmt.setDate(1, java.sql.Date.valueOf(eLocalDate));
                pstmt.setString(2, e.getEventName() + "-" + e.getFkUserName());
                pstmt.setString(3, String.valueOf(e.getStartTime()));
                pstmt.setString(4, String.valueOf(e.getEndTime()));
                pstmt.setString(5, signedInUser.getUsername());
                pstmt.executeUpdate();
                //Close connection
                db.close_JDBC();
            } catch (SQLException ex) {
                System.out.println("mergeEventButtonPushed Error: " + ex);
            }
            
            /* Refresh the user's tableview */
            populateUserTableView(); 
            
            /* Remove other user's arraylist<event> */
            otherEventsArrayList.remove(e);
            
            /* Refresh the tableview of other user's tableview */
            populateOtherUserTableView(); 
            
            /* Set Label text */
            statusLabel.setText("SUCCESS");
        } 
        else {
            statusLabel.setText("CONFLICTED WITH CURRENT EVENTS....");
        }
    } 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
        
    }    
    
}
