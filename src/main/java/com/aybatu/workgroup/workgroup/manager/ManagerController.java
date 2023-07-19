/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.manager;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aybatukerkukluoglu
 */
@RestController
public class ManagerController {
    private ManagerService managerService;
    
    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }
    
    public Manager getManagerByEmailAddress(String emailAddress) {
        return managerService.getManagerByEmailAddress(emailAddress);
    }
}
