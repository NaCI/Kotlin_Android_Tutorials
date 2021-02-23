package com.naci.tutorial.dependencyinjectionudemyclass.common.di

import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionDetailsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.dialogs.DialogsNavigator
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.viewmvc.ViewMvcFactory

class PresentationCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    private val layoutInflater get() = activityCompositionRoot.layoutInflater

    private val fragmentManager get() = activityCompositionRoot.fragmentManager

    private val stackoverflowApi get() = activityCompositionRoot.stackoverflowApi

    val screensNavigator get() = activityCompositionRoot.screensNavigator

    val dialogsNavigator by lazy {
        DialogsNavigator(fragmentManager)
    }

    val viewMvcFactory by lazy {
        ViewMvcFactory(layoutInflater)
    }
    val fetchQuestionsUseCase by lazy {
        FetchQuestionsUseCase(stackoverflowApi)
    }
    val fetchQuestionDetailsUseCase by lazy {
        FetchQuestionDetailsUseCase(stackoverflowApi)
    }
}