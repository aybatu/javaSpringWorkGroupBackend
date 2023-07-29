/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author aybatukerkukluoglu
 */
@Component
public class UniqueRegistrationNumberGenerator {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");

    public String generateUniqueRegistrationNumber() {
        // Generate the current date as an integer
        String currentDate = dateFormat.format(new Date());
        int dateNumber = Integer.parseInt(currentDate);

        // Generate a random number with 6 digits
        int randomNumber = (int) (Math.random() * 900000) + 100000;

        // Combine the date number and random number to form the registration number
        String registrationNumber = String.valueOf(dateNumber) + String.format("%06d", randomNumber);

        return registrationNumber;
    }
}
