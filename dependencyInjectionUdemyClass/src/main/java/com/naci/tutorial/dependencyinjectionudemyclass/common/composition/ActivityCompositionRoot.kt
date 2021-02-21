package com.naci.tutorial.dependencyinjectionudemyclass.common.composition

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionDetailsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.ScreensNavigator
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.dialogs.DialogsNavigator
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.viewmvc.ViewMvcFactory

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
) {

    val screensNavigator by lazy {
        ScreensNavigator(activity)
    }

    private val fragmentManager get() = activity.supportFragmentManager

    val dialogsNavigator by lazy {
        DialogsNavigator(fragmentManager)
    }

    private val stackoverflowApi get() = appCompositionRoot.stackoverflowApi

    private val layoutInflater get() = LayoutInflater.from(activity)

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