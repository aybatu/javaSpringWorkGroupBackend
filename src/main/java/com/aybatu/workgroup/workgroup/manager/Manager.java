/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.manager;

import com.aybatu.workgroup.workgroup.project.Meeting;
import com.aybatu.workgroup.workgroup.user.AccountTypes;
import com.aybatu.workgroup.workgroup.user.UserAccount;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author aybatukerkukluoglu
 */
@Document(collection = "Manager")
public class Manager implements UserAccount {
  
    @Field("emailAddress")
    private String emailAddress;
       private AccountTypes accountType;
       private String userFirstName;
       private String userLastName;
       private String password;
       private List<Meeting> employeeMeetings;

      
    public Manager(String emailAddress, String userFirstName, String userLastName, String password) {
        this.accountType = AccountTypes.MANAGER;
        this.emailAddress = emailAddress;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.password = password;
        this.employeeMeetings = new ArrayList<>();
    }


    public String getAccountType() {
        return AccountTypes.MANAGER.toString();
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
