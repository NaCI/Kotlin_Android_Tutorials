package com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UseCaseModule {

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