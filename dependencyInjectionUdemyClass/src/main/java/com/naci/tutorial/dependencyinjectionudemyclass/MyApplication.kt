package com.naci.tutorial.dependencyinjectionudemyclass

import android.app.Application
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.app.AppComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.app.AppModule
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.app.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}