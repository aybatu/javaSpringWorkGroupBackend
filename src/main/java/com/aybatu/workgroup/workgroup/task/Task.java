/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.task;


import com.aybatu.workgroup.workgroup.company.employee.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author aybatukerkukluoglu
 */
@Document(collection = "Tasks")
public class Task {
   
    private String title;
    private String description;
    private List<Employee> assignedEmployees;
    private boolean isTaskCompleted;
 
    private String taskStartDate;

    private String taskEndDate;
   
     public Task(
             String title, 
             String description, 
             List<Employee> assignedEmployees,
             String taskStartDate,
             String taskEndDate
     ) {
        this.title = title;
        this.description = description;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.isTaskCompleted = false;
        this.assignedEmployees = assignedEmployees;
        
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }

    public boolean isIsTaskCompleted() {
        return isTaskCompleted;
    }

    public String getTaskStartDate() {
        return taskStartDate;
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAssignedEmployees(List<Employee> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }

    public void setIsTaskCompleted(boolean isTaskCompleted) {
        this.isTaskCompleted = isTaskCompleted;
    }

    public void setTaskStartDate(String taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        this.taskEndDate = taskEndDate;
    }
     

}
