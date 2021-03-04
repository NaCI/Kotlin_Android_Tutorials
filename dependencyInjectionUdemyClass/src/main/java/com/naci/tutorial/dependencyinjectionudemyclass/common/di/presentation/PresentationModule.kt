package com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PresentationModule {

    // THIS IS JUST FOR SAMPLE
    // NORMALLY DON'T PUT DATA STRUCTURES(like Int, String) to DEPENDENCY OBJECT GRAPH
    @Provides
    @Named("randomNumber")
    fun randomNumber(): Int = (0..7).random()
}