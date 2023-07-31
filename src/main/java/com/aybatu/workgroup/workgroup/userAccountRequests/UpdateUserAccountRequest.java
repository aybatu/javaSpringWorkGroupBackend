/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.userAccountRequests;

/**
 *
 * @author aybatukerkukluoglu
 */
public class UpdateUserAccountRequest {
    private AccountTypes originalAccountType;
private String originalEmailAddress;
    private String newEmailAddress;
    private AccountTypes newAccountType;
    private String newUserFirstName;
    private String newUserLastName;
    private String newPassword;

    public String getOriginalEmailAddress() {
        return originalEmailAddress;
    }

    public AccountTypes getOriginalAccountType() {
        return originalAccountType;
    }
    

    public String getNewEmailAddress() {
        return newEmailAddress;
    }

    public AccountTypes getNewAccountType() {
        return newAccountType;
    }

    public String getNewUserFirstName() {
        return newUserFirstName;
    }

    public String getNewUserLastName() {
        return newUserLastName;
    }

    public String getNewPassword() {
        return newPassword;
    }
    
  
}
