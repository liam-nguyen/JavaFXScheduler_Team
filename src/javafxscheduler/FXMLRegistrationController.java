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
    @FXML private Button registerButton; 
    @FXML private Button backButton; 
    
    /**
     * When this method is called, Login Scene appears.
     */
    public void backButtonPushed (ActionEvent event) { 
        try {
            /*
            * Switch to Main Calendar Scene
            */
            Parent mainCalendarParent = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
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
     * When this method is called, it does the following: 
     * Notify user if any text field is left empty,
     * Then save data into the database
     * Then switch to Login Scene if successful. 
     * @param event
     */
    public void registerButtonPushed (ActionEvent event) { 
        boolean invalidInput = false; 
        
        //Check if any field is empty
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
        
        if (!invalidInput) {
            try { 
                DatabaseHandler db = new DatabaseHandler();
                db.connect_CALENDAR();
                PreparedStatement pstmt;
                String query; 
                boolean usedEmail, usedUsername;
                
                /**
                 * Check if email is already in the system.
                 */
                query = "SELECT * FROM USERS WHERE EMAIL=?";
                pstmt = db.conn.prepareStatement(query); 
                pstmt.setString(1, emailTextField.getText());
                ResultSet rs = pstmt.executeQuery(); 
                if (rs.next()) {
                    //Found existing user in the database based on Email.
                    usedEmail = true; 
                }
                else 
                    usedEmail = false; 
                
                //Alert if email is already in the system. 
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
                
                //Alert if email is already in the system. 
                if (usedUsername == true) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Username found");
                    errorAlert.setContentText("Please choose another username or login.");
                    errorAlert.showAndWait();
                }
                
                /**
                 * Check if email is already in the system.
                 */
                
                //New User
                if (usedEmail == false && usedUsername == false) {
                    /**
                     * If user is not in the database, save user's data into database Calendar
                     */ 
                    query = "INSERT INTO USERS (first_name, last_name, username, password, email) "
                            + "VALUES (?, ?, ?, ?, ?)";
                    pstmt = db.conn.prepareStatement(query);
                    pstmt.setString(1, firstNameTextField.getText());
                    pstmt.setString(2, lastNameTextField.getText());
                    pstmt.setString(3, usernameTextField.getText());
                    pstmt.setString(4, passwordTextField.getText());
                    pstmt.setString(5, emailTextField.getText());
                    pstmt.executeUpdate();
                    
                    //Notify user about successful registration
                    Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
                    confirmationAlert.setContentText("Successful Registration");
                    confirmationAlert.getButtonTypes().remove(1);
                    confirmationAlert.showAndWait();
                     
                    /*
                    * Switch to Main Calendar Scene
                    */ 
                    Parent mainCalendarParent = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
                    Scene mainCalendarScene = new Scene(mainCalendarParent);

                    //This line gets stage informaion
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
        // TODO
    }    
    
}
