package com.example.udacityadvancedtutorial10.data.source

import com.example.udacityadvancedtutorial10.MainCoroutineRule
import com.example.udacityadvancedtutorial10.data.Result
import com.example.udacityadvancedtutorial10.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultTasksRepositoryTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSource

    private lateinit var tasksRepository: DefaultTasksRepository

    @Before
    fun createRepository() {
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())

        tasksRepository = DefaultTasksRepository(
            tasksRemoteDataSource, tasksLocalDataSource, Dispatchers.Main
        )
    }

    /*
     * Generally, only create one TestCoroutineDispatcher to run a test.
     * Whenever you call runBlockingTest, it will create a new TestCoroutineDispatcher if you don't specify one.
     * MainCoroutineRule includes a TestCoroutineDispatcher.
     * So, to ensure that you don't accidentally create multiple instances of TestCoroutineDispatcher,
     * use the mainCoroutineRule.runBlockingTest instead of just runBlockingTest.
     */
    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
        // WHEN tasks are requested from the tasks repository
        val tasks = tasksRepository.getTasks(true) as Result.Success

        // THEN tasks are loaded from the remote data source
        assertThat(tasks.data, IsEqual(remoteTasks))
    }
}