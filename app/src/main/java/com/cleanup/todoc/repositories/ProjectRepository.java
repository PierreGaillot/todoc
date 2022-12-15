package com.cleanup.todoc.repositories;

import com.cleanup.todoc.database.TudocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.entities.Project;
import com.cleanup.todoc.database.entities.Task;

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
    public List<Project> getProjects(){return this.projectDao.getProjects();}

    // GET ALL PROJECTS
    public Project getProjectForTask(Task task){return this.projectDao.getById(task.getProjectId());}

    // CREATE PROJECT
    public void createProject(Project project){
        this.projectDao.createProject(project);
    }

    // DELETE PROJECT
    public void deleteProject(Project project){this.projectDao.deleteProject(project);}

    // DELETE ALL PROJECT
    public void deleteProjects(){this.projectDao.deleteProjects();}
}
