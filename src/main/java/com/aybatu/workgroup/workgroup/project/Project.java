/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.project;

import com.aybatu.workgroup.workgroup.task.Task;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author aybatukerkukluoglu
 */
@Document(collection = "Projects")
public class Project {
    @Id
    private String title;
    private String description;
    private List<Task> tasks;
    private String startDate;
    private String endDate;

    public Project(String title, String description, List<Task> tasks, String startDate, String endDate) {
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    public void addTask(Task task) {
        tasks.add(task);
    }
    
}
