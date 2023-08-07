/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.meeting;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author aybatukerkukluoglu
 */
public interface MeetingRepository extends MongoRepository<Meeting, String> {
    
}
