package javafxscheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EventsOfADay {
    /*********** Fields ***********/ 
    //private XSSFWorkbook userWorkbook; 
    //private XSSFSheet userSpreadsheet;
    private String currentUser;
    private ArrayList eventCollection;
    
    /*********** Constructor, Getters and Setters ***********
    /* Constructor
     * @param user
     * @param coll 
     */
    public EventsOfADay (String user, ArrayList<Object[]> coll) {
        currentUser = user; 
        eventCollection = coll; 
    }
    
    /* Setters and Getters */
    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList getEventCollection() {
        for (int i = 0; i < eventCollection.size(); i++) {
            Object[] objectArr = (Object[]) eventCollection.get(i);
            for (Object o : objectArr) {
                System.out.print(o + " ");
            }
        }
        return eventCollection;
        
    }

    public void setEventCollection(ArrayList eventCollection) {
        this.eventCollection = eventCollection;
    }
    
    /*
    public XSSFWorkbook getUserWorkbook() {
        return userWorkbook;
    }
    public void setUserWorkbook(XSSFWorkbook userWorkbook) {
        this.userWorkbook = userWorkbook;
    }
    public XSSFSheet getUserSpreadsheet() {
        return userSpreadsheet;
    }
    public void setUserSpreadsheet(XSSFSheet userSpreadsheet) {
        this.userSpreadsheet = userSpreadsheet;
    }
    */
    
    /*********** Methods ***********/
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
                Object[] objectArr = (Object[]) eventCollection.get(rowid);
                
                int cellid = 0;
                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String) obj);
                }
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
    
}
