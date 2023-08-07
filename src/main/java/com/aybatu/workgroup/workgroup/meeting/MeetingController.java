/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.meeting;

import com.aybatu.workgroup.workgroup.company.Company;
import com.aybatu.workgroup.workgroup.company.CompanyService;
import com.aybatu.workgroup.workgroup.company.employee.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aybatukerkukluoglu
 */
@RestController
public class MeetingController {
    private final MeetingService meetingService;
    private final CompanyService companyService;
    
    @Autowired
    public MeetingController(MeetingService meetingService, CompanyService companyService) {
       this.meetingService = meetingService; 
       this.companyService = companyService;
    }
    
    @PostMapping("/{registrationNumber}/createMeeting")
    public ResponseEntity<?> createMeeting(@PathVariable String registrationNumber, @RequestBody ScheduleMeetingRequest request) {
        Company company = companyService.getCompanyByRegistrationNumber(registrationNumber);
        
        if(company == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company is not found to register meeting.");
        }
        
        for(Employee e: request.getInvitedEmployeeList()) {
            Employee foundEmployee = company.getEmployeeAccounts().stream()
                    .filter(user -> user.getEmailAddress().equalsIgnoreCase(e.getEmailAddress()))
                    .findFirst()
                    .orElse(null);
            if(foundEmployee != null) {
                foundEmployee.addMeeting(request.getMeeting());
            }
        }
        
        company.addNewMeeting(request.getMeeting());
        companyService.saveCompany(company);
        return ResponseEntity.ok("Meeting is scheduled successfully.");
    }
    
    @PutMapping("/{registrationNumber}/updateMeeting")
    public ResponseEntity<?> updateMeeting(@PathVariable String registrationNumber, @RequestBody UpdateMeetingRequest request) {
        Company company = companyService.getCompanyByRegistrationNumber(registrationNumber);
        if(company == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company is not found");
        }
        
        List<Meeting> meetingList = company.getMeetings();
        Meeting originalMeeting = request.getOriginalMeeting();
        Meeting updatedMeeting = request.getMeeting();
        
        Meeting isMeetingExists = meetingList.stream()
                .filter(existingMeeting -> existingMeeting.getMeetingTitle().equalsIgnoreCase(updatedMeeting.getMeetingTitle()))
                .findFirst()
                .orElse(null);
        
        if (isMeetingExists != null && !originalMeeting.getMeetingTitle().equalsIgnoreCase(updatedMeeting.getMeetingTitle())) {
            String errorMessage = "There is already a meeting schedule with " + '"' +updatedMeeting.getMeetingTitle() +'"' + ". Please change the title and try again.";
            
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }

        
        Meeting foundOriginalMeeting = meetingList.stream()
                .filter(meeting -> meeting.getMeetingTitle().equalsIgnoreCase(originalMeeting.getMeetingTitle()))
                .findFirst()
                .orElse(null);
        
        List<Employee> employeeList = company.getEmployeeAccounts();
        
        for(Employee e: employeeList) {
            Meeting existingMeeting = e.getEmployeeInvitedMeetings().stream()
                    .filter(meetingToDelete -> meetingToDelete.getMeetingTitle().equalsIgnoreCase(foundOriginalMeeting.getMeetingTitle()))
                    .findFirst()
                    .orElse(null);
            
            if(existingMeeting != null) {
                e.removeMeeting(existingMeeting);
            }
        }
        
        company.removeMeeting(originalMeeting);
        
        Employee[] invitedEmployees = request.getInvitedEmployeeList();
        
        for(Employee e: invitedEmployees) {
           Employee employeeToInvite = employeeList.stream()
                    .filter(invitedEmployee -> invitedEmployee.getEmailAddress().equalsIgnoreCase(e.getEmailAddress()))
                    .findFirst()
                    .orElse(null);
           if(employeeToInvite != null) {
               employeeToInvite.addMeeting(updatedMeeting);
           }
        }
        
        company.addNewMeeting(updatedMeeting);
        
        companyService.saveCompany(company);
        
        return ResponseEntity.ok("Meeting changes are implemented successfully.");
    }
}
