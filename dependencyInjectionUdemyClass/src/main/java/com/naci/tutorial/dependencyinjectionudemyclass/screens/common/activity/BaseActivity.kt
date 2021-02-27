package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.naci.tutorial.dependencyinjectionudemyclass.MyApplication
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.ActivityComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.ActivityModule
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.DaggerActivityComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation.DaggerPresentationComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation.PresentationComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation.PresentationModule

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

    protected val injector get() = presentationComponent

}