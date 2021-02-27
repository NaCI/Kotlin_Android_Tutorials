package com.naci.tutorial.dependencyinjectionudemyclass.common.di.app

import android.app.Application
import com.naci.tutorial.dependencyinjectionudemyclass.networking.StackoverflowApi
import dagger.Component
import retrofit2.Retrofit

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun application(): Application

    fun retrofit(): Retrofit

    fun stackoverflowApi(): StackoverflowApi
}