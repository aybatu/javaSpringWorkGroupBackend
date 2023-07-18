/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup;
import com.aybatu.workgroup.workgroup.company.UniqueRegistrationNumberGenerator;
 import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author aybatukerkukluoglu
 */
 
@Configuration

public class AppConfig {

    @Bean
    public UniqueRegistrationNumberGenerator numberGenerator() {
        return new UniqueRegistrationNumberGenerator();
    }

}

