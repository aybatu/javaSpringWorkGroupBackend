/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company.employee;


import com.aybatu.workgroup.workgroup.company.Company;
import com.aybatu.workgroup.workgroup.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aybatukerkukluoglu
 */
@RestController
public class EmployeeController {
    private EmployeeService employeeService;
    private CompanyService companyService;
    
    @Autowired
    public EmployeeController(EmployeeService employeeService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }   
    
   @GetMapping("/company/employeeAccounts/{companyRegistrationNumber}")
   public ResponseEntity<?> getEmployeeAccounts(@PathVariable String companyRegistrationNumber){
       Company company = companyService.getCompanyByRegistrationNumber(companyRegistrationNumber);
       
       if(company != null) {
           return ResponseEntity.ok(company.getEmployeeAccounts());
       } else {
           String errorMsg = "Please check your internet connection. There was an error while fetching company information.";
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
       }
   }
}
