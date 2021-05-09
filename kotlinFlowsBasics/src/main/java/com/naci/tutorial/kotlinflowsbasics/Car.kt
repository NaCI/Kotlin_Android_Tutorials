package com.naci.tutorial.kotlinflowsbasics

import androidx.annotation.ColorInt

data class Car(
    var brand: String,
    @ColorInt val color: Int,
    val gear: Int,
    val door: Int,
    val isSport: Boolean
)
