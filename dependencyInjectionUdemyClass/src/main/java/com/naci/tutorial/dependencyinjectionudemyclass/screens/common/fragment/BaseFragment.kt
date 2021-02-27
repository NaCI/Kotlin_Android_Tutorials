package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.fragment

import androidx.fragment.app.Fragment
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.DaggerPresentationComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.Injector
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.PresentationComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.PresentationModule
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity.BaseActivity

open class BaseFragment : Fragment() {

    private val presentationComponent: PresentationComponent by lazy {
        DaggerPresentationComponent
            .builder()
            .presentationModule(
                PresentationModule((requireActivity() as BaseActivity).activityComponent)
            )
            .build()
    }

    protected val injector get() = Injector(presentationComponent)
}