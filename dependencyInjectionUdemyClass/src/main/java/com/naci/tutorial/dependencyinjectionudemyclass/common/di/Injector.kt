package com.naci.tutorial.dependencyinjectionudemyclass.common.di

import com.naci.tutorial.dependencyinjectionudemyclass.screens.questiondetails.QuestionDetailsActivity
import com.naci.tutorial.dependencyinjectionudemyclass.screens.questionslist.QuestionsListFragment

class Injector(private val compositionRoot: PresentationCompositionRoot) {

    fun inject(fragment: QuestionsListFragment) {
        fragment.screensNavigator = compositionRoot.screensNavigator
        fragment.dialogsNavigator = compositionRoot.dialogsNavigator
        fragment.viewMvcFactory = compositionRoot.viewMvcFactory
        fragment.fetchQuestionsUseCase = compositionRoot.fetchQuestionsUseCase
    }

    fun inject(activity: QuestionDetailsActivity) {
        activity.screensNavigator = compositionRoot.screensNavigator
        activity.dialogsNavigator = compositionRoot.dialogsNavigator
        activity.viewMvcFactory = compositionRoot.viewMvcFactory
        activity.fetchQuestionDetailsUseCase = compositionRoot.fetchQuestionDetailsUseCase
    }


}