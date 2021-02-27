package com.naci.tutorial.dependencyinjectionudemyclass.common.di

import android.app.Application
import com.naci.tutorial.dependencyinjectionudemyclass.Constants
import com.naci.tutorial.dependencyinjectionudemyclass.networking.StackoverflowApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Application object which composes services and resides at the root of the dependencies tree
 */
@Module
class AppModule(private val application: Application) {

    /**
     * "lazy" : this keyword means that the variable will be initialize when it will be used for the first time
     *
     * unlike "get() = " assignment lazy variables are not going to created for every call. there will be only one variable (like singleton)
     *
     * and lazy is thread-safe
     *
     * whenever we want to service global, we need to use lazy for better performance
     */

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val stackoverflowApi: StackoverflowApi by lazy {
        retrofit.create(StackoverflowApi::class.java)
    }

    @Provides
    fun application() = application

    @Provides
    fun stackoverflowApi(retrofit: Retrofit): StackoverflowApi = stackoverflowApi
}