package javafxscheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;


public class ApptManipulator {
    /*********** Fields ***********/ 

    private String currentUser;
    private ArrayList<Event> eventCollection;
    
    /*********** Constructor, Getters and Setters ***********
    /* Constructor
     * @param user
     * @param coll 
     */
    public ApptManipulator (String user) {
        currentUser = user; 
    }
    
    /* Constructor
     * @param user
     * @param coll 
     */
    public ApptManipulator () {
        currentUser = ""; 
    }
    
    /* Setters and Getters */
    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList getEventCollection() {
        return eventCollection;
        
    }

    public void setEventCollection(ArrayList eventCollection) {
        this.eventCollection = eventCollection;
    }
    
    /*********** Methods ***********/
    
    /** 
     * This method gets all the appointment from the database and return an ArrayList.
     * @return 
     */
    public ArrayList<Event> allEventsOfUser () {
        ArrayList <Event> eventsArrayList = new ArrayList(); 
        
        /* Search database all the events on that date */ 
        try {
            DatabaseHandler db = new DatabaseHandler();
            db.connect_CALENDAR();
            String query = "SELECT * FROM EVENTS WHERE DATE BETWEEN ? AND ? AND FK_USERNAME = ?";
            PreparedStatement pstmt;
            pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, "1900-01-01");
            pstmt.setString(2, "2099-01-01");
            pstmt.setString(3, currentUser);
            ResultSet rs = pstmt.executeQuery();
            /* Create an ArrayList to store all the events.
             * Each event is considered to be an String array.
             * The format of the String[] is : [date, event_name, start_time, end_time, fk_username]
            */ 
            
            while (rs.next()) {
                /* Get data from database of the user on a specific date */
                    //Dealing with date. 
                Date tempDate = rs.getDate("date");
                LocalDate date = tempDate.toLocalDate();

                String dateString =  date.getYear() + "-" + date.getMonth().getValue() + "-" + date.getDayOfMonth();
                System.out.println("dateString: " + dateString);
                    //Other data
                String tempEventName = rs.getNString("event_name"); 
                int tempStartTime = rs.getInt("start_time");
                int tempEndTime = rs.getInt("end_time");
                String tempUserName = rs.getNString("fk_username");
                /* Create an event object to hold all the information */
                Event tempEvent = new Event(dateString, tempEventName, 
                    Integer.toString(tempStartTime), Integer.toString(tempEndTime), tempUserName); 
                /* Add that object array into the ArrayList */
                eventsArrayList.add(tempEvent);
            }
        }   catch (SQLException ex) {
            System.out.println("allEventsOfUser error: " + ex);
        }
        return eventsArrayList; 
    }
    
    
    /**
     * This methods write an Arraylist of events object into an Excel file.
     */
    public void writeToFile () {
        FileOutputStream out = null;
        try {
            //Create blank workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet spreadsheet = workbook.createSheet("Event of a day");
            //Create row object
            XSSFRow row;
                        
            for (int rowid = 0; rowid < eventCollection.size(); rowid++) {
                //Create a new row for each iteration.
                row = spreadsheet.createRow(rowid);
                
                Event objectArr = eventCollection.get(rowid);
           
                Cell cell = row.createCell(0);
                cell.setCellValue(objectArr.getEventDate());
                cell = row.createCell(1);
                cell.setCellValue(objectArr.getEventName());
                cell = row.createCell(2);
                cell.setCellValue(objectArr.getStartTime());
                cell = row.createCell(3);
                cell.setCellValue(objectArr.getEndTime());
                cell = row.createCell(4);
                cell.setCellValue(objectArr.getFkUserName());  
            }
            LocalDate now = LocalDate.now(); 
           
            /* Write the workbook in file system */
            out = new FileOutputStream(new File(currentUser +  "_Calendar_" + now + ".xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("Writesheet.xlsx written successfully");
            System.out.println("Written a file");
        } catch (FileNotFoundException ex) {
            System.out.println("writeToFile FileNotFound: " + ex);
        } catch (IOException ex) {
            System.out.println("writeToFile IOException: " + ex);
        }
    }
    
    /**
     * This methods read an Excel file to get appointment.
     */
    public ArrayList <Event> readFile (File userFile) {
        ArrayList <Event> otherUserEventsArrayList = new ArrayList(); 
        try {
            FileInputStream fis = new FileInputStream(userFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator < Row >  rowIterator = spreadsheet.iterator();
            XSSFRow row; 
           
            while (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next(); 
                Iterator < Cell >  cellIterator = row.cellIterator();
                
                String[] stringRow = new String[5];
                int index = 0; 
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    stringRow[index] = cell.getStringCellValue();
                    index++;
                }
                
                
                //Save the stringRow into the Event object. 
                Event tempEvent = new Event(stringRow[0], stringRow[1], stringRow[2], stringRow[3], stringRow[4]); 
                
                //Add that to the arraylist
                otherUserEventsArrayList.add(tempEvent);
            }  
            
        } catch (FileNotFoundException ex) {
            System.out.println("readFile: File not Found. " + ex);
        } catch (IOException ex) {
            System.out.println("readFile: IOException " + ex);      
        }
         
        return otherUserEventsArrayList;  
    }
}
