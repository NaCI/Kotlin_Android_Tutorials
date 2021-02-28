package com.naci.tutorial.dependencyinjectionudemyclass.questions

import com.google.gson.annotations.SerializedName
import com.naci.tutorial.dependencyinjectionudemyclass.users.User

data class QuestionWithBody(
        @SerializedName("title") val title: String,
        @SerializedName("question_id") val id: String,
        @SerializedName("body") val body: String,
        @SerializedName("owner") val owner: User
)