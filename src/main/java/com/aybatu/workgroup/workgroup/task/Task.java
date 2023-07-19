/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.task;

import com.aybatu.workgroup.workgroup.company.employee.Employee;
import java.util.Date;

/**
 *
 * @author aybatukerkukluoglu
 */
public class Task {
       private String title;
    private String description;
    private Employee[] assignedEmployees;
    private boolean isTaskCompleted;
    private Date taskStartDate;
    private Date taskEndDate;
   
 
    public Task(String title, String description, Employee[] assignedEmployees, Date taskStartDate, Date taskEndDate) {
        this.title = title;
        this.description = description;
        this.assignedEmployees = assignedEmployees;
        this.isTaskCompleted = false;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
    }
}
