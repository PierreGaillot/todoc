package com.cleanup.todoc.model;

import com.cleanup.todoc.database.entities.Project;
import com.cleanup.todoc.database.entities.Task;

public class TaskWithProject {

    private Task task;
    private Project project;

    public TaskWithProject(Task task, Project project) {
        this.task = task;
        this.project = project;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
