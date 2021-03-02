package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.dialogs

import androidx.fragment.app.DialogFragment
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation.PresentationComponent
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity.BaseActivity

abstract class BaseDialog : DialogFragment() {

    private val presentationComponent: PresentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent()
    }

    protected val injector get() = presentationComponent

}