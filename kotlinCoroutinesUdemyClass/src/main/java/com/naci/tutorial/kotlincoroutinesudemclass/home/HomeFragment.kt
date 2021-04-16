package com.naci.tutorial.kotlincoroutinesudemclass.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.naci.tutorial.kotlincoroutinesudemclass.R
import com.naci.tutorial.kotlincoroutinesudemclass.common.BaseFragment

class HomeFragment : BaseFragment(), HomeArrayAdapter.Listener {

    override val screenTitle get() = "Coroutines Course"

    private lateinit var listScreensReachableFromHome: ListView
    private lateinit var adapterScreensReachableFromHome: HomeArrayAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        adapterScreensReachableFromHome = HomeArrayAdapter(requireContext(), this)

        listScreensReachableFromHome = view.findViewById(R.id.list_screens)
        listScreensReachableFromHome.adapter = adapterScreensReachableFromHome

        adapterScreensReachableFromHome.addAll(*ScreenReachableFromHome.values())
        adapterScreensReachableFromHome.notifyDataSetChanged()

        return view
    }

    override fun onScreenClicked(screenReachableFromHome: ScreenReachableFromHome) {
        when (screenReachableFromHome) {
            ScreenReachableFromHome.UI_THREAD_DEMO -> screensNavigator.toUiThreadDemo()
            ScreenReachableFromHome.BACKGROUND_THREAD_DEMO -> screensNavigator.toBackgroundThreadDemo()
            ScreenReachableFromHome.BASIC_COROUTINES_DEMO -> screensNavigator.toBasicCoroutinesDemo()
            ScreenReachableFromHome.EXERCISE_1 -> screensNavigator.toExercise1()
            ScreenReachableFromHome.EXERCISE_2 -> screensNavigator.toExercise2()
            ScreenReachableFromHome.CONCURRENT_COROUTINES_DEMO -> screensNavigator.toConcurrentCoroutines()
            ScreenReachableFromHome.EXERCISE_3 -> screensNavigator.toExercise3()
            ScreenReachableFromHome.VIEWMODEL_DEMO -> screensNavigator.toViewModel()
            ScreenReachableFromHome.EXERCISE_4 -> screensNavigator.toExercise4()
            ScreenReachableFromHome.DESIGN_DEMO -> screensNavigator.toDesignDemo()
            ScreenReachableFromHome.COROUTINES_CANCELLATION_DEMO -> screensNavigator.toCoroutinesCancellationDemo()
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return HomeFragment()
        }
    }
}