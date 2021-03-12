package com.naci.tutorial.dependencyinjectionudemyclass.questions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
        @SerializedName("title") val title: String,
        @SerializedName("question_id") val id: String
) : Parcelable