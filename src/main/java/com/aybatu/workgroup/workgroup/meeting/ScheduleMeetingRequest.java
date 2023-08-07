/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.meeting;

import com.aybatu.workgroup.workgroup.company.employee.Employee;

/**
 *
 * @author aybatukerkukluoglu
 */
public class ScheduleMeetingRequest {
    private Meeting meeting;
    private Employee[] invitedEmployeeList;

    public Meeting getMeeting() {
        return meeting;
    }

    public Employee[] getInvitedEmployeeList() {
        return invitedEmployeeList;
    }
    
    
}
