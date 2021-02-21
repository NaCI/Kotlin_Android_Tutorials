package com.naci.tutorial.dependencyinjectionudemyclass.common.composition

import android.app.Activity
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionDetailsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.ScreensNavigator

class ActivityCompositionRoot(
    private val activity: Activity,
    private val appCompositionRoot: AppCompositionRoot
) {

    val screensNavigator by lazy {
        ScreensNavigator(activity)
    }

    private val stackoverflowApi get() = appCompositionRoot.stackoverflowApi

    val fetchQuestionsUseCase by lazy {
        FetchQuestionsUseCase(stackoverflowApi)
    }
    val fetchQuestionDetailsUseCase by lazy {
        FetchQuestionDetailsUseCase(stackoverflowApi)
    }
}