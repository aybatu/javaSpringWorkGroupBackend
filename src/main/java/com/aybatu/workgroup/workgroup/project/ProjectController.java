/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.project;

import com.aybatu.workgroup.workgroup.company.Company;
import com.aybatu.workgroup.workgroup.company.CompanyRepository;
import com.aybatu.workgroup.workgroup.company.CompanyService;
import com.aybatu.workgroup.workgroup.company.employee.Employee;
import com.aybatu.workgroup.workgroup.task.Task;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aybatukerkukluoglu
 */
@RestController
public class ProjectController {

    private final ProjectService projectService;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/{registrationNumber}/projects")
    public ResponseEntity<?> createProject(@PathVariable String registrationNumber, @RequestBody Project project) {
        // Retrieve the company using the registration number
        Company company = companyRepository.findByRegistrationNumber(registrationNumber);
        if (company == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
        }

        // Check if a project with the same title already exists in the company's projects
        Project isProjectExists = company.getProjects().stream()
                .filter(existingProject -> existingProject.getTitle().equalsIgnoreCase(project.getTitle()))
                .findFirst()
                .orElse(null);

        if (isProjectExists != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Project title is registered before. Please change the title and try again.");
        }

        // Get the list of employees from the company
        List<Employee> employeeList = company.getEmployeeAccounts();

        // Loop through each task in the new project
        for (Task task : project.getTasks()) {
            // Loop through each assigned employee for the task
            for (Employee assignedEmployee : task.getAssignedEmployees()) {
                // Find the corresponding employee from the employee list based on email address
                Employee foundEmployee = employeeList.stream()
                        .filter(employee -> employee.getEmailAddress().equals(assignedEmployee.getEmailAddress()))
                        .findFirst()
                        .orElse(null);

                // If the employee is found, add the task to their userTasks list
                if (foundEmployee != null) {
                    foundEmployee.addUserTask(task);
                }
            }
        }

        // Add the newly created project to the company's projects
        company.addNewProject(project);

        companyRepository.save(company);
        return ResponseEntity.ok("Project is added successfully.");
    }

}
