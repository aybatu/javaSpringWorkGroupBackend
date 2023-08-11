/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company;

import com.aybatu.workgroup.workgroup.userAccountRequests.UserAccount;

/**
 *
 * @author aybatukerkukluoglu
 */
public class LoginRespond {
    private UserAccount userAccount;
    private Company company;
    public LoginRespond(){}
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    
}
