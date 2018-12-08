package javafxscheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLAccountDetailController implements Initializable {

    @FXML private Label accountDetailLabel; 
    @FXML private Label firstNameTitleLabel; 
    @FXML private Label lastNameTitleLabel; 
    @FXML private Label usernameTitleLabel; 
    @FXML private Label passwordTitleLabel; 
    @FXML private Label emailTitleLabel; 
    @FXML private Label userFirstNameLabel; 
    @FXML private Label userLastNameLabel; 
    @FXML private Label userUsernameNameLabel; 
    @FXML private Label userPasswordNameLabel; 
    @FXML private Label userEmailNameLabel; 
    @FXML private Button backButton; 
    @FXML private Button fillInButton;
    
    private User signedInUser = new User() ;
    
    /**
     * When this method is called, currentUser's fields are initialized 
     *      from previous scene. 
     */
    public void initCurrentUser (User passingUser) 
    {
        signedInUser.setUsername(passingUser.getUsername());
        signedInUser.setPassword(passingUser.getPassword());
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
        userFirstNameLabel.setText(signedInUser.getFirstName().toUpperCase());
        userLastNameLabel.setText(signedInUser.getLastName().toUpperCase(Locale.ITALY));
        userUsernameNameLabel.setText(signedInUser.getUsername());
        userPasswordNameLabel.setText(signedInUser.getPassword());
        userEmailNameLabel.setText(signedInUser.getEmail());
    }
    
    /**
     * This method brings user back to the Main Calendar Scene.
     */
    public void backButtonPushed(ActionEvent event) {
        try {
            /*
            * Switch to Main Calendar Scene
            */
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(getClass().getResource("FXMLMainCalendar.fxml"));
            Parent loginParent = loader.load();
            Scene mainCalendarScene = new Scene(loginParent);

            //This line gets stage informaion
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainCalendarScene);
            window.show();
        } 
        catch (IOException ex) {
            System.out.println("User verified error: " + ex);
        } 
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
            }
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
