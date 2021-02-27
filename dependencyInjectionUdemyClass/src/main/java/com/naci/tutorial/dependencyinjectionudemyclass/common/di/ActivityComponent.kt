package com.naci.tutorial.dependencyinjectionudemyclass.common.di

import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.naci.tutorial.dependencyinjectionudemyclass.networking.StackoverflowApi
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.ScreensNavigator
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent {

    fun screensNavigator(): ScreensNavigator

    fun layoutInflater(): LayoutInflater

    fun fragmentManager(): FragmentManager

    fun stackoverflowApi(): StackoverflowApi
}