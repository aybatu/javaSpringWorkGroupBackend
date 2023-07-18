/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company;

import com.aybatu.workgroup.workgroup.user.AccountTypes;
import com.aybatu.workgroup.workgroup.company.employee.Employee;
import com.aybatu.workgroup.workgroup.manager.Manager;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

/**
 *
 * @author aybatukerkukluoglu
 */
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final MongoOperations mongoOperations;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, MongoOperations mongoOperations) {
        this.companyRepository = companyRepository;
        this.mongoOperations = mongoOperations;
    }

    public Company registerCompany(Company company) {

        return companyRepository.save(company);
    }

    public Optional<Company> getCompanyById(String id) {
        return companyRepository.findById(id);

    }

    public Company getCompanyByRegistrationNumber(String registrationNumber) {
        // Implement the logic to retrieve the company from the repository using the registration number
        // Return the retrieved company or null if not found
        return companyRepository.findByRegistrationNumber(registrationNumber);
    }
    
    public AccountTypes authenticateUser(String emailAddress, String password, String registrationNumber) {
        Company company = getCompanyByRegistrationNumber(registrationNumber);
        
        if (company != null) {
            // Check if the provided credentials match the owner account
            if (company.getOwnerAccount().getEmailAddress().equals(emailAddress) &&
                company.getOwnerAccount().getPassword().equals(password)) {
                return AccountTypes.ADMIN;
            }
            
            // Check the employee accounts
            for (Employee employee : company.getEmployeeAccounts()) {
                if (employee.getEmailAddress().equals(emailAddress) &&
                    employee.getPassword().equals(password)) {
                    return AccountTypes.EMPLOYEE;
                }
            }
            
            // Check the manager accounts
            for (Manager manager : company.getManagerAccounts()) {
                if (manager.getEmailAddress().equals(emailAddress) &&
                    manager.getPassword().equals(password)) {
                    return AccountTypes.MANAGER;
                }
            }
        }
        
        // No matching account found
        return null;
    }
}
