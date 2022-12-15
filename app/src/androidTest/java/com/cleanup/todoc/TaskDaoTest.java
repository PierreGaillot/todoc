package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.TudocDatabase;
import com.cleanup.todoc.database.entities.Project;
import com.cleanup.todoc.database.entities.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class TaskDaoTest {

    // -- DATABASE --
    private TudocDatabase database;

    // -- DEMO DATA --
    private static final long PROJECT_ID = 1;
    private static final Project PROJECT_DEMO = new Project(PROJECT_ID, "Project Name", 0xFFA3CED2);
    private static final Task TASK_DEMO = new Task(PROJECT_ID, "Tache 1", 123);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                        TudocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        this.database.close();
    }

    @Test
    public void createTask() throws InterruptedException {
        // -- BEFORE --
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().createTask(TASK_DEMO);

        Task task = database.taskDao().getTasks().get(0);
        // -- TEST --
        assertEquals(TASK_DEMO.getName(), task.getName());
        assertEquals(TASK_DEMO.getProjectId(), task.getProjectId());
    }


    @Test
    public void getTasksById() throws InterruptedException {
        // -- BEFORE --
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 1", 123));
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 2", 124));
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 3", 125));

        List<Task> tasks =database.taskDao().getTasksByProject(PROJECT_DEMO.getId());
        // -- TEST --
        assertEquals(tasks.size(), 3);
    }


    @Test
    public void getTasks() throws InterruptedException {
        // -- BEFORE --
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 1", 123));
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 2", 124));
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 3", 125));

        List<Task> tasks =database.taskDao().getTasks();
        // -- TEST --
        assertEquals(tasks.size(), 3);
    }

    @Test
    public void deleteTask() throws InterruptedException {
        // -- BEFORE --
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 1", 123));
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 2", 124));
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 3", 125));

        List<Task> tasks = database.taskDao().getTasks();
        Task task =database.taskDao().getTasks().get(0);
        assertEquals(tasks.size(), 3);
        this.database.taskDao().deleteTask(task.getId());
        // -- TEST --
        assertEquals(2, database.taskDao().getTasks().size());
    }


    @Test
    public void deleteAllTasks() throws InterruptedException {
        // -- BEFORE --
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 1", 123));
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 2", 124));
        this.database.taskDao().createTask(new Task(PROJECT_ID, "Tache 3", 125));

        assertEquals(3, database.taskDao().getTasks().size());
        this.database.taskDao().deleteAllTasks();
        // -- TEST --
        assertTrue(this.database.taskDao().getTasks().isEmpty());
    }

}
