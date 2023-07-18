package com.aybatu.workgroup.workgroup.admin;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.aybatu.workgroup.workgroup.admin.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author aybatukerkukluoglu
 */
public interface AdminRepository extends MongoRepository<Admin, String> {
    // Admin-specific methods...
}

