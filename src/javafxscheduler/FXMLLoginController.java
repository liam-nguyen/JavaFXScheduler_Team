package javafxscheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FXMLLoginController implements Initializable {
    
    //These items are for Log In Scene
    @FXML private Label signInLabel;
    @FXML private TextField usernameTextField; 
    @FXML private TextField passwordTextField;
    @FXML private Button loginButton; 
    @FXML private Button registerButton;
    @FXML private AnchorPane rootPane;
    
    private YearMonth currentYearMonth;
    
    /**
     * When this method is called, a registration form scene appears.
     * @param event
     */
    public void registerButtonPushed (ActionEvent event) { 
        try {
            /*
            * Switch to Registration
            */
            Parent mainCalendarParent = FXMLLoader.load(getClass().getResource("FXMLRegistration.fxml"));
            Scene mainCalendarScene = new Scene(mainCalendarParent);
            
            //This line gets stage informaion
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            
            window.setScene(mainCalendarScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * When this method is called, username and password are verified
     * If successful, Main Calendar Scene appears
     * If failed, warning scene appears.
     * @param event
     */
    public void loginButtonPushed (ActionEvent event) { 
        String usernameAttempt = usernameTextField.getText();
        String passwordAttempt = passwordTextField.getText();
        /*
        * Check if username or password fields are empty
        */
        if (usernameTextField.getText().isEmpty() || 
                passwordTextField.getText().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Please fill in username and password.");
            errorAlert.showAndWait();
        }
        
        else {
            /*
            * Verify username and password  
            */
            DatabaseHandler userDB = new DatabaseHandler();
            boolean verified = false; 

            try {
                userDB.connect_CALENDAR();
                if (usernameAttempt != null && passwordAttempt != null) {
                    String query = "SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD= ?";
                    PreparedStatement pstmt;
                    pstmt = userDB.conn.prepareStatement(query); 
                    pstmt.setString(1, usernameAttempt);
                    pstmt.setString(2, passwordAttempt);
                    ResultSet rs = pstmt.executeQuery(); 
                    if (rs.next()) {
                        verified = true;
                    }
                }
            } 
            catch (SQLException ex) {
                System.out.println("Verify account error: " + ex);
            }

            //If user is in the database
            try {
                if (verified == true) {
                        /*
                        * Switch to Main Calendar Scene
                        */
                        FXMLLoader loader = new FXMLLoader(); 
                        loader.setLocation(getClass().getResource("FXMLMainCalendar.fxml"));
                        Parent loginParent = loader.load();
                        Scene mainCalendarScene = new Scene(loginParent);
                        
                        //Access the controller and call a method
                        FXMLMainCalendarController mcController = loader.getController();
                                                
                        //Call function in Main Calendar Controller
                        mcController.initializeMainCalendar(usernameAttempt, passwordAttempt);
                        mcController.populateCalendar(YearMonth.now());
                        
                        //This line gets stage informaion
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(mainCalendarScene);
                        window.show();
                    }
            
            //If user is not in database, alert box appears.
                else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Wrong Username or Password");
                        errorAlert.setContentText("Please login with different username or password.");
                        errorAlert.showAndWait();
                    } 
                } catch (IOException ex) {
                    System.out.println("User verified error: " + ex);
                }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
