package com.naci.tutorial.dependencyinjectionudemyclass.common.di.repository

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    /*@Provides
    @ViewModelScoped
    fun provideXRepository(stackoverflowApi: StackoverflowApi): XRepository {
        return XRepository(stackoverflowApi)
    }*/
}