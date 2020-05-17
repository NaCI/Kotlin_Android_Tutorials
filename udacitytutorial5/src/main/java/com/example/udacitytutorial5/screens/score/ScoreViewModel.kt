package com.example.udacitytutorial5.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ScoreViewModel(finalScore: Int): ViewModel() {

    private val _score = MutableLiveData<Int>(finalScore)
    val score: LiveData<Int>
        get() = _score

    private val _eventPlayAgain = MutableLiveData(false)
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        Timber.i("Final Score is $finalScore")
    }

    fun onEventPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onEventPlayAgainCompleted() {
        _eventPlayAgain.value = false
    }
}