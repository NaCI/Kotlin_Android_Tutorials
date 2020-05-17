package com.example.udacitytutorial5.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * We need to use factory design pattern in order to use constructor on view models
  */
class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }

}