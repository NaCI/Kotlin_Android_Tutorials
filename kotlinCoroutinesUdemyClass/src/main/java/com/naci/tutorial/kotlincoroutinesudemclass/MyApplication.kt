package com.naci.tutorial.kotlincoroutinesudemclass

import android.app.Application
import com.naci.tutorial.kotlincoroutinesudemclass.common.dependencyinjection.ApplicationCompositionRoot

class MyApplication : Application() {

    val applicationCompositionRoot = ApplicationCompositionRoot()

    override fun onCreate() {
        super.onCreate()
    }
}