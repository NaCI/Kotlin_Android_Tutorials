package com.naci.tutorial.dependencyinjectionudemyclass.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation.PresentationScope
import javax.inject.Inject
import javax.inject.Provider

@PresentationScope
class ViewModelFactory @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return providers[modelClass]?.get() as? T
            ?: throw IllegalArgumentException("unsupported viewmodel type: $modelClass")
    }
}
