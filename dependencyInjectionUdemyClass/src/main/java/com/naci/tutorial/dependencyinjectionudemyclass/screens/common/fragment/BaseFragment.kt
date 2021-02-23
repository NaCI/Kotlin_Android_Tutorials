package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.fragment

import androidx.fragment.app.Fragment
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.Injector
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.PresentationCompositionRoot
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity.BaseActivity

open class BaseFragment: Fragment() {

    private val compositionRoot by lazy {
        PresentationCompositionRoot((requireActivity() as BaseActivity).activityCompositionRoot)
    }

    protected val injector get() = Injector(compositionRoot)
}