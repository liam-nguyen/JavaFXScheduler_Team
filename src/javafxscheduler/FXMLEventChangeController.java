/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxscheduler;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLEventChangeController implements Initializable {
    /* Elements */
    @FXML private Label EventDateLabel;
    @FXML private Label EventNameLabel; 
    @FXML private Label EventStartTimeLabel; 
    @FXML private Label EventEndTimeLabel;
    @FXML private Label statusLabel; 
    @FXML private TextField EventDateTextField;
    @FXML private TextField EventNameTextField; 
    @FXML private TextField EventStartTimeTextField; 
    @FXML private TextField EventEndTimeTextField;
    @FXML private Button EventDateButton;
    @FXML private Button EventNameButton; 
    @FXML private Button EventStartTimeButton; 
    @FXML private Button EventEndTimeButton;
   
    /* Other data */
    private Event currentEvent; 
    
    /* Pass the event from previous scene to this scene */
    public void initializeEvent (String date, String name, String start, String end, String username) {
        currentEvent = new Event(date, name, start, end, username); 
        /* Update labels */
        EventDateLabel.setText(currentEvent.getEventDate());
        EventNameLabel.setText(currentEvent.getEventName());
        EventStartTimeLabel.setText(currentEvent.getRealStartTime());
        EventEndTimeLabel.setText(currentEvent.getRealEndTime());
    }
    
    /**
     * This method is to change Name. 
     */
    public void changeNameButton () {
            String newName = EventNameTextField.getText();
            
            DatabaseHandler db = new DatabaseHandler();
        try {
            db.connect_CALENDAR();
            String query = "UPDATE EVENTS SET event_name = ? WHERE "
                    + "event_name = ? AND date = ? AND start_time = ? AND end_time = ? and fk_username = ?";
            PreparedStatement pstmt;
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, newName);
            pstmt.setString(2, currentEvent.getEventName());
            pstmt.setString(3, currentEvent.getEventDate());
            pstmt.setString(4, currentEvent.getStartTime());
            pstmt.setString(5, currentEvent.getEndTime());
            pstmt.setString(6, currentEvent.getFkUserName());
            pstmt.executeUpdate();
            
            /* Update internal event */
            currentEvent.setEventName(newName);
            
            /* Update Status Label */
            statusLabel.setText("Name Changed Sucessfully.");
            
            /* Update Label */
            EventNameLabel.setText(newName);
            
            /* Close connection */
             db.close_JDBC();
        } catch (SQLException ex) {
            System.out.println("changeNameButton fail: + " + ex);
        }
    }
    
    /**
     * This method is to change Name. 
     */
    public void dateChangeButton () {
            String newDate = EventDateTextField.getText();
            /* Get LocalDate from String EventDate from Event e */
            String eDateString = newDate; 
            String[] parseDateString = eDateString.split("-");
            LocalDate eLocalDate = LocalDate.of(Integer.parseInt(parseDateString[0]), Integer.parseInt(parseDateString[1]), 
                Integer.parseInt(parseDateString[2])); 
            /* Check if the new date is conflicted with any other events on that new date*/
            boolean conflicted = Event.apptConflict(currentEvent.getFkUserName(), eLocalDate, 
                    Integer.parseInt(currentEvent.getStartTime()), Integer.parseInt(currentEvent.getEndTime()));
            
            /* If so, error popup */
            if (conflicted == true) {
                //Get alert confirmation to make sure the user wants to the change. 
                Alert alert = new Alert(Alert.AlertType.ERROR); 
                alert.setContentText("Conflicted with Existing Events");
                alert.showAndWait();
            }
            /* Change the database */
            else {
                DatabaseHandler db = new DatabaseHandler();
                try {
                db.connect_CALENDAR();
                String query = "UPDATE EVENTS SET date = ? WHERE "
                        + "event_name = ? AND date = ? AND start_time = ? AND end_time = ? and fk_username = ?";
                PreparedStatement pstmt;
                pstmt = db.conn.prepareStatement(query);
                pstmt.setString(1, newDate);
                pstmt.setString(2, currentEvent.getEventName());
                pstmt.setString(3, currentEvent.getEventDate());
                pstmt.setString(4, currentEvent.getStartTime());
                pstmt.setString(5, currentEvent.getEndTime());
                pstmt.setString(6, currentEvent.getFkUserName());
                pstmt.executeUpdate();

                /* Update internal event */
                currentEvent.setEventDate(newDate);

                /* Update Status Label */
                statusLabel.setText("Date Changed Sucessfully.");

                /* Update Label */
                EventDateLabel.setText(newDate);

                /* Close connection */
             db.close_JDBC();
             
                } catch (SQLException ex) {
                    System.out.println("dateChangeButton fail: + " + ex);
                }
            }
    }
    
    /**
     * This method is to change Name. 
     */
    public void startTimeChangeButton () {
        String newStartTime = EventStartTimeTextField.getText();
        /* Convert user's input into minutes */
        String newStartTimeInMinute = Event.convertToOnlyMinutes(newStartTime); 
        /* Get LocalDate from String EventDate from Event e */
        String eDateString =  currentEvent.getEventDate(); 
        String[] parseDateString = eDateString.split("-");
        LocalDate eLocalDate = LocalDate.of(Integer.parseInt(parseDateString[0]), Integer.parseInt(parseDateString[1]), 
            Integer.parseInt(parseDateString[2])); 

        /* Remove the event from database */ 
        DatabaseHandler db = new DatabaseHandler();
        try {    
            db.connect_CALENDAR();
            String query = "DELETE FROM EVENTS WHERE "
                    + "event_name = ? AND date = ? AND start_time = ? AND end_time = ? and fk_username = ?";
            PreparedStatement pstmt;
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, currentEvent.getEventName());
            pstmt.setString(2, currentEvent.getEventDate());
            pstmt.setString(3, currentEvent.getStartTime());
            pstmt.setString(4, currentEvent.getEndTime());
            pstmt.setString(5, currentEvent.getFkUserName());
            pstmt.executeUpdate();

        /* Check if the new date is conflicted with any other events on that new date*/
        boolean conflicted = Event.apptConflict(currentEvent.getFkUserName(), eLocalDate, 
                Integer.parseInt(newStartTimeInMinute), Integer.parseInt(currentEvent.getEndTime()));

        /* If so, error popup */
        if (conflicted == true) {
            /* Put back the value */
            query = "INSERT INTO EVENTS VALUES (?, ?, ?, ?, ?)";
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, currentEvent.getEventDate());
            pstmt.setString(2, currentEvent.getEventName());
            pstmt.setString(3, currentEvent.getStartTime());
            pstmt.setString(4, currentEvent.getEndTime());
            pstmt.setString(5, currentEvent.getFkUserName());
            pstmt.executeUpdate();

            /* Alert user */
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setContentText("Conflicted with Existing Events");
            alert.showAndWait();
        }
        /* Change the database */
        else {
            query = "INSERT INTO EVENTS VALUES (?, ?, ?, ?, ?)"; 
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(2, currentEvent.getEventName());
            pstmt.setString(1, currentEvent.getEventDate());
            pstmt.setString(3, newStartTimeInMinute);
            pstmt.setString(4, currentEvent.getEndTime());
            pstmt.setString(5, currentEvent.getFkUserName());
            pstmt.executeUpdate();

            /* Update internal event */
            currentEvent.setStartTime(newStartTimeInMinute);

            /* Update Status Label */
            statusLabel.setText("Start Time Changed Sucessfully.");

            /* Update Label */
            EventStartTimeLabel.setText(newStartTime);
        }
        /* Close connection */
         db.close_JDBC();
        } catch (SQLException ex) {
                System.out.println("startTimeChangeButton fail: + " + ex);
        }
    }
    
    /**
     * This method is to change Name. 
     */
    public void endTimeChangeButton () {
        String newEndTime = EventEndTimeTextField.getText();
        /* Convert user's input into minutes */
        String newEndTimeInMinute = Event.convertToOnlyMinutes(newEndTime); 
        /* Get LocalDate from String EventDate from Event e */
        String eDateString =  currentEvent.getEventDate(); 
        String[] parseDateString = eDateString.split("-");
        LocalDate eLocalDate = LocalDate.of(Integer.parseInt(parseDateString[0]), Integer.parseInt(parseDateString[1]), 
            Integer.parseInt(parseDateString[2])); 

        /* Remove the event from database */ 
        DatabaseHandler db = new DatabaseHandler();
        try {    
            db.connect_CALENDAR();
            String query = "DELETE FROM EVENTS WHERE "
                    + "event_name = ? AND date = ? AND start_time = ? AND end_time = ? and fk_username = ?";
            PreparedStatement pstmt;
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, currentEvent.getEventName());
            pstmt.setString(2, currentEvent.getEventDate());
            pstmt.setString(3, currentEvent.getStartTime());
            pstmt.setString(4, currentEvent.getEndTime());
            pstmt.setString(5, currentEvent.getFkUserName());
            pstmt.executeUpdate();

        /* Check if the new date is conflicted with any other events on that new date*/
        boolean conflicted = Event.apptConflict(currentEvent.getFkUserName(), eLocalDate, 
                Integer.parseInt(currentEvent.getStartTime()), Integer.parseInt(newEndTimeInMinute));

        /* If so, error popup */
        if (conflicted == true) {
            /* Put back the value */
            query = "INSERT INTO EVENTS VALUES (?, ?, ?, ?, ?)";
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, currentEvent.getEventDate());
            pstmt.setString(2, currentEvent.getEventName());
            pstmt.setString(3, currentEvent.getStartTime());
            pstmt.setString(4, currentEvent.getEndTime());
            pstmt.setString(5, currentEvent.getFkUserName());
            pstmt.executeUpdate();

            /* Alert user */
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setContentText("Conflicted with Existing Events");
            alert.showAndWait();
        }
        /* Change the database */
        else {
            query = "INSERT INTO EVENTS VALUES (?, ?, ?, ?, ?)"; 
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(2, currentEvent.getEventName());
            pstmt.setString(1, currentEvent.getEventDate());
            pstmt.setString(3, currentEvent.getStartTime());
            pstmt.setString(4, newEndTimeInMinute);
            pstmt.setString(5, currentEvent.getFkUserName());
            pstmt.executeUpdate();

            /* Update internal event */
            currentEvent.setEndTime(newEndTimeInMinute);

            /* Update Status Label */
            statusLabel.setText("Start Time Changed Sucessfully.");

            /* Update Label */
            EventEndTimeLabel.setText(newEndTime);
        }
        /* Close connection */
         db.close_JDBC();
        } catch (SQLException ex) {
                System.out.println("endTimeChangeButton fail: + " + ex);
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
