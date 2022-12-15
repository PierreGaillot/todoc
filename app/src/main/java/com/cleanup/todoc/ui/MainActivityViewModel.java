package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.AppExecutors;
import com.cleanup.todoc.database.entities.Project;
import com.cleanup.todoc.database.entities.Task;
import com.cleanup.todoc.model.SortMethod;
import com.cleanup.todoc.model.TaskWithProject;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivityViewModel extends ViewModel {

    private final TaskRepository mTaskRepository = TaskRepository.getInstance();
    private final ProjectRepository mProjectRepository = ProjectRepository.getInstance();
    private final Executor executor = AppExecutors.getInstance();

    private SortMethod sortMethod = SortMethod.NONE;

    private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<TaskWithProject>> getTasks() {
        return Transformations.map(tasks, input -> {
            ArrayList<TaskWithProject> result = new ArrayList<>();

            for (Task task : input) {
                result.add(new TaskWithProject(
                        task,
                        getProjectForTask(task)
                ));
            }

            return result;
        });
    }

    public Project getProjectForTask(Task task) {
        for (Project project : getProjects().getValue()) {
            if(project.getId() == task.getProjectId())
                return project;
        }
        return null;
    }

    public void initViewModel() {
        createProject(1L, "Projet Tartampion", 0xFFEADAD1);
        createProject(2L, "Projet Lucidia", 0xFFB4CDBA);
        createProject(3L, "Projet Circus", 0xFFA3CED2);

        executor.execute(() -> {
            projects.postValue(mProjectRepository.getProjects());
            refreshTasks();
        });
    }

    private final MutableLiveData<List<Project>> projects = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Project>> getProjects() {
        return projects;
    }

    private void createProject(long projectId, String projectName, int projectColor) {
        executor.execute(() -> {
            mProjectRepository.createProject(new Project(projectId, projectName, projectColor));
        });
    }

    // Insert a new task
    public void createTask(String text, long projectId, long creationTimestamp){
        executor.execute(() -> {
            mTaskRepository.createTask(new Task(projectId, text, creationTimestamp));
            refreshTasks();
        });
    }

    // Delete a task
    public void deleteTask(Task task){
        executor.execute(() -> {
            mTaskRepository.deleteTask(task.getId());
            refreshTasks();
        });
    }

    // Refresh tasks List
    private void refreshTasks() {
        ArrayList<Task> list = new ArrayList<>(mTaskRepository.getTasks());

        switch (sortMethod) {
            case ALPHABETICAL:
                Collections.sort(list, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(list, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(list, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(list, new Task.TaskOldComparator());
                break;
        }
        tasks.postValue(list);
    }

    // Delete all tasks
    public void deleteAllTasks(){
        executor.execute(() -> {
            mTaskRepository.deleteAllTasks();
            refreshTasks();
        });
    }

    // Delete all projects
    public void deleteProjects() {
        executor.execute(mProjectRepository::deleteProjects);
    }

    public void sortTasks(SortMethod sortMethod) {
        this.sortMethod = sortMethod;
        executor.execute(this::refreshTasks);
    }
}
