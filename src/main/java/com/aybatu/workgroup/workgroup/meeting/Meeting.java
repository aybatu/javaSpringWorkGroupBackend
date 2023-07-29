/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.meeting;

import java.util.Date;

/**
 *
 * @author aybatukerkukluoglu
 */
public class Meeting {
     private Date meetingDate;
     private Date meetingStartTime;
     private Date meetingEndTime;
     private String meetingTitle;
     private String meetingDescription;

    
    public Meeting(Date meetingDate, Date meetingStartTime, Date meetingEndTime, String meetingTitle, String meetingDescription) {
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.meetingTitle = meetingTitle;
        this.meetingDescription = meetingDescription;
    }
}
