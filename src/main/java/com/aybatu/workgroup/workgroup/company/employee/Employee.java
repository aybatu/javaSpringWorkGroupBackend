/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company.employee;


import com.aybatu.workgroup.workgroup.project.Meeting;
import com.aybatu.workgroup.workgroup.task.Task;
import com.aybatu.workgroup.workgroup.user.AccountTypes;
import com.aybatu.workgroup.workgroup.user.UserAccount;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author aybatukerkukluoglu
 */
@Document(collection = "Employee")
public class Employee implements UserAccount {
    
    @Field("emailAddress")
    private String emailAddress;
       private  AccountTypes accountType;
       private String userFirstName;
       private String userLastName;
       private String password;
       private List<Task> userTasks;
       private List<Meeting> employeeInvitedMeetings;
       
       public Employee(String emailAddress, String userFirstName, String userLastName, String password) {
   
        this.accountType = AccountTypes.EMPLOYEE;
          this.emailAddress = emailAddress;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.password = password;
        this.userTasks = new ArrayList<>();
        this.employeeInvitedMeetings = new ArrayList<>();
    }

    public List<Task> getUserTasks() {
        return userTasks;
    }

    public List<Meeting> getEmployeeInvitedMeetings() {
        return employeeInvitedMeetings;
    }

    
  
    public String getAccountType() {
        return AccountTypes.EMPLOYEE.toString();
    }

    
    public String getEmailAddress() {
        return emailAddress;
    }


    public String getUserFirstName() {
        return userFirstName;
    }


    public String getUserLastName() {
        return userLastName;
    }


    public String getPassword() {
        return password;
    }
    
}
