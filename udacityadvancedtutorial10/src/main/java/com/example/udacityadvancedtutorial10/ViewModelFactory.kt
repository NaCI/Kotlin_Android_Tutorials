package com.example.udacityadvancedtutorial10

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.udacityadvancedtutorial10.addedittask.AddEditTaskViewModel
import com.example.udacityadvancedtutorial10.data.source.TasksRepository
import com.example.udacityadvancedtutorial10.statistics.StatisticsViewModel
import com.example.udacityadvancedtutorial10.taskdetail.TaskDetailViewModel
import com.example.udacityadvancedtutorial10.tasks.TasksViewModel

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(StatisticsViewModel::class.java) ->
                    StatisticsViewModel(tasksRepository)
                isAssignableFrom(TaskDetailViewModel::class.java) ->
                    TaskDetailViewModel(tasksRepository)
                isAssignableFrom(AddEditTaskViewModel::class.java) ->
                    AddEditTaskViewModel(tasksRepository)
                isAssignableFrom(TasksViewModel::class.java) ->
                    TasksViewModel(tasksRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}