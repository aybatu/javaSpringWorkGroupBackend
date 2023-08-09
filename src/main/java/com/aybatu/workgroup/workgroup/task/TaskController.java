/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.task;

import com.aybatu.workgroup.workgroup.company.Company;
import com.aybatu.workgroup.workgroup.company.CompanyService;
import com.aybatu.workgroup.workgroup.company.employee.Employee;
import com.aybatu.workgroup.workgroup.project.Project;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aybatukerkukluoglu
 */
@RestController
public class TaskController {
    private final TaskService taskService;
    private final CompanyService companyService;
    
    @Autowired
    public TaskController(TaskService taskService, CompanyService companyService) {
        this.taskService = taskService;
        this.companyService = companyService;
    }
    
    @PutMapping("/{registrationNo}/sendCompleteTaskRequest") 
    public ResponseEntity<?> completeTaskRequest(@PathVariable String registrationNo, @RequestBody TaskCompletionRequest taskCompleteRequest) {
            Company company = companyService.getCompanyByRegistrationNumber(registrationNo);
        
        List<Project> projects = company.getProjects();
        
        int projectIndex = projects.indexOf(taskCompleteRequest.getProject());
        Project foundProject = projects.get(projectIndex);
        
        if(foundProject == null) {
            return ResponseEntity.ofNullable("Project is not found");
        }
        
        List<Task> projectTasks = foundProject.getTasks();
        int taskIndex = projectTasks.indexOf(taskCompleteRequest.getTask());
        Task foundTask = projectTasks.get(taskIndex);
        
        if(foundTask == null) {
             return ResponseEntity.ofNullable("Task is not found");
        }
        
       for(Employee e: taskCompleteRequest.getTask().getAssignedEmployees()) {
        
        Employee foundEmployee = company.getEmployeeAccounts().stream()
                .filter(employee -> employee.getEmailAddress().equals(e.getEmailAddress()))
                .findFirst()
                .orElse(null);
        if(foundEmployee != null) {
            foundEmployee.getUserTasks().remove(taskCompleteRequest.getTask());
        }
       }
        
       
        foundProject.addTaskCompleteReuqest(foundTask);
        
        companyService.saveCompany(company);
        return ResponseEntity.ok("Task completion request is sent successfully");
    }
    
}
