package com.example.udacityadvancedtutorial10.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.udacityadvancedtutorial10.data.Result
import com.example.udacityadvancedtutorial10.data.Task

class FakeDataSource(var tasks: MutableList<Task>? = mutableListOf()) : TasksDataSource {
    override suspend fun getTasks(): Result<List<Task>> {
        tasks?.let {
            return Result.Success(ArrayList(it))
        }
        return Result.Error(Exception("Tasks not found!"))
    }

    override suspend fun saveTask(task: Task) {
        tasks?.add(task)
    }

    override suspend fun deleteAllTasks() {
        tasks?.clear()
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        return MutableLiveData<Result<List<Task>>>().apply { Result.Success(ArrayList(tasks)) }
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        val task = tasks?.find { it.id == taskId }
        task?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Task not found"))
    }

    override suspend fun completeTask(task: Task) {
        tasks?.find { it == task }?.isCompleted = true
    }

    override suspend fun completeTask(taskId: String) {
        tasks?.find { it.id == taskId }?.isCompleted = true
    }

    override suspend fun activateTask(task: Task) {
        tasks?.find { it == task }?.isCompleted = false
    }

    override suspend fun activateTask(taskId: String) {
        tasks?.find { it.id == taskId }?.isCompleted = false
    }

    override suspend fun clearCompletedTasks() {
        tasks?.removeIf { it.isCompleted }
    }

    override suspend fun deleteTask(taskId: String) {
        tasks?.removeIf { it.id == taskId }
    }

    override suspend fun refreshTasks() {
        TODO("Not yet implemented")
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTask(taskId: String) {
        TODO("Not yet implemented")
    }
}