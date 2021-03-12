package com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PresentationModule(private val savedStateRegistryOwner: SavedStateRegistryOwner) {

    // THIS IS JUST FOR DEMONSTRATION
    // NORMALLY DON'T PUT DATA STRUCTURES(like Int, String) to DEPENDENCY OBJECT GRAPH
    @Provides
    @Named("randomNumber")
    fun randomNumber(): Int = (0..7).random()

    @Provides
    fun savedStateRegistryOwner() = savedStateRegistryOwner
}