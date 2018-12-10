package javafxscheduler;
import java.sql.*;

public class DatabaseHandler {
    // Attribute
    public Connection conn = null; 
    private String username = "root"; //username to database
    private String pass = "say2tome"; //password to database
    
    /** Establish connection to database CALENDAR */
    public void connect_CALENDAR() {
        try {
            // Register JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");
            // Open a connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost/CALENDAR?useSSL=false", this.username, this.pass);
            System.out.println("Sucessfully connected to Calendar");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("connect_Calendar() error: " + ex);
        }
    }
    
    /** Close connection*/ 
    public void close_JDBC() {
        try {
            conn.close();
            System.out.println("Close JDBC");
        } catch (SQLException ex) {
            System.out.println("close_JDBC() error: " + ex);
        }
    }
    
 
    public void createDatabase () {
        try {            
            // Open a connection to database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/?useSSL=false", username, pass);            
            /**
             * Create database and tables
             */

            Statement stmt = conn.createStatement();
            
            //Create database called Calendar
            stmt.execute("CREATE DATABASE IF NOT EXISTS CALENDAR");
            // Use database CALENDAR
            stmt.execute("USE CALENDAR");
            String query;
            //Create table USERS
            query = "CREATE TABLE IF NOT EXISTS USERS (" +
                    "first_name varchar(32) not NULL, " +
                    "last_name varchar(32) not NULL, " +
                    "username varchar(32) PRIMARY KEY, " +
                    "password varchar(32) not NULL, " +
                    "email varchar(32) not NULL, " +
                    "phone varchar(32), " + 
                    "preference varchar(32) not NULL, " + 
                    "reminderTime varchar(32), " + 
                    "provider varchar(32))"; 
            stmt.execute(query);
            
            //Create table CALENDAR
            query = "CREATE TABLE IF NOT EXISTS EVENTS (" +
                    "date date not NULL, " +
                    "event_name varchar(32) not NULL, " + 
                    "start_time varchar(32) not NULL, " + 
                    "end_time varchar(32) not NULL, " +
                    "fk_username varchar(32)," +
                    "FOREIGN KEY(fk_username) REFERENCES USERS(username)"
                    + ")";
            
            stmt.execute(query);
            //Close conneciton.
            conn.close(); 
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("createDatabase() error: " + ex);
        }
    }
}
 
