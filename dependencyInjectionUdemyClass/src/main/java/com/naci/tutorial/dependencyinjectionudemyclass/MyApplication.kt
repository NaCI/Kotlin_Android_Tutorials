package com.naci.tutorial.dependencyinjectionudemyclass

import android.app.Application
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.AppCompositionRoot

class MyApplication : Application() {

    lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        super.onCreate()
        appCompositionRoot = AppCompositionRoot()
    }

}