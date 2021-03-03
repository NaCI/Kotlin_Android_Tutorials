package com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.ScreensNavigator
import dagger.Module
import dagger.Provides

@Module
object ActivityModule {

    @ActivityScope
    @Provides
    fun screensNavigator(activity: AppCompatActivity) = ScreensNavigator(activity)

    @Provides
    fun layoutInflater(activity: AppCompatActivity) = LayoutInflater.from(activity)

    @Provides
    fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager
}