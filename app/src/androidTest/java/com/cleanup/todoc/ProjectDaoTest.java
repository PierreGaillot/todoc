package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.TudocDatabase;
import com.cleanup.todoc.database.entities.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    // -- DATABASE --
    private TudocDatabase database;
    // -- DEMO DATA --
    private static final long PROJECT_ID = 1;
    private static final Project PROJECT_DEMO = new Project(PROJECT_ID, "Project Test", 0xFFA3CED2);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception{
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TudocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb(){
        this.database.close();
    }

    // -- TESTS --

    @Test
    public void insertAndGetProject() throws InterruptedException {
        // BEFORE : Add new project
        this.database.projectDao().createProject(PROJECT_DEMO);

        // TEST
        Project project = database.projectDao().getProjects().get(0);
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getAllProjects() throws InterruptedException {
        // BEFORE : Add new project
        this.database.projectDao().createProject(new Project(1, "project 1", 123));
        this.database.projectDao().createProject(new Project(2, "project 2", 123));
        this.database.projectDao().createProject(new Project(3, "project 3", 123));

        List<Project> projects =database.projectDao().getProjects();
        assertEquals(projects.size(), 3);
    }

    @Test
    public void deleteProject() throws InterruptedException {
        // BEFORE : Add new project
        this.database.projectDao().createProject(PROJECT_DEMO);
        Project project = database.projectDao().getProjects().get(0);
        // BEFORE TEST : assert if this project exist
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);

        // -- TEST --
        this.database.projectDao().deleteProject(PROJECT_DEMO);
        assertTrue("the project was deleted", database.projectDao().getProjects().isEmpty());
    }

    @Test
    public void replaceProject() throws InterruptedException {
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.projectDao().createProject(PROJECT_DEMO);

        assertEquals(1, database.projectDao().getProjects().size());
    }

    @Test
    public void deleteAllProjects() throws InterruptedException {
        // BEFORE : Add 3 new projects
        this.database.projectDao().createProject(new Project(1, "project 1", 123));
        this.database.projectDao().createProject(new Project(2, "project 2", 123));
        this.database.projectDao().createProject(new Project(3, "project 3", 123));

        assertEquals(3, database.projectDao().getProjects().size());
        this.database.projectDao().deleteProjects();
        assertEquals(0,database.projectDao().getProjects().size());
    }

}
