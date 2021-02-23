package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.naci.tutorial.dependencyinjectionudemyclass.MyApplication
import com.naci.tutorial.dependencyinjectionudemyclass.common.composition.ActivityCompositionRoot
import com.naci.tutorial.dependencyinjectionudemyclass.common.composition.PresentationCompositionRoot

open class BaseActivity : AppCompatActivity() {

    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    private val activityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }

    val compositionRoot by lazy {
        PresentationCompositionRoot(activityCompositionRoot)
    }

}