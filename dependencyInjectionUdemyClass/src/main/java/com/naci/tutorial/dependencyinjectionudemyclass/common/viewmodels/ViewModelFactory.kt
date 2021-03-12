package com.naci.tutorial.dependencyinjectionudemyclass.common.viewmodels

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation.PresentationScope
import javax.inject.Inject
import javax.inject.Provider

@PresentationScope
class ViewModelFactory @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
    savedStateRegistryOwner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        val viewModel = providers[modelClass]?.get()
            ?: throw IllegalArgumentException("unsupported viewmodel type: $modelClass")

        if (viewModel is SavedStateViewModel) {
            viewModel.init(handle)
        }

        @Suppress("UNCHECKED_CAST")
        return viewModel as T
    }
}
