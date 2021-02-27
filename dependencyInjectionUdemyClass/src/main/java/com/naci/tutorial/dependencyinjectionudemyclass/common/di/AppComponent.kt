package com.naci.tutorial.dependencyinjectionudemyclass.common.di

import android.app.Application
import com.naci.tutorial.dependencyinjectionudemyclass.networking.StackoverflowApi
import dagger.Component
import retrofit2.Retrofit

@Component(modules = [AppModule::class])
interface AppComponent {

    fun application(): Application

    fun retrofit(): Retrofit

    fun stackoverflowApi(): StackoverflowApi
}