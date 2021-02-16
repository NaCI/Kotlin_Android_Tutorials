package com.naci.tutorial.dependencyinjectionudemyclass.screens.common

import android.app.Activity
import com.naci.tutorial.dependencyinjectionudemyclass.screens.questiondetails.QuestionDetailsActivity

class ScreensNavigator(private val activity: Activity) {

    fun navigateBack() {
        activity.onBackPressed()
    }

    fun toQuestionDetails(questionId: String) {
        QuestionDetailsActivity.start(activity, questionId)
    }
}