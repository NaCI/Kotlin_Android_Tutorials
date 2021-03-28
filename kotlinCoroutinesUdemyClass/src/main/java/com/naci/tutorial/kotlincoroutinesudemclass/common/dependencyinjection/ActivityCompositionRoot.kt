package com.naci.tutorial.kotlincoroutinesudemclass.common.dependencyinjection

import androidx.fragment.app.FragmentActivity
import com.naci.tutorial.kotlincoroutinesudemclass.R
import com.naci.tutorial.kotlincoroutinesudemclass.common.ScreensNavigator
import com.naci.tutorial.kotlincoroutinesudemclass.common.ToolbarDelegate
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise1.GetReputationEndpoint
import com.ncapdevi.fragnav.FragNavController

class ActivityCompositionRoot(
    private val activity: FragmentActivity,
    private val appCompositionRoot: ApplicationCompositionRoot
) {

    val toolbarManipulator get() = activity as ToolbarDelegate

    val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(fragNavController)
    }

    private val fragNavController get() = FragNavController(fragmentManager, R.id.frame_content)

    private val fragmentManager get() = activity.supportFragmentManager

    val getReputationEndpoint get() = GetReputationEndpoint()
}