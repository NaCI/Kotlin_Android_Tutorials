package com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation

import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionDetailsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.ScreensNavigator
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.dialogs.DialogsNavigator
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.viewmvc.ViewMvcFactory
import dagger.Component

@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun screensNavigator(): ScreensNavigator

    fun dialogsNavigator(): DialogsNavigator

    fun viewMvcFactory(): ViewMvcFactory

    fun fetchQuestionsUseCase(): FetchQuestionsUseCase

    fun fetchQuestionDetailsUseCase(): FetchQuestionDetailsUseCase
}