
package javafxscheduler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    @FXML private TableColumn eDateColumn;  @FXML private TableColumn eNameColumn; 
    @FXML private TableColumn eStartTimeColumn; @FXML private TableColumn eEndTimeColumn;
    @FXML private TableColumn eUsernameColumn;
    
    @FXML private TableColumn nDateColumn;  @FXML private TableColumn nNameColumn; 
    @FXML private TableColumn nStartTimeColumn; @FXML private TableColumn nEndTimeColumn;
    @FXML private TableColumn nUsernameColumn;
    
    /* Other data */
    private User signedInUser = new User(); 
    
    /* Put table column into an array */
    private List<TableColumn> currentColList; 
    
    
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
    /*public void populateUserTableView (String username, TableView table) {
        ApptManipulator apptManipulator = new ApptManipulator(username); 
        ArrayList<Event> eventsArrayList = apptManipulator.allEventsOfUser();
        ObservableList<Event> obsEventsArr = FXCollections.observableArrayList(eventsArrayList); //Tableview uses observableArrayList
        System.out.println("Check List");
         for (int i = 0; i < obsEventsArr.size(); i++) {
             Event tempt = obsEventsArr.get(i); 
             System.out.println(tempt.getEventDate()+ " " + tempt.getEventName() + " " + tempt.getRealStartTime()+ " " + tempt.getRealEndTime()+ " " + tempt.getFkUserName());
        }
        table.setItems(obsEventsArr);
        eDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        eNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("RealStartTime"));
        eEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("RealEndTime"));
        eUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("FkUserName"));
    } */ //Delete this
    

    /************************************** ACTION EVENTS **************************************/
    /**
     * This method activates when populate user's events is pushed.
     */
    public void populateUserEventPushed (ActionEvent event) {
        ApptManipulator apptManipulator = new ApptManipulator(signedInUser.getUsername()); 
        ArrayList<Event> eventsArrayList = apptManipulator.allEventsOfUser();
        ObservableList<Event> obsEventsArr = FXCollections.observableArrayList(eventsArrayList); //Tableview uses observableArrayList
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
        ArrayList<Event> otherEventsArrayList = apptManipulator.readFile(file);
        
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
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
        
    }    
    
}
