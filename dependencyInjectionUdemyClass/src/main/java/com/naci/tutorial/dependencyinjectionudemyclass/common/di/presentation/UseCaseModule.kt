package com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UseCaseModule {

    // THIS IS JUST FOR DEMONSTRATION
    // NORMALLY DON'T PUT DATA STRUCTURES(like Int, String) to DEPENDENCY OBJECT GRAPH
    @Provides
    @Named("randomDayOfWeek")
    fun randomDayOfWeek(@Named("randomNumber") randomNumber: Int): String {
        return when (randomNumber) {
            0 -> "Monday"
            1 -> "Tuesday"
            2 -> "Wednesday"
            3 -> "Thursday"
            4 -> "Friday"
            5 -> "Saturday"
            6 -> "Sunday"
            else -> "Apocalypse Day"
        }
    }
}