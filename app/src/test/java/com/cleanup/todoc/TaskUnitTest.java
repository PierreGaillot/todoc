package com.cleanup.todoc;

import static org.junit.Assert.assertSame;

import com.cleanup.todoc.database.entities.Task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Unit tests for tasks
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task( 1, "aaa", 123);
        final Task task2 = new Task( 2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task( 1, "aaa", 123);
        final Task task2 = new Task( 2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task( 1, "aaa", 123);
        final Task task2 = new Task( 2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task( 1, "aaa", 123);
        final Task task2 = new Task( 2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}