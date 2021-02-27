package com.naci.tutorial.dependencyinjectionudemyclass

import android.app.Application
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.AppComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.AppModule
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}