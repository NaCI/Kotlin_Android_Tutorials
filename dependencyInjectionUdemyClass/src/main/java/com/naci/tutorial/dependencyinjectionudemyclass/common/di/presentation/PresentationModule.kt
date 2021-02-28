package com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation

import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.dialogs.DialogsNavigator
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.viewmvc.ViewMvcFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PresentationModule {

    @Provides
    fun dialogsNavigator(fragmentManager: FragmentManager) = DialogsNavigator(fragmentManager)

    @Provides
    fun viewMvcFactory(layoutInflater: LayoutInflater) = ViewMvcFactory(layoutInflater)

    @Provides
    @Named("randomNumber")
    fun randomNumber(): Int = (0..7).random()
}