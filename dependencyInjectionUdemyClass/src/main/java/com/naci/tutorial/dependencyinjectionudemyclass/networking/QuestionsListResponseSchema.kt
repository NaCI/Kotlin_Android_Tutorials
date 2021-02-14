package com.naci.tutorial.dependencyinjectionudemyclass.networking

import com.google.gson.annotations.SerializedName
import com.naci.tutorial.dependencyinjectionudemyclass.questions.Question

class QuestionsListResponseSchema(@SerializedName("items") val questions: List<Question>)