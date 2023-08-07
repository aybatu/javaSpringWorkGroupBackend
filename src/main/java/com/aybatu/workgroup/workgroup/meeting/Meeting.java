/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.meeting;

import com.aybatu.workgroup.workgroup.manager.Manager;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author aybatukerkukluoglu
 */
public class Meeting {
     private String meetingDate;
     private String meetingStartTime;
     private String meetingEndTime;
     private String meetingTitle;
     private String meetingDescription;

    
    public Meeting(String meetingDate, String meetingStartTime, String meetingEndTime, String meetingTitle, String meetingDescription) {
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.meetingTitle = meetingTitle;
        this.meetingDescription = meetingDescription;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public String getMeetingStartTime() {
        return meetingStartTime;
    }

    public String getMeetingEndTime() {
        return meetingEndTime;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

   
         @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Meeting meeting = (Meeting) obj;
        return Objects.equals(meetingTitle, meeting.meetingTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingTitle);
    }

}
