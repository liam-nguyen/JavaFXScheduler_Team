/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxscheduler;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;

/**
 *
 * @author ilike
 */
public class Event {
     String eventName, startTime, endTime, fkUserName, eventDate;
     String realStartTime , realEndTime; 

    public String getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(String realStartTime) {
        this.realStartTime = realStartTime;
    }

    public String getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(String realEndTime) {
        this.realEndTime = realEndTime;
    }
    
    public Event(String date, String name, String start, String end, String username) {
        eventDate = date; 
        eventName = name;
        startTime = start; 
        endTime = end; 
        fkUserName = username;
        realStartTime = Event.convertMinutesIntoHourMins(start); 
        realEndTime = Event.convertMinutesIntoHourMins(end); 
    }
    
    public Event(String eventName) {
        this.eventName = eventName; 
    }
    
    public Event() {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFkUserName() {
        return fkUserName;
    }

    public void setFkUserName(String fkUserName) {
        this.fkUserName = fkUserName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate.toString();
    }
    
    
    /** This method checks if the input start time and end time are valid.
     * @param startTime: event's start time.
     * @param endTime: event's end time
     * @return 
     */
    static public boolean validTime (int startTime, int endTime) {
        boolean valid = true; 
        //Check if the HOUR of end time is after start time
        if (startTime >= endTime) {
            valid = false; 
        }
        return valid;
    }
    
    
        /**
     * This method checks if new appointment conflicts with current appointment. 
     * @return boolean: true if valid. 
     */
    static public boolean apptConflict (String username, LocalDate apptDate, int startTime, int endTime) {
        boolean conflicted = false;
        try {
            final int HOUR = 24, MIN = 60; 
            boolean dayBlock[] = new boolean [HOUR*MIN];
            
            //Initialize the dayBlock
            for (int i = 0; i < HOUR * MIN; i++) {
                dayBlock[i] = true;
            }
            
            //Check if there is any appointment happens on the same date.
            DatabaseHandler db = new DatabaseHandler();
            db.connect_CALENDAR();
            String query = "SELECT * FROM EVENTS WHERE fk_username=? AND date = ?";
            PreparedStatement pstmt = db.conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, String.valueOf(apptDate));
            ResultSet rs = pstmt.executeQuery();
            
            //If there is a result, there is a potential conflict. 
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    //Get value from database
                    Date sqlDate = rs.getDate("date"); 
                    LocalDate sqlLocalDate = sqlDate.toLocalDate();
                    int sqlStartHour = rs.getInt("start_time");
                    int sqlStartMin = rs.getInt("end_time");
                    //Mark all the busy minutes. 
                    for (int i = sqlStartHour; i < sqlStartMin; i++) {
                        dayBlock[i] = false;
                    }
                    for (int i = startTime; i < endTime; i++) {
                        if (dayBlock[i] == false) {
                            conflicted = true;
                            break; 
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("apptConflict Error: " + ex);
        }
        return conflicted; 
    }
    
    /**
     * This method converts a string of time in minutes into proper hour and minute. 
     * @param timeInMinutes
     * @return 
     */
    static public String convertMinutesIntoHourMins (String timeInMinutes) {
        int timeInMinuteValue = Integer.parseInt(timeInMinutes); 
        DecimalFormat formatter = new DecimalFormat("00"); 
        String realTime = Integer.toString(timeInMinuteValue / 60) + ":" + formatter.format(timeInMinuteValue % 60); 
        return realTime; 
    }
    
    /**
     * This method converts a string of hour and minutes in minutes into minutes.
     * @param properTime
     * @return 
     */
    static public String convertToOnlyMinutes (String properTime) {
        String[] parseDateString = properTime.split(":");
        String result = Integer.toString(Integer.parseInt(parseDateString[0])* 60 + Integer.parseInt(parseDateString[1])); 
        return result; 
    }
    
    
}
