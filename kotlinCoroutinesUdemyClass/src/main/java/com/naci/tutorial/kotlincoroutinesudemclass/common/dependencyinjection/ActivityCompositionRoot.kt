package com.naci.tutorial.kotlincoroutinesudemclass.common.dependencyinjection

import androidx.fragment.app.FragmentActivity
import com.naci.tutorial.kotlincoroutinesudemclass.R
import com.naci.tutorial.kotlincoroutinesudemclass.common.ScreensNavigator
import com.naci.tutorial.kotlincoroutinesudemclass.common.ToolbarDelegate
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.coroutinescancellation.CancellableBenchmarkUseCase
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.design.BenchmarkUseCase
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.noncancellable.CustomersDao
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.noncancellable.MakeCustomerPremiumUseCase
import com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.noncancellable.PremiumCustomersEndpoint
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise1.GetReputationEndpoint
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise4.FactorialUseCase
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise6.Exercise6BenchmarkUseCase
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise6.PostBenchmarkResultsEndpoint
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

    val factorialUseCase get() = FactorialUseCase()

    val benchmarkUseCase get() = BenchmarkUseCase()

    val cancellableBenchmarkUseCase get() = CancellableBenchmarkUseCase()

    private val postBenchmarkResultsEndpoint get() = PostBenchmarkResultsEndpoint()

    val exercise6BenchmarkUseCase get() = Exercise6BenchmarkUseCase(postBenchmarkResultsEndpoint)

    private val premiumCustomersEndpoint get() = PremiumCustomersEndpoint()
    private val customersDao get() = CustomersDao()
    val makeCustomerPremiumUseCase
        get() = MakeCustomerPremiumUseCase(
            premiumCustomersEndpoint,
            customersDao
        )
}