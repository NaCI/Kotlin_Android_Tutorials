package com.naci.tutorial.kotlincoroutinesudemclass.common

import android.os.Bundle
import com.naci.tutorial.kotlincoroutinesudemclass.home.HomeFragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavController.RootFragmentListener
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.uithread.UiThreadDemoFragment

class ScreensNavigator(private val mFragNavController: FragNavController) {

    fun init(savedInstanceState: Bundle?) {
        mFragNavController.rootFragmentListener = object : RootFragmentListener {
            override val numberOfRootFragments get() = 1

            override fun getRootFragment(index: Int) = HomeFragment.newInstance()
        }

        mFragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    fun onSaveInstanceState(outState: Bundle?) {
        mFragNavController.onSaveInstanceState(outState)
    }

    fun isRootScreen() = mFragNavController.isRootFragment

    fun navigateBack(): Boolean {
        if (mFragNavController.isRootFragment) {
            return false
        } else {
            mFragNavController.popFragment()
            return true
        }
    }

    fun navigateUp() {
        mFragNavController.popFragment()
    }

    fun toHomeScreen() {
        mFragNavController.clearStack()
        mFragNavController.pushFragment(HomeFragment.newInstance())
    }

    fun toUiThreadDemonstration() {
        mFragNavController.pushFragment(UiThreadDemoFragment.newInstance())
    }

}