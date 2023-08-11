/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company;

import com.aybatu.workgroup.workgroup.admin.Admin;
import com.aybatu.workgroup.workgroup.company.employee.Employee;
import com.aybatu.workgroup.workgroup.company.employee.EmployeeService;
import com.aybatu.workgroup.workgroup.manager.Manager;
import com.aybatu.workgroup.workgroup.manager.ManagerService;
import com.aybatu.workgroup.workgroup.userAccountRequests.CreateUserAccountRequest;
import com.aybatu.workgroup.workgroup.userAccountRequests.DeleteUserAccountRequest;
import com.aybatu.workgroup.workgroup.userAccountRequests.UpdateUserAccountRequest;
import com.aybatu.workgroup.workgroup.userAccountRequests.UserAccount;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aybatukerkukluoglu
 */
@RestController
public class CompanyController {

    private final CompanyService companyService;
    private final UniqueRegistrationNumberGenerator numberGenerator;
    private final EmployeeService employeeService;
    private final ManagerService managerService;

    @Autowired
    public CompanyController(CompanyService companyService, UniqueRegistrationNumberGenerator numberGenerator, EmployeeService employeeService, ManagerService managerService) {
        this.companyService = companyService;
        this.numberGenerator = numberGenerator;
        this.employeeService = employeeService;
        this.managerService = managerService;
    }
    
    
    @PostMapping("/registercompany")
    public ResponseEntity<?> createCompany(@RequestBody Company company) {
        Company companyByOwner = companyService.findCompanyByOwnerAccountEmailAddress(company.getOwnerAccount().getEmailAddress());
        if (companyByOwner != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The owner email address " + company.getOwnerAccount().getEmailAddress() + " already registered in the system. Please try another email address." );
        }
        try {
            String registrationNumber = numberGenerator.generateUniqueRegistrationNumber();
            company.setRegistrationNumber(registrationNumber);

            // Save the company in the database
            Company createdCompany = companyService.registerCompany(company);

            // Prepare the response
            Map<String, Object> response = new HashMap<>();
            response.put("registrationNumber", registrationNumber);
            response.put("company", createdCompany);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            String errorMessage = "An error occurred while creating the company.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping("/company/login")
    public ResponseEntity<?> getCompanyByRegistrationNumber(@RequestBody CompanyLoginRequest companyLoginRequest) {
        try {
            // Retrieve the company from the database using the registration number
            Company company = companyService.getCompanyByRegistrationNumber(companyLoginRequest.registrationNumber);
       
            if (company == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no company found with this registration number.");
            }
            Admin foundAdmin = company.getOwnerAccount();
            Manager foundManager = company.getManagerAccounts().stream()
                    .filter(isManager -> isManager.getEmailAddress().equalsIgnoreCase(companyLoginRequest.email))
                    .findFirst()
                    .orElse(null);
            Employee foundEmployee = company.getEmployeeAccounts().stream()
                    .filter(isEmployee -> isEmployee.getEmailAddress().equalsIgnoreCase(companyLoginRequest.email))
                    .findFirst()
                    .orElse(null);
            
            if(foundManager == null && foundEmployee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entered email address is not found in the system.");
            } else {
                
                UserAccount userAccount;
                boolean isPasswordCorrect;
                 
                if(foundEmployee != null){
                    userAccount = foundEmployee;
                    isPasswordCorrect = userAccount.getPassword().equals(companyLoginRequest.password);
                } else if(foundManager != null) {
                    userAccount = foundManager;
                    isPasswordCorrect = userAccount.getPassword().equals(companyLoginRequest.password);
                } else {
                    userAccount = foundAdmin;
                    isPasswordCorrect = userAccount.getPassword().equals(companyLoginRequest.password);
                }
              
                if(isPasswordCorrect) {
                    LoginRespond loginRespond = new LoginRespond();
                    loginRespond.setCompany(company);
                    loginRespond.setUserAccount(userAccount);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginRespond);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incorrect password.");
                }
            }
            
        } catch (Exception e) {
            String errorMessage = "An error occurred while retrieving the company.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping("/{companyRegistrationNumber}/{accountType}")
    public ResponseEntity<String> createAccount(
            @PathVariable String companyRegistrationNumber,
            @RequestBody CreateUserAccountRequest request
    ) {

        try {

            switch (request.getAccountType()) {
                case EMPLOYEE -> {
                    Employee userAccount = new Employee(
                            request.getEmailAddress(),
                            request.getUserFirstName(),
                            request.getUserLastName(),
                            request.getPassword()
                    );
                    if (companyService.addEmployeeAccount(companyRegistrationNumber, userAccount)) {
                        return ResponseEntity.ok("Account created successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("User is already registered in the system.");
                    }
                }
                case MANAGER -> {
                    Manager userAccount = new Manager(
                            request.getEmailAddress(),
                            request.getUserFirstName(),
                            request.getUserLastName(),
                            request.getPassword()
                    );
                    if (companyService.addManagerAccount(companyRegistrationNumber, userAccount)) {
                        return ResponseEntity.ok("Account created successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("User is already registered in the system.");
                    }
                }
                default -> {
                    return ResponseEntity.badRequest().body("Invalid account type.");
                }
            }

        } catch (Exception e) {
            String errorMessage = "Failed to create account. Please check your internet connection. Could not connect to the server. ";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

    }

    @PutMapping("/{companyRegistrationNumber}/updateUserAccount")
    public ResponseEntity<String> updateUserAccount(
            @PathVariable String companyRegistrationNumber,
            @RequestBody UpdateUserAccountRequest request
    ) {
        String userOriginalEmail = request.getOriginalEmailAddress();
        String userNewEmail = request.getNewEmailAddress();
        Company company = companyService.getCompanyByRegistrationNumber(companyRegistrationNumber);
        if(!userOriginalEmail.equalsIgnoreCase(userNewEmail)) {
            Employee employee = employeeService.getEmployeeByEmailAddress(company, userNewEmail);
            Manager manager = managerService.getManagerByEmailAddress(company, userNewEmail);
            
            if(manager != null || employee != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(userNewEmail + " is already registered in the system. Please try a different email address.");
            }
        }
        
        try {
            switch (request.getNewAccountType()) {
                case EMPLOYEE -> {
                    Employee updatedUserAccount = new Employee(
                            request.getNewEmailAddress(),
                            request.getNewUserFirstName(),
                            request.getNewUserLastName(),
                            request.getNewPassword()
                    );
                    if (companyService.updateUserAccount(companyRegistrationNumber, updatedUserAccount, request.getOriginalEmailAddress(), request.getOriginalAccountType())) {
                        return ResponseEntity.ok("Account updated successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account is not found in the system. Please try again.");
                    }
                }
                case MANAGER -> {
                    Manager updatedUserAccount = new Manager(
                            request.getNewEmailAddress(),
                            request.getNewUserFirstName(),
                            request.getNewUserLastName(),
                            request.getNewPassword()
                    );
                    if (companyService.updateUserAccount(companyRegistrationNumber, updatedUserAccount, request.getOriginalEmailAddress(), request.getOriginalAccountType())) {
                        return ResponseEntity.ok("Account updated successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account is not found in the system. Please try again.");
                    }
                }
                default -> {
                    return ResponseEntity.badRequest().body("Invalid account type.");
                }
            }
        } catch (Exception e) {
            String errorMessage = "Failed to update account. Please check your internet connection. Could not connect to the server.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @DeleteMapping("/{companyRegistrationNumber}/deleteUserAccount")
    public ResponseEntity<?> deleteUserAccount(@PathVariable String companyRegistrationNumber, @RequestBody DeleteUserAccountRequest request) {
        switch (request.getAccountType()) {
            case EMPLOYEE -> {
                Employee userAccount = new Employee(
                        request.getEmailAddress(),
                        request.getUserFirstName(),
                        request.getUserLastName(),
                        request.getPassword()
                );
                if (companyService.deleteEmployeeAccount(companyRegistrationNumber, userAccount)) {
                    return ResponseEntity.ok("Account deleted successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account is not found in the system please try again.");
                }
            }
            case MANAGER -> {
                Manager userAccount = new Manager(
                        request.getEmailAddress(),
                        request.getUserFirstName(),
                        request.getUserLastName(),
                        request.getPassword()
                );
                if (companyService.deleteManagerAccount(companyRegistrationNumber, userAccount)) {
                    return ResponseEntity.ok("Account deleted successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account is not found in the system please try again.");
                }
            }
            default -> {
                return ResponseEntity.badRequest().body("Invalid account type.");
            }
        }
    }
  
}
