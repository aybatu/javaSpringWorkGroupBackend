/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.user;

import com.aybatu.workgroup.workgroup.admin.Admin;
import com.aybatu.workgroup.workgroup.admin.AdminController;
import com.aybatu.workgroup.workgroup.company.Company;
import com.aybatu.workgroup.workgroup.company.CompanyController;
import com.aybatu.workgroup.workgroup.company.CompanyService;
import com.aybatu.workgroup.workgroup.company.employee.Employee;
import com.aybatu.workgroup.workgroup.company.employee.EmployeeController;
import com.aybatu.workgroup.workgroup.manager.Manager;
import com.aybatu.workgroup.workgroup.manager.ManagerController;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author aybatukerkukluoglu
 */
@Service
public class UserAccountService {


    private final CompanyService companyService;
   
    @Autowired
    public UserAccountService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public boolean isEmailAddressRegisted(String emailAddress, Company company) {

        List<Employee> employeeList = company.getEmployeeAccounts();
        List<Manager> managerList = company.getManagerAccounts();
        Admin ownerAccount = company.getOwnerAccount();
      
        if(!employeeList.isEmpty()) {
            for(Employee e: employeeList){
                if(e.getEmailAddress().equalsIgnoreCase(emailAddress)) {
                    System.out.println("FoundEmployee: " + e.getEmailAddress());
                    return true;
                }
            }
        }
        if(ownerAccount.getEmailAddress().equalsIgnoreCase(emailAddress)) {
            System.out.println("FoundOwner: " + ownerAccount.getEmailAddress());
           return true;
        }
        if(!managerList.isEmpty()) {
            for(Manager m: managerList){
                if(m.getEmailAddress().equalsIgnoreCase(emailAddress)) {
                    System.out.println("FoundManager: " + m.getEmailAddress());
                    return true;
                }
            }
        }
        
        return false;
    }

}
