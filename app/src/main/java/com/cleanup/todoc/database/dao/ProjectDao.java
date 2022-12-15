package com.cleanup.todoc.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.database.entities.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    /**
     * Get all task from database
     * @return
     */
    @Query("SELECT * FROM Project")
    List<Project> getProjects();

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
     *  Remove Task from database by taskId
     * @param projectId
     */
    @Query("SELECT * FROM Project WHERE id=:projectId")
    Project getById(long projectId);

    /**
     * Delete all projects
     */
    @Query("DELETE FROM Project")
    void deleteProjects();
}
