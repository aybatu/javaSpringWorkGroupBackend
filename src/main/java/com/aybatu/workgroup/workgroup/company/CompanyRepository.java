/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author aybatukerkukluoglu
 */
public interface CompanyRepository extends MongoRepository<Company, String> {
    Company findByRegistrationNumber(String registrationNumber);
}