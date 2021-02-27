package com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation

import com.naci.tutorial.dependencyinjectionudemyclass.screens.questiondetails.QuestionDetailsActivity
import com.naci.tutorial.dependencyinjectionudemyclass.screens.questionslist.QuestionsListFragment
import dagger.Component

@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(fragment: QuestionsListFragment)
    fun inject(activity: QuestionDetailsActivity)
}