package javafxscheduler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.paint.Color;


public class User {
    private String firstName, lastName, email;
    private String username, password, phone;
    private String preference;
    private String reminderTime = "30";
    private String provider;
    private Color calendarColor; 

    
//Connecting to database
    private DatabaseHandler userDB = new DatabaseHandler();
   /** Constructor */
    public User (String f, String l, String e, String u, String p) {
        firstName = f; 
        lastName = l; 
        email = e; 
        username = u; 
        password = p;
        preference = "email"; 
    }

    
    public User (String u, String p) {
        username = u; 
        password = p;
    }
    
    /* Getters & Setters */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
    
    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    public Color getCalendarColor() {
        return calendarColor;
    }

    public void setCalendarColor(Color calendarColor) {
        this.calendarColor = calendarColor;
    }
    
    
    /** Other methods
     * @return  */
    public boolean verifyAccount() {
        try {
            userDB.connect_CALENDAR();
            
            if (this.username != null && this.password != null) {
                String query = "SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD= ?";
                PreparedStatement pstmt;
                pstmt = userDB.conn.prepareStatement(query); 
                pstmt.setString(1, this.username);
                pstmt.setString(2, this.password);
                ResultSet rs = pstmt.executeQuery(); 
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Verify_account method error: " + ex);
        }
        return false;
    }
}
