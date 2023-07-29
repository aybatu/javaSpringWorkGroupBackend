/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.admin;


import com.aybatu.workgroup.workgroup.userAccountRequests.AccountTypes;
import com.aybatu.workgroup.workgroup.userAccountRequests.UserAccount;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author aybatukerkukluoglu
 */
@Document(collection = "OwnerAccount")
public class Admin implements UserAccount {

    @Field("emailAddress")
    private String emailAddress;
    private AccountTypes accountType;
    private String password;
    private String userFirstName;
    private String userLastName;

   public Admin(String emailAddress, String userFirstName, String userLastName, String password) {
    
        this.accountType = AccountTypes.ADMIN;
        this.emailAddress = emailAddress;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAccountType() {
        return accountType.toString();
    }

    public String getPassword() {
        return password;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }
 
    
}
