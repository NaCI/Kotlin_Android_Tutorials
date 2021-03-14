package com.naci.tutorial.dependencyinjectionudemyclass.common.di.app

import com.naci.tutorial.dependencyinjectionudemyclass.common.di.qualifier.Retrofit1
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.qualifier.Retrofit2
import com.naci.tutorial.dependencyinjectionudemyclass.networking.StackoverflowApi
import com.naci.tutorial.dependencyinjectionudemyclass.networking.UrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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