package com.naci.tutorial.dependencyinjectionudemyclass.screens.common

import androidx.appcompat.app.AppCompatActivity
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.ActivityScope
import com.naci.tutorial.dependencyinjectionudemyclass.screens.questiondetails.QuestionDetailsActivity
import javax.inject.Inject

@ActivityScope
class ScreensNavigator @Inject constructor(private val activity: AppCompatActivity) {

    fun navigateBack() {
        activity.onBackPressed()
    }

    fun toQuestionDetails(questionId: String) {
        QuestionDetailsActivity.start(activity, questionId)
    }
}