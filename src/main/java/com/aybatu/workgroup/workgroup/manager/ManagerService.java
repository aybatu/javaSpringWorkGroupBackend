/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.manager;

import com.aybatu.workgroup.workgroup.company.Company;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author aybatukerkukluoglu
 */
@Service
public class ManagerService {
    private ManagerRepository managerRepository;
    
    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }
  
    
    public Manager getManagerByEmailAddress(Company company, String emailAddress) {
        if (company != null) {
            return company.getManagerAccounts().stream()
                    .filter(userAccount -> {
                return userAccount.getEmailAddress().equals(emailAddress);
            })
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
    
}
