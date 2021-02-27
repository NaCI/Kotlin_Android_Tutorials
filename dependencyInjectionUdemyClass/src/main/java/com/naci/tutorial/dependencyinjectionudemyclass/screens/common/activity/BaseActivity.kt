package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.naci.tutorial.dependencyinjectionudemyclass.MyApplication
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.*

open class BaseActivity : AppCompatActivity() {

    private val appComponent get() = (application as MyApplication).appComponent

    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent
            .builder()
            .activityModule(ActivityModule(this, appComponent))
            .build()
    }

    private val presentationComponent: PresentationComponent by lazy {
        DaggerPresentationComponent
            .builder()
            .presentationModule(PresentationModule(activityComponent))
            .build()
    }

    protected val injector get() = Injector(presentationComponent)

}