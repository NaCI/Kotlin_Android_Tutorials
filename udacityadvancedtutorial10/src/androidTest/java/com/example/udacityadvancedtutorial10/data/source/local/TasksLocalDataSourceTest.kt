package com.example.udacityadvancedtutorial10.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.udacityadvancedtutorial10.TodoDatabaseRule
import com.example.udacityadvancedtutorial10.data.Result
import com.example.udacityadvancedtutorial10.data.Task
import com.example.udacityadvancedtutorial10.data.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class TasksLocalDataSourceTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var todoDatabaseRule = TodoDatabaseRule()

    private lateinit var localDataSource: TasksLocalDataSource

    @Before
    fun setupLocalDataSource() {
        localDataSource = TasksLocalDataSource(
            todoDatabaseRule.database.taskDao(),
            Dispatchers.Main
        )
    }

    @Test
    fun saveTask_retrievesTask() = runBlocking {
        // GIVEN - A new task saved in the database.
        val newTask = Task("title", "description", false)
        localDataSource.saveTask(newTask)

        // WHEN  - Task retrieved by ID.
        val result = localDataSource.getTask(newTask.id)

        // THEN - Same task is returned.
        assertThat(result.succeeded, `is`(true))
        result as Result.Success
        assertThat(result.data.title, `is`("title"))
        assertThat(result.data.description, `is`("description"))
        assertThat(result.data.isCompleted, `is`(false))
    }

    @Test
    fun completeTask_retrievedTaskIsComplete() = runBlocking {
        // 1. Save a new active task in the local data source.
        val newTask = Task("title", "description", false)
        localDataSource.saveTask(newTask)

        // 2. Mark it as complete.
        localDataSource.completeTask(newTask)

        // 3. Check that the task can be retrieved from the local data source and is complete.
        val result = localDataSource.getTask(newTask.id)

        assertThat(result.succeeded, `is`(true))
        result as Result.Success
        assertThat(result.data.isCompleted, `is`(true))

    }

}