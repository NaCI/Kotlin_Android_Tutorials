package com.naci.tutorial.dependencyinjectionudemyclass.networking

import com.google.gson.annotations.SerializedName
import com.naci.tutorial.dependencyinjectionudemyclass.questions.QuestionWithBody

data class SingleQuestionResponseSchema(@SerializedName("items") val questions: List<QuestionWithBody>) {
    val question: QuestionWithBody get() = questions[0]
}