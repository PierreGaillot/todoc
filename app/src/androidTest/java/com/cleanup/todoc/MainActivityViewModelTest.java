package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.database.TudocDatabase;
import com.cleanup.todoc.database.entities.Task;
import com.cleanup.todoc.ui.MainActivityViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityViewModelTest {

    Task demoTask1 = new Task(1L, "projet1", 123);
    Task demoTask2 = new Task(2L, "projet2", 124);
    Task demoTask3 = new Task(3L, "projet3", 125);

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private MainActivityViewModel viewModel;

    @Before
    public void before() {
        AppExecutors.setExecutor(new UnitTestExecutor());

        TudocDatabase.setInstance(
                Room.inMemoryDatabaseBuilder(
                                ApplicationProvider.getApplicationContext(),
                                TudocDatabase.class)
                        .allowMainThreadQueries()
                        .build()
        );
        viewModel = new MainActivityViewModel();
    }

    @After
    public void closeDatabase() {
        TudocDatabase.getInstance().close();
    }

    @Test
    public void when_viewModel_is_not_init__the_projects_are_not_created() {
        viewModel.getProjects().observeForever(projects -> Assert.assertEquals(0, projects.size()));
    }

    @Test
    public void when_viewModel_is_init__the_projects_are_created() {
        viewModel.initViewModel();
        viewModel.getProjects().observeForever(projects -> Assert.assertEquals(3, projects.size()));
    }

    @Test
    public void getTasks() {
        viewModel.initViewModel();
        viewModel.createTask("projet1", 1, 123);
        viewModel.createTask("projet2", 1, 124);
        viewModel.createTask("projet3", 1, 125);

        viewModel.getTasks().observeForever(taskWithProjects -> Assert.assertEquals(3, taskWithProjects.size()));
    }

    @Test
    public void getProjectByTask(){
        // Before init ViewModel for generate 3 projects
        viewModel.initViewModel();
        // create 3 tasks, one by project
        viewModel.createTask(demoTask1.getName(), 1L, demoTask1.getCreationTimestamp());
        viewModel.createTask(demoTask2.getName(), 2L, demoTask2.getCreationTimestamp());
        viewModel.createTask(demoTask3.getName(), 3L, demoTask3.getCreationTimestamp());

        // TEST
        Assert.assertNotEquals(viewModel.getProjects().getValue().get(1), viewModel.getProjectForTask(demoTask1));
        Assert.assertEquals(viewModel.getProjects().getValue().get(0), viewModel.getProjectForTask(demoTask1));
        Assert.assertEquals(viewModel.getProjects().getValue().get(1), viewModel.getProjectForTask(demoTask2));
        Assert.assertEquals(viewModel.getProjects().getValue().get(2), viewModel.getProjectForTask(demoTask3));
    }




}