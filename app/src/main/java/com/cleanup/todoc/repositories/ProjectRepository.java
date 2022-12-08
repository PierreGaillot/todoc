package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TudocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    public static ProjectRepository getInstance(){return new ProjectRepository(TudocDatabase.getInstance().projectDao());}


    // -- DAO --
    private final ProjectDao projectDao;

    // -- CONSTRUCTOR --
    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    /** -------------
     * -- METHODES --
     * --------------
     */

    // GET ALL PROJECTS
    public LiveData<List<Project>> getProjects(){return this.projectDao.getProjects();}

    // CREATE PROJECT
    public void createProject(Project project){
        this.projectDao.createProject(project);
    }

    // DELETE PROJECT
    public void deleteProject(Project project){this.projectDao.deleteProject(project);}

    // DELETE ALL PROJECT
    public void deleteProjects(){this.projectDao.deleteProjects();}
}
