package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {


    /**
     * Get all task from database
     * @return
     */
    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getProjects();


    /**
     * Create a new Project.
     * @param project
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    /**
     * Delete a Project.
     * @param project
     */
    @Delete
    void deleteProject(Project project);


    /**
     * Delete all projects
     */
    @Query("DELETE FROM Project")
    void deleteProjects();
}
