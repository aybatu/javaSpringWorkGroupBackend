/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.meeting;

import com.aybatu.workgroup.workgroup.task.Task;
import java.util.Date;

/**
 *
 * @author aybatukerkukluoglu
 */
public class Project {
    
    private String title;
    private String description;
    private Task[] tasks;
    private Date startDate;
    private Date finishDate;

    public Project(String title, String description, Task[] tasks, Date startDate, Date finishDate) {
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
}
