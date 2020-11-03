package com.example.udacityadvancedtutorial10.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.udacityadvancedtutorial10.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ToDoDatabase

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertTaskAndGetById() = runBlockingTest {
        // GIVEN - Insert a task.
        val task = Task("Title", "Description")
        database.taskDao().insertTask(task)

        // WHEN - Get the task by id from the database.
        val loadedTask = database.taskDao().getTaskById(task.id)

        // THEN - The loaded data contains the expected values.
        assertThat<Task>(loadedTask as Task, notNullValue())
        assertThat(loadedTask.id, `is`(task.id))
        assertThat(loadedTask.title, `is`(task.title))
        assertThat(loadedTask.description, `is`(task.description))
        assertThat(loadedTask.isCompleted, `is`(task.isCompleted))
    }

    @Test
    fun updateTaskAndGetById() = runBlockingTest {
        // 1. Insert a task into the DAO.
        val task = Task("Title", "Description")
        database.taskDao().insertTask(task)

        // 2. Update the task by creating a new task with the same ID but different attributes.
        val updated = Task("NewTitle", "NewDescription", false, task.id)
        database.taskDao().updateTask(updated)

        // 3. Check that when you get the task by its ID, it has the updated values.
        val loadedTask = database.taskDao().getTaskById(task.id)
        assertThat<Task>(loadedTask as Task, notNullValue())
        assertThat(loadedTask.id, `is`(updated.id))
        assertThat(loadedTask.title, `is`(updated.title))
        assertThat(loadedTask.description, `is`(updated.description))
        assertThat(loadedTask.isCompleted, `is`(updated.isCompleted))
    }
}