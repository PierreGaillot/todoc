package com.cleanup.todoc.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.database.entities.Task;

import java.util.List;

@Dao
public interface TaskDao {

    /**
     * @param task
     * @return
     */
    @Insert
    void createTask(Task task);

    /**
     * Get All Task from database
     * @return
     */
    @Query("SELECT * FROM Task")
    List<Task> getTasks();

    /**
     * Return LiveData list of Tasks by
     * @param projectId
     * @return
     */
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    List<Task> getTasksByProject(long projectId);

    /**
     *  Remove Task from database by taskId
     * @param taskId
     */
    @Query("DELETE FROM Task WHERE id = :taskId")
    void deleteTask(long taskId);

    @Query("DELETE FROM Task")
    void deleteAllTasks();

}
