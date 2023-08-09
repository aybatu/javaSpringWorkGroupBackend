/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.project;

import com.aybatu.workgroup.workgroup.task.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private boolean isProjectComplete;
    private List<Task> completedTasksRequests;
    private List<Task> completedTasks;

    public Project(String title, String description, List<Task> tasks, String startDate, String endDate) {
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isProjectComplete = false;
        this.completedTasksRequests = new ArrayList<>();
        this.completedTasks = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public List<Task> getCompletedTasksRequests() {
        return completedTasksRequests;
    }

    public boolean isIsProjectComplete() {
        return isProjectComplete;
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
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

    public void completeProject() {
        isProjectComplete = true;
    }
    public void completeTask(Task task) {
        completedTasksRequests.remove(task);
        completedTasks.add(task);
    }
    public void addTaskCompleteReuqest(Task task) {
        tasks.remove(task);
        completedTasksRequests.add(task);
    }
    public void rejectTaskCompleteRequest(Task task) {
        completedTasksRequests.remove(task);
        tasks.add(task);
                
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Project project = (Project) obj;
        return Objects.equals(title, project.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
