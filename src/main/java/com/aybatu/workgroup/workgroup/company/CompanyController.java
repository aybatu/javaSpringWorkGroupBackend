/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company;

import com.aybatu.workgroup.workgroup.user.AccountTypes;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    public CompanyController(CompanyService companyService, UniqueRegistrationNumberGenerator numberGenerator) {
        this.companyService = companyService;
        this.numberGenerator = numberGenerator;
    }

    @PostMapping("/registercompany")
    public ResponseEntity<?> createCompany(@RequestBody Company company) {
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

    @GetMapping("/company/{registrationNumber}")
    public ResponseEntity<?> getCompanyByRegistrationNumber(@PathVariable String registrationNumber) {
        try {
            // Retrieve the company from the database using the registration number
            Company company = companyService.getCompanyByRegistrationNumber(registrationNumber);

            if (company != null) {
                return ResponseEntity.ok(company);
            } else {
                String errorMessage = "Company not found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while retrieving the company.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
