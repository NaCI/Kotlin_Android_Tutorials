package com.naci.tutorial.dependencyinjectionudemyclass.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naci.tutorial.dependencyinjectionudemyclass.screens.viewmodel.MyViewModel
import com.naci.tutorial.dependencyinjectionudemyclass.screens.viewmodel.MyViewModel2
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val myViewModel: Provider<MyViewModel>,
    private val myViewModel2: Provider<MyViewModel2>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MyViewModel::class.java -> myViewModel.get() as T
            MyViewModel2::class.java -> myViewModel2.get() as T
            else -> throw RuntimeException("unsupported viewmodel type: $modelClass")
        }
    }
}
