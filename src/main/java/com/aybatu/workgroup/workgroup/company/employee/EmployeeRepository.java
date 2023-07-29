/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company.employee;


import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author aybatukerkukluoglu
 */
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    public Employee findByEmailAddress(String emailAddress);
}
