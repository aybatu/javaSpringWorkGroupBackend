/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.manager;

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
    
    Manager getManagerByEmailAddress(String emailAddress) {
        return managerRepository.findByEmailAddress(emailAddress);
    }
}
