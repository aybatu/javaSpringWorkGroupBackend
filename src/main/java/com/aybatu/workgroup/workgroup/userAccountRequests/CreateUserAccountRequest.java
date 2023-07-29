/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.user;

/**
 *
 * @author aybatukerkukluoglu
 */
public class UserAccountRequest {
    private String emailAddress;
    private AccountTypes accountType;
    private String userFirstName;
    private String userLastName;
    private String password;

    public AccountTypes getAccountType() {
        return accountType;
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
