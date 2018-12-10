package javafxscheduler;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLAccountDetailController implements Initializable {

    @FXML private Label accountDetailLabel; 
    @FXML private Label firstNameTitleLabel; 
    @FXML private Label lastNameTitleLabel; 
    @FXML private Label usernameTitleLabel; 
    @FXML private Label passwordTitleLabel; 
    @FXML private Label emailTitleLabel; 
    @FXML private Label phoneTitleLabel;
    @FXML private Label reminderTitleLabel;
    @FXML private Label userFirstNameLabel; 
    @FXML private Label userLastNameLabel; 
    @FXML private Label userUsernameNameLabel; 
    @FXML private Label userPasswordNameLabel; 
    @FXML private Label userEmailNameLabel; 
    @FXML private Label userPhoneLabel;
    @FXML private Label userPreferenceLabel;
    @FXML private Label userReminderLabel;
    
    private User signedInUser;
    
    /**
     * When this method is called, currentUser's fields are initialized 
     *      from previous scene. 
     */
    public void initCurrentUser (String u, String p)  
    {
        signedInUser = new User(u,p); 
    }
    
    /**
     * This method populates the labels with user's information.
     */
     public void populateUserInformation () {
        userFirstNameLabel.setVisible(true);
        userLastNameLabel.setVisible(true);
        userUsernameNameLabel.setVisible(true);
        userPasswordNameLabel.setVisible(true);
        userEmailNameLabel.setVisible(true);
        userPhoneLabel.setVisible(true);
        userPreferenceLabel.setVisible(true);
        userReminderLabel.setVisible(true);
        userFirstNameLabel.setText(signedInUser.getFirstName().toUpperCase());
        userLastNameLabel.setText(signedInUser.getLastName().toUpperCase(Locale.ITALY));
        userUsernameNameLabel.setText(signedInUser.getUsername());
        userPasswordNameLabel.setText(signedInUser.getPassword());
        userEmailNameLabel.setText(signedInUser.getEmail());
        userPhoneLabel.setText(signedInUser.getPhone());
         System.out.println(signedInUser.getPreference());
        userPreferenceLabel.setText(signedInUser.getPreference());
        userReminderLabel.setText(signedInUser.getReminderTime() + " mins");
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

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}
