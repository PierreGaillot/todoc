package com.cleanup.todoc.ui;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.MyApp;
import com.cleanup.todoc.database.TudocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends ViewModel {

    private TaskRepository mTaskRepository = TaskRepository.getInstance();
    private ProjectRepository mProjectRepository = ProjectRepository.getInstance();
    private Executor executor = Executors.newSingleThreadExecutor();


    public void initViewModel() {
        createProject(1L, "Projet Tartampion", 0xFFEADAD1);
        createProject(2L, "Projet Lucidia", 0xFFB4CDBA);
        createProject(3L, "Projet Circus", 0xFFA3CED2);
    }

    /**
     * Get all project
     */
    public LiveData<List<Project>> getProjects() {
        return mProjectRepository.getProjects();
    }

    public void createProject(long projectId, String projectName, int projectColor) {
        executor.execute(() -> {
            mProjectRepository.createProject(new Project(projectId, projectName, projectColor));
        });
    }

    /**
     * Get all Tasks
     * @return
     */
    public LiveData<List<Task>> getTasks() {
        return mTaskRepository.getTask();
    }

    /**
     * Insert a new task
     */
    public void createTask(String text, long projectId, long creationTimestamp){
        executor.execute(() -> {
            mTaskRepository.createTask(new Task(projectId, text, creationTimestamp));
        });
    }

    /**
     * Delete a task
     */
    public void deleteTask(Task task){
        executor.execute(() -> {
            mTaskRepository.deleteTask(task.getId());
        });
    }


    /**
     * Delete all tasks
     */
    public void deleteAllTasks(){
        executor.execute(() -> {
            mTaskRepository.deleteAllTasks();
        });
    }

//    public Project getProjectByTask(Task task){
//        executor.execute(() -> {
//            this.mProjectRepository.getProject().observe(this, new Observer<List<Project>>() {
//                @Override
//                public void onChanged(List<Project> projects) {
//                    for (Project project :
//                            projects) {
//                        if (task.getProjectId() == project.getId()){
//                            return project;
//                        }
//                    }
//                }
//            });
//
//        });
//    };


    /**
     * Delete all projects
     */
    public void deleteProjects() {
        executor.execute(() -> {
            mProjectRepository.deleteProjects();
        });
    }
    
}
