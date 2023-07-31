/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company.employee;


import com.aybatu.workgroup.workgroup.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author aybatukerkukluoglu
 */
@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

    }
    
    public Employee getEmployeeByEmailAddress(Company company, String emailAddress) {
        if (company != null) {
            return company.getEmployeeAccounts().stream()
                    .filter(userAccount -> {
                return userAccount.getEmailAddress().equals(emailAddress);
            })
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
  
   
}
