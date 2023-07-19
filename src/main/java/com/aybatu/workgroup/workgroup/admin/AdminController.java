/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.admin;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aybatukerkukluoglu
 */
@RestController
public class AdminController {
    private AdminService adminService;
    
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    
}
