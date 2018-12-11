/**
 * This class is a controller for the scene Registration. 
 */
package javafxscheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLRegistrationController implements Initializable {
    @FXML private Label registrationLabel; 
    @FXML private Label firstNameWarningLabel; 
    @FXML private Label lastNameWarningLabel; 
    @FXML private Label usernameWarningLabel;
    @FXML private Label passwordWarningLabel; 
    @FXML private Label emailWarningLabel; 
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField serviceProviderTextField;
    @FXML private Button registerButton; 
    @FXML private Button backButton; 
    
    
    /**
     * This method goes back to the LogIn Scene. 
     * @param event 
     */
    public void backButtonPushed (ActionEvent event) { 
        try {
            /* Switch to Login Scene */
            Parent mainCalendarParent = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
            Scene mainCalendarScene = new Scene(mainCalendarParent);
            
            /* Display the scene */
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainCalendarScene);
            window.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * When this method is called, it does the following: 
     * Notify user if any text field is left empty,
     * Then save data into the database
     * Then switch to Login Scene if successful. 
     * @param event: mouse click.
     */
    public void registerButtonPushed (ActionEvent event) { 
        boolean invalidInput = false; //Check if all the required inputs are filled. 
        
        /* Check if any field is empty */
        if(firstNameTextField.getText().isEmpty()) {
            firstNameWarningLabel.setText("First name can't be empty.");
            firstNameWarningLabel.setVisible(true);
            invalidInput = true;
        }
        else if (lastNameTextField.getText().isEmpty()) {
            lastNameWarningLabel.setText("Last name can't be empty.");
            lastNameWarningLabel.setVisible(true);
            invalidInput = true;
        }
        else if (usernameTextField.getText().isEmpty()) {
            usernameWarningLabel.setText("Username can't be empty.");
            usernameWarningLabel.setVisible(true);
            invalidInput = true;
        }
        else if (passwordTextField.getText().isEmpty()) {
            passwordWarningLabel.setText("Password can't be empty.");
            passwordWarningLabel.setVisible(true);
            invalidInput = true;
        }
        else if (emailTextField.getText().isEmpty()) {
            emailWarningLabel.setText("Email can't be empty.");
            emailWarningLabel.setVisible(true);
            invalidInput = true;
        }
        
        /* If inputs are all valid */
        if (!invalidInput) {
            try { 
                /* Connect to the database */
                DatabaseHandler db = new DatabaseHandler();
                db.connect_CALENDAR();
                PreparedStatement pstmt;
                String query; 
                
                boolean usedEmail, usedUsername; //check if email or username is used. 
                
                /** Check if email is already in the database. */
                query = "SELECT * FROM USERS WHERE EMAIL=?";
                pstmt = db.conn.prepareStatement(query); 
                pstmt.setString(1, emailTextField.getText());
                ResultSet rs = pstmt.executeQuery(); 
                if (rs.next()) {
                    usedEmail = true; 
                }
                else 
                    usedEmail = false; 
                
                /* Alert if email is already in the system. */
                if (usedEmail == true) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Email found");
                    errorAlert.setContentText("Please choose another email or login.");
                    errorAlert.showAndWait();
                }
                
                /**
                 * Check if username is already in the system.
                 */
                query = "SELECT * FROM USERS WHERE USERNAME=?";
                pstmt = db.conn.prepareStatement(query); 
                pstmt.setString(1, usernameTextField.getText());
                rs = pstmt.executeQuery(); 
                if (rs.next()) {
                    //Found existing user in the database based on Email.
                    usedUsername = true; 
                }
                else 
                    usedUsername = false; 
                
                /* Alert if email is already in the system. */
                if (usedUsername == true) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Username found");
                    errorAlert.setContentText("Please choose another username or login.");
                    errorAlert.showAndWait();
                }
                
                /* New User */
                if (usedEmail == false && usedUsername == false) {
                    query = "INSERT INTO USERS (first_name, last_name, username, password, email, phone, preference, reminderTime, provider, calendar_color) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pstmt = db.conn.prepareStatement(query);
                    pstmt.setString(1, firstNameTextField.getText());
                    pstmt.setString(2, lastNameTextField.getText());
                    pstmt.setString(3, usernameTextField.getText());
                    pstmt.setString(4, passwordTextField.getText());
                    pstmt.setString(5, emailTextField.getText());
                    pstmt.setString(6, phoneTextField.getText());
                    pstmt.setString(7, "email");
                    pstmt.setString(8, "30");
                    pstmt.setString(9, "NULL");
                    pstmt.setString(10, "#ffffff");
                    pstmt.executeUpdate();
                    
                    /* Notify user about successful registration */
                    Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
                    confirmationAlert.setContentText("Successful Registration");
                    confirmationAlert.getButtonTypes().remove(1);
                    confirmationAlert.showAndWait();
                     
                    /* Close connection */
                    db.close_JDBC();
                    
                    /* Switch to Login Scene */ 
                    Parent mainCalendarParent = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
                    Scene mainCalendarScene = new Scene(mainCalendarParent);
                    
                    /* Display */
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(mainCalendarScene);
                    window.show();
                } 
            } catch (SQLException | IOException ex) {
                Logger.getLogger(FXMLRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
