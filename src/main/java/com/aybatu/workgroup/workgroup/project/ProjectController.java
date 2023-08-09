/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.project;

import com.aybatu.workgroup.workgroup.company.Company;
import com.aybatu.workgroup.workgroup.company.CompanyService;
import com.aybatu.workgroup.workgroup.company.employee.Employee;
import com.aybatu.workgroup.workgroup.task.Task;
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
public class ProjectController {

    private final ProjectService projectService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/{registrationNumber}/projects")
    public ResponseEntity<?> createProject(@PathVariable String registrationNumber, @RequestBody Project project) {
        // Retrieve the company using the registration number
        Company company = companyService.getCompanyByRegistrationNumber(registrationNumber);
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

        companyService.saveCompany(company);
        return ResponseEntity.ok("Project is added successfully.");
    }
    
    @PutMapping("{registrationNumber}/updateProject")
    public ResponseEntity<?> updateProject(@PathVariable String registrationNumber, @RequestBody UpdateProjectRequest updateProjectRequest) {
        Company company = companyService.getCompanyByRegistrationNumber(registrationNumber);
        
        if(company == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company registration number cannot be found. Please relogin your account.");
        }
        
        List<Project> projects = company.getProjects();
        List<Employee> employeeList = company.getEmployeeAccounts();
        
        Project oldProject = projects.stream()
                .filter(project -> project.getTitle().equals(updateProjectRequest.getOriginalProjectTitle()))
                .findFirst()
                .orElse(null);
                
        if(oldProject != null) {
            List<Task> oldProjectTasks = oldProject.getTasks();
            
            for(Task t: oldProjectTasks) {
                for(Employee e: t.getAssignedEmployees()) {
                    Employee foundEmployee = employeeList.stream()
                            .filter(assignedEmployee -> assignedEmployee.getEmailAddress().equals(e.getEmailAddress()))
                            .findFirst()
                            .orElse(null);
                    foundEmployee.removeUserTask(t);
                }
            }
            projects.remove(oldProject);
        }
        
           // Loop through each task in the new project
        for (Task task : updateProjectRequest.getUpdatedProject().getTasks()) {
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
        company.addNewProject(updateProjectRequest.getUpdatedProject());

        companyService.saveCompany(company);
        return ResponseEntity.ok("Project is updated successfully.");
        
    }
    
    @PutMapping("/{registrationNumber}/completeProject")
    public ResponseEntity<?> markProjectCompleted(@PathVariable String registrationNumber, @RequestBody Project project) {
        Company company = companyService.getCompanyByRegistrationNumber(registrationNumber);
        
        List<Project> projects = company.getProjects();
        
        int projectIndex = projects.indexOf(project);
        Project foundProject = projects.get(projectIndex);
        
        List<Task> taskList = foundProject.getTasks();
        
        for(Task t: taskList) {
            if (t.isIsTaskCompleted() == false) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The project has task or tasks not completed yet. You cannot complete the project before all tasks are completed.");
            }
        }
        foundProject.completeProject();
        company.completeProject(foundProject);
      
        companyService.saveCompany(company);
        return ResponseEntity.ok("Project is completed.");
    }
    
    
     @PutMapping("/{registrationNo}/acceptTaskCompletion")
    public ResponseEntity<?> acceptTaskCompletion(@PathVariable String registrationNo, @RequestBody CompleteProjectTaskRequest completeTaskRequest) {
        Company company = companyService.getCompanyByRegistrationNumber(registrationNo);
        
        List<Project> projects = company.getProjects();
        
        Project foundProject = projects.stream()
                .filter(project -> project.getTitle().equalsIgnoreCase(completeTaskRequest.getProject().getTitle()))
                .findFirst()
                .orElse(null);
        
        if(foundProject == null) {
            return ResponseEntity.ofNullable("Project is not found.");
        }
        
        Task foundTask = foundProject.getCompletedTasksRequests().stream()
                .filter(task -> task.getTitle().equalsIgnoreCase(completeTaskRequest.getTask().getTitle()))
                .findFirst()
                .orElse(null);
        
        if(foundTask == null) {
            return ResponseEntity.ofNullable("Task is not found");
        }
        
        foundProject.completeTask(foundTask);
        companyService.saveCompany(company);
        
        return ResponseEntity.ok("Task is completed.");
    }

    @PutMapping("/{registrationNo}/rejectTaskCompletion")
    public ResponseEntity<?> rejectTaskCompletion(@PathVariable String registrationNo, @RequestBody CompleteProjectTaskRequest rejectTaskRequest) {
        Company company = companyService.getCompanyByRegistrationNumber(registrationNo);
        
        List<Project> projects = company.getProjects();
        List<Employee> companyEmployeeList = company.getEmployeeAccounts();
        
        Project foundProject = projects.stream()
                .filter(project -> project.getTitle().equalsIgnoreCase(rejectTaskRequest.getProject().getTitle()))
                .findFirst()
                .orElse(null);
        
        if(foundProject == null) {
            return ResponseEntity.ofNullable("Project is no longer available.");
        }

        
        List<Task> taskList = foundProject.getCompletedTasksRequests();
        Task foundTask = taskList.stream()
                .filter(task -> task.getTitle().equalsIgnoreCase(rejectTaskRequest.getTask().getTitle()))
                .findFirst()
                .orElse(null);
        
        if(foundTask == null) {
            return ResponseEntity.ofNullable("Task is no longer available");
        }
        
        foundProject.rejectTaskCompleteRequest(foundTask);
        
        List<Employee> assignedEmployees = rejectTaskRequest.getTask().getAssignedEmployees();
        for(Employee e: assignedEmployees) {
            Employee foundEmployee = companyEmployeeList.stream() 
                    .filter(employee -> employee.getEmailAddress().equalsIgnoreCase(e.getEmailAddress()))
                    .findFirst()
                    .orElse(null);
            
            if(foundEmployee != null) {
                foundEmployee.addUserTask(rejectTaskRequest.getTask());
            }
        }
        
        
        companyService.saveCompany(company);
        return ResponseEntity.ok("Task is completion is rejected.");
    }
}
