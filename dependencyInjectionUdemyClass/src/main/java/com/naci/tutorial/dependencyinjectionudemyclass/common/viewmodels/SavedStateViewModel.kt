package com.naci.tutorial.dependencyinjectionudemyclass.common.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

abstract class SavedStateViewModel : ViewModel() {
    abstract fun init(savedStateHandle: SavedStateHandle)
}