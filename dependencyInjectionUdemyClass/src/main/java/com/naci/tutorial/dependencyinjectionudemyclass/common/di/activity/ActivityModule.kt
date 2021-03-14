package com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.ScreensNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @ActivityScoped
    @Provides
    fun screensNavigator(activity: AppCompatActivity) = ScreensNavigator(activity)

    @Provides
    fun layoutInflater(activity: Activity) = LayoutInflater.from(activity)

    @Provides
    fun appCompatActivity(activity: Activity) = activity as AppCompatActivity

    @Provides
    fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager
}