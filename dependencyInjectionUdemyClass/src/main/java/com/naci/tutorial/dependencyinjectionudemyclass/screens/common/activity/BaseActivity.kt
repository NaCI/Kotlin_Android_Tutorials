package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.naci.tutorial.dependencyinjectionudemyclass.MyApplication
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.ActivityComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.ActivityModule
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation.PresentationComponent

open class BaseActivity : AppCompatActivity() {

    private val appComponent get() = (application as MyApplication).appComponent

    val activityComponent: ActivityComponent by lazy {
        appComponent.newActivityComponentBuilder()
            .activity(this)
            .activityModule(ActivityModule)
            .build()
    }

    private val presentationComponent: PresentationComponent by lazy {
        activityComponent.newPresentationComponent()
    }

    protected val injector get() = presentationComponent

}