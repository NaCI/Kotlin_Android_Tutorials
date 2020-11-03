package com.example.udacityadvancedtutorial10

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.udacityadvancedtutorial10.data.source.local.ToDoDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TodoDatabaseRule : TestWatcher() {
    lateinit var database: ToDoDatabase

    override fun starting(description: Description?) {
        super.starting(description)
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        database.close()
    }
}