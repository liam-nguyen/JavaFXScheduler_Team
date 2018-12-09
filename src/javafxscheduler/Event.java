/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxscheduler;

import java.sql.Date;
import java.text.DecimalFormat;

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
        /* Convert back to real hour and minutes for start and end time */
        int startValue = Integer.parseInt(start); 
        int endValue = Integer.parseInt(end);
        DecimalFormat formatter = new DecimalFormat("00"); 

        realStartTime = Integer.toString(startValue / 60) + ":" + formatter.format(startValue % 60); 
        realEndTime = Integer.toString(endValue / 60) + ":" + formatter.format(endValue % 60); 
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
    
}
