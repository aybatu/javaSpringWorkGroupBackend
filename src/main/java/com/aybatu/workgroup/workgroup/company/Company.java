/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company;


import com.aybatu.workgroup.workgroup.admin.Admin;
import com.aybatu.workgroup.workgroup.company.employee.Employee;
import com.aybatu.workgroup.workgroup.manager.Manager;
import com.aybatu.workgroup.workgroup.meeting.Meeting;
import com.aybatu.workgroup.workgroup.project.Project;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 *
 * @author aybatukerkukluoglu
 */
@Document(collection = "companies")
public class Company {
    @Id
    private String registrationNumber;
    private String companyName;
    private Admin ownerAccount;
    private List<Employee> employeeAccounts;
    private List<Manager> managerAccounts;
    private List<Project> projects;
    private List<Meeting> meetings;

    public Company(String companyName, Admin ownerAccount) {
        this.companyName = companyName;
        this.ownerAccount = ownerAccount;
        this.employeeAccounts = new ArrayList<>();
        this.managerAccounts = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.meetings = new ArrayList<>();
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Admin getOwnerAccount() {
        return ownerAccount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Employee> getEmployeeAccounts() {
        return employeeAccounts;
    }

    public List<Manager> getManagerAccounts() {
        return managerAccounts;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setRegistrationNumber(String registerNo) {
        registrationNumber = registerNo;
    }
}
