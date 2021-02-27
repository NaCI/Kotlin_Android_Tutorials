package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.fragment

import androidx.fragment.app.Fragment
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation.PresentationComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation.PresentationModule
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity.BaseActivity

open class BaseFragment : Fragment() {

    private val presentationComponent: PresentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(
            PresentationModule()
        )
    }

    protected val injector get() = presentationComponent
}