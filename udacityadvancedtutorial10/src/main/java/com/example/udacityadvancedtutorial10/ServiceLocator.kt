package com.example.udacityadvancedtutorial10

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.udacityadvancedtutorial10.data.source.DefaultTasksRepository
import com.example.udacityadvancedtutorial10.data.source.TasksDataSource
import com.example.udacityadvancedtutorial10.data.source.TasksRepository
import com.example.udacityadvancedtutorial10.data.source.local.TasksLocalDataSource
import com.example.udacityadvancedtutorial10.data.source.local.ToDoDatabase
import com.example.udacityadvancedtutorial10.data.source.remote.TasksRemoteDataSource
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private val lock = Any()

    private var database: ToDoDatabase? = null

    @Volatile
    var tasksRepository: TasksRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): TasksRepository {
        synchronized(this) {
            return tasksRepository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): TasksRepository {
        val tempRepository =
            DefaultTasksRepository(TasksRemoteDataSource, createTasksLocalDataSource(context))
        tasksRepository = tempRepository
        return tempRepository
    }

    private fun createTasksLocalDataSource(context: Context): TasksDataSource {
        val database = database ?: createDatabase(context)
        return TasksLocalDataSource(database.taskDao())
    }

    private fun createDatabase(context: Context): ToDoDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java, "Tasks.db"
        ).build()
        database = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            runBlocking {
                TasksRemoteDataSource.deleteAllTasks()
            }

            // Clear all data to avoid test pollution
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            tasksRepository = null
        }
    }
}