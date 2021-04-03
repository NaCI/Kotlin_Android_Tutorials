package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naci.tutorial.kotlincoroutinesudemclass.common.ThreadInfoLogger
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long> = _elapsedTime

    private val _isTrackingTime = MutableLiveData<Boolean>()
    val isTrackingTime: LiveData<Boolean> = _isTrackingTime

    fun toggleTrackElapsedTime() {
        val isTrackingTimeNow = isTrackingTime.value
        logThreadInfo("trackElapsedTime(); isTrackingTimeNow = $isTrackingTimeNow")
        if (isTrackingTimeNow == null || !isTrackingTimeNow) {
            startTracking()
        } else {
            stopTracking()
        }
    }

    private fun startTracking() = viewModelScope.launch {
        logThreadInfo("startTracking()")
        _isTrackingTime.postValue(true)

        val startTimeNano = System.nanoTime()

        while (true) {
            val elapsedTimeSeconds = (System.nanoTime() - startTimeNano) / 1_000_000_000L
            logThreadInfo("elapsed time: $elapsedTimeSeconds seconds")
            _elapsedTime.postValue(elapsedTimeSeconds)
            delay(1000)
        }
    }

    private fun stopTracking() {
        logThreadInfo("stopTracking()")
        _isTrackingTime.postValue(false)
        viewModelScope.coroutineContext.cancelChildren()
    }

    // Framework automatically cancels viewModelScope onCleared - so we don't need to handle it again
    /*override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }*/

    private fun logThreadInfo(message: String) {
        ThreadInfoLogger.logThreadInfo(message)
    }

}