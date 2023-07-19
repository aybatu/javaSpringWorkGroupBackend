/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.user;

import com.aybatu.workgroup.workgroup.company.employee.Employee;
import com.aybatu.workgroup.workgroup.manager.Manager;
import com.aybatu.workgroup.workgroup.company.Company;
import com.aybatu.workgroup.workgroup.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aybatukerkukluoglu
 */
@RestController
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final CompanyRepository companyRepository;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, CompanyRepository companyRepository, UserAccountRepository userAccountRepository) {
        this.userAccountService = userAccountService;
        this.companyRepository = companyRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping("/{companyRegistrationNumber}/{accountType}")
    public ResponseEntity<String> createAccount(
            @PathVariable String companyRegistrationNumber,
            @RequestBody UserAccountRequest request
    ) {
        try {
            Company company = companyRepository.findByRegistrationNumber(companyRegistrationNumber);
            if (company == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
            }

            boolean isUserExists = userAccountService.isEmailAddressRegisted(request.getEmailAddress(), company);
     
            if (isUserExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("There is a user already registered with the email address " + request.getEmailAddress() + ". Please try a different email address to register user.");
            }

            if (request.getAccountType().equals(AccountTypes.EMPLOYEE)) {
                Employee userAccount = new Employee(
                        request.getEmailAddress(),
                        request.getUserFirstName(),
                        request.getUserLastName(),
                        request.getPassword()
                );
                company.addEmployeeAccount(userAccount);
            } else if (request.getAccountType().equals(AccountTypes.MANAGER)) {
                Manager userAccount = new Manager(
                        request.getEmailAddress(),
                        request.getUserFirstName(),
                        request.getUserLastName(),
                        request.getPassword()
                );
                company.addManagerAccount(userAccount);
            }

            companyRepository.save(company);
            return ResponseEntity.ok("Account created successfully");

        } catch (Exception e) {
            String errorMessage = "Failed to create account. Please check your internet connection. Could not connect to the server. ";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

    }
}
