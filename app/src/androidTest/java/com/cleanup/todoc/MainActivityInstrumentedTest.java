package com.cleanup.todoc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import androidx.lifecycle.ViewModelProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.ui.MainActivityViewModel;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void removeAllTask() {
        rule.getScenario().onActivity(activity -> {
           MainActivityViewModel viewModel = new ViewModelProvider(activity).get(MainActivityViewModel.class);
           viewModel.initViewModel();
           viewModel.deleteAllTasks();
        });
    }

    @Test
    public void addAndRemoveTask() {
        ViewInteraction lblNoTask = onView(withId(R.id.lbl_no_task));
        ViewInteraction listTasks = onView(withId(R.id.list_tasks));
        ViewInteraction fab_add_task = onView(withId(R.id.fab_add_task));
        ViewInteraction txt_task_name = onView(withId(R.id.txt_task_name));
        ViewInteraction validateTaskBtn = onView(withId(android.R.id.button1));

        // Create a task
        fab_add_task.perform(click());
        txt_task_name.perform(replaceText("Tâche example"));
        validateTaskBtn.perform(click());

        // Check that lblTask is not displayed anymore
        lblNoTask.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        // Check that recyclerView is displayed
        listTasks.check(matches(isDisplayed()));

        // Check that it contains one element only
        rule.getScenario().onActivity(activity -> {
            RecyclerView listTaskRV = activity.findViewById(R.id.list_tasks);
            assertEquals(1, listTaskRV.getAdapter().getItemCount());
        });

        // Click at delete btn
        onView(withId(R.id.img_delete)).perform(click());

        // Check that lblTask is displayed
        lblNoTask.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        // Check that recyclerView is not displayed anymore
        listTasks.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

    }


    @Test
    public void sortTasks() {

        ViewInteraction validateButton = onView(withId(android.R.id.button1));
        ViewInteraction txt_task_name = onView(withId(R.id.txt_task_name));
        ViewInteraction FabAddTask = onView(withId(R.id.fab_add_task));
        ViewInteraction action_filter = onView(withId(R.id.action_filter));

        FabAddTask.perform(click());
        txt_task_name.perform(replaceText("aaa Tâche example"));
        validateButton.perform(click());
        FabAddTask.perform(click());
        txt_task_name.perform(replaceText("zzz Tâche example"));
        validateButton.perform(click());
        FabAddTask.perform(click());
        txt_task_name.perform(replaceText("hhh Tâche example"));
        validateButton.perform(click());

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort alphabetical
        action_filter.perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));

        // Sort alphabetical inverted
        action_filter.perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));

        // Sort old first
        action_filter.perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort recent first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_recent_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));

    }
}
