package com.example.udacityadvancedtutorial10.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.udacityadvancedtutorial10.R
import com.example.udacityadvancedtutorial10.ServiceLocator
import com.example.udacityadvancedtutorial10.data.Task
import com.example.udacityadvancedtutorial10.data.source.FakeAndroidTestRepository
import com.example.udacityadvancedtutorial10.data.source.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {

    private lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTaskDetails_DisplayedInUi() = runBlockingTest {
        // GIVEN - Add active (incomplete) task to the DB
        val activeTest = Task("Active Task", "Task Description Wulululu", false)
        repository.saveTask(activeTest)

        // WHEN - Details fragment launched to display task
        val bundle = TaskDetailFragmentArgs(activeTest.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
        Thread.sleep(2000)
    }
}