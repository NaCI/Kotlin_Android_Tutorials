package com.naci.tutorial.kotlincoroutinesudemclass.common

import android.os.Bundle
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.backgroundthread.BackgroundThreadDemoFragment
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.basiccoroutines.BasicCoroutinesDemoFragment
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.concurrentcoroutines.ConcurrentCoroutinesDemoFragment
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.uithread.UiThreadDemoFragment
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.viewmodel.ViewModelDemoFragment
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise1.Exercise1Fragment
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise2.Exercise2Fragment
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise3.Exercise3Fragment
import com.naci.tutorial.kotlincoroutinesudemclass.home.HomeFragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavController.RootFragmentListener

class ScreensNavigator(private val fragNavController: FragNavController) {

    fun init(savedInstanceState: Bundle?) {
        fragNavController.rootFragmentListener = object : RootFragmentListener {
            override val numberOfRootFragments get() = 1

            override fun getRootFragment(index: Int) = HomeFragment.newInstance()
        }

        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    fun onSaveInstanceState(outState: Bundle?) {
        fragNavController.onSaveInstanceState(outState)
    }

    fun isRootScreen() = fragNavController.isRootFragment

    fun navigateBack(): Boolean {
        if (fragNavController.isRootFragment) {
            return false
        } else {
            fragNavController.popFragment()
            return true
        }
    }

    fun navigateUp() {
        fragNavController.popFragment()
    }

    fun toHomeScreen() {
        fragNavController.clearStack()
        fragNavController.pushFragment(HomeFragment.newInstance())
    }

    fun toUiThreadDemo() {
        fragNavController.pushFragment(UiThreadDemoFragment.newInstance())
    }

    fun toBackgroundThreadDemo() {
        fragNavController.pushFragment(BackgroundThreadDemoFragment.newInstance())
    }

    fun toBasicCoroutinesDemo() {
        fragNavController.pushFragment(BasicCoroutinesDemoFragment.newInstance())
    }

    fun toExercise1() {
        fragNavController.pushFragment(Exercise1Fragment.newInstance())
    }

    fun toExercise2() {
        fragNavController.pushFragment(Exercise2Fragment.newInstance())
    }

    fun toConcurrentCoroutines() {
        fragNavController.pushFragment(ConcurrentCoroutinesDemoFragment.newInstance())
    }

    fun toExercise3() {
        fragNavController.pushFragment(Exercise3Fragment.newInstance())
    }

    fun toViewModel() {
        fragNavController.pushFragment(ViewModelDemoFragment.newInstance())
    }
}