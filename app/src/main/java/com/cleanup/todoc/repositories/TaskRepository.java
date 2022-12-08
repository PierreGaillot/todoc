package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TudocDatabase;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    public static TaskRepository getInstance(){ return new TaskRepository(TudocDatabase.getInstance().taskDao());}


    // -- DAO --
    private final TaskDao taskDao;

    // -- CONSTRUCTOR --
    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    /** -------------
     * -- METHODES --
     * --------------
     */

    // -- GET TASK BY PROJECT ID --
    public LiveData<List<Task>> getTaskByProject(long projectId){return this.taskDao.getTasksByProject(projectId);}

    // -- GET TASKS --
    public LiveData<List<Task>> getTask(){return this.taskDao.getTasks();}

    // -- CREATE TASK --
    public void createTask(Task task){this.taskDao.createTask(task);}

    // -- DELETE TASK --
    public void deleteTask(long taskId){ this.taskDao.deleteTask(taskId);}

    // -- DELETE ALL TASKS --
    public void deleteAllTasks(){ this.taskDao.deleteAllTasks();}

}
