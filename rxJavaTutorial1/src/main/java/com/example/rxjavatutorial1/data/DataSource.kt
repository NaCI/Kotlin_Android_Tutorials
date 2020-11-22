package com.example.rxjavatutorial1.data

class DataSource {
    companion object {
        fun createTaskList(): List<Task> {
            return mutableListOf<Task>(
                Task("Take out the trash", true, 3),
                Task("Walk the dog", false, 2),
                Task("Go to sleep", true, 1),
                Task("Play video game", false, 0),
                Task("Read a book", true, 5)
            )
        }
    }
}