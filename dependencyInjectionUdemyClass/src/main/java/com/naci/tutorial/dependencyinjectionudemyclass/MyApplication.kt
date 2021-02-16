package com.naci.tutorial.dependencyinjectionudemyclass

import android.app.Application
import com.naci.tutorial.dependencyinjectionudemyclass.networking.StackoverflowApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {

    // init retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val stackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    override fun onCreate() {
        super.onCreate()
    }

}