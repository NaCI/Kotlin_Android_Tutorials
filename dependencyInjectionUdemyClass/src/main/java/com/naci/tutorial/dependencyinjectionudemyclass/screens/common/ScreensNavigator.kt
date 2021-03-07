package com.naci.tutorial.dependencyinjectionudemyclass.screens.common

import androidx.appcompat.app.AppCompatActivity
import com.naci.tutorial.dependencyinjectionudemyclass.screens.questiondetails.QuestionDetailsActivity
import com.naci.tutorial.dependencyinjectionudemyclass.screens.viewmodel.ViewModelActivity

class ScreensNavigator(private val activity: AppCompatActivity) {

    fun navigateBack() {
        activity.onBackPressed()
    }

    fun toQuestionDetails(questionId: String) {
        QuestionDetailsActivity.start(activity, questionId)
    }

    fun toViewModel() {
        ViewModelActivity.start(activity)
    }
}