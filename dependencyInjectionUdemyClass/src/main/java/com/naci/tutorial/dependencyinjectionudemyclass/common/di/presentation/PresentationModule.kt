package com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PresentationModule {

    @Provides
    @Named("randomNumber")
    fun randomNumber(): Int = (0..7).random()
}