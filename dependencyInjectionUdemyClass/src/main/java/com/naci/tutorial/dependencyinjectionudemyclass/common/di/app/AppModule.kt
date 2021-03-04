package com.naci.tutorial.dependencyinjectionudemyclass.common.di.app

import android.app.Application
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.qualifier.Retrofit1
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.qualifier.Retrofit2
import com.naci.tutorial.dependencyinjectionudemyclass.networking.StackoverflowApi
import com.naci.tutorial.dependencyinjectionudemyclass.networking.UrlProvider
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

    @Provides
    fun application() = application

    @Provides
    @AppScope
    @Retrofit1
    fun retrofit1(urlProvider: UrlProvider): Retrofit {
        return Retrofit.Builder()
            .baseUrl(urlProvider.getBaseUrl1())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    @Retrofit2
    fun retrofit2(urlProvider: UrlProvider): Retrofit {
        return Retrofit.Builder()
            .baseUrl(urlProvider.getBaseUrl2())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun urlProvider(): UrlProvider = UrlProvider()

    @Provides
    @AppScope
    fun stackoverflowApi(@Retrofit1 retrofit: Retrofit): StackoverflowApi =
        retrofit.create(StackoverflowApi::class.java)
}