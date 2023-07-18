/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.springframework.stereotype.Component;

/**
 *
 * @author aybatukerkukluoglu
 */
@Component
public class UniqueRegistrationNumberGenerator {

    private static final int RANDOM_NUMBER_MIN = 1000;
    private static final int RANDOM_NUMBER_MAX = 9999;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
    private final Random random = new Random();

    public String generateUniqueRegistrationNumber() {
        // Generate the current date as an integer
        String currentDate = dateFormat.format(new Date());
        int dateNumber = Integer.parseInt(currentDate);

        // Generate a random number between 1000 and 9999
        int randomNumber = random.nextInt(RANDOM_NUMBER_MAX - RANDOM_NUMBER_MIN + 1) + RANDOM_NUMBER_MIN;

        // Combine the date number and random number to form the registration number
        String registrationNumber = String.valueOf(dateNumber) + String.valueOf(randomNumber);

        return registrationNumber;
    }
}
