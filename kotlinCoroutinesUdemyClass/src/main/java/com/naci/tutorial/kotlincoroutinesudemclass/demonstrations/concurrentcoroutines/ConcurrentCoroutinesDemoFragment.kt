package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.concurrentcoroutines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.naci.tutorial.kotlincoroutinesudemclass.R
import com.naci.tutorial.kotlincoroutinesudemclass.common.BaseFragment
import com.naci.tutorial.kotlincoroutinesudemclass.common.ThreadInfoLogger
import com.naci.tutorial.kotlincoroutinesudemclass.home.ScreenReachableFromHome
import kotlinx.coroutines.*

class ConcurrentCoroutinesDemoFragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    private var job: Job? = null
    private var jobCounter: Job? = null

    override val screenTitle get() = ScreenReachableFromHome.CONCURRENT_COROUTINES_DEMO.description

    private lateinit var btnStart: Button
    private lateinit var txtRemainingTime: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ui_thread_demo, container, false)

        txtRemainingTime = view.findViewById(R.id.txt_remaining_time)

        btnStart = view.findViewById(R.id.btn_start)
        btnStart.setOnClickListener {
            logThreadInfo("button callback")

            val benchmarkDurationSeconds = 5
            jobCounter = coroutineScope.launch {
                updateRemainingTime(benchmarkDurationSeconds)
            }
            job = coroutineScope.launch {
                btnStart.isEnabled = false
                val iterationsCount = executeBenchmark(benchmarkDurationSeconds)
                Toast.makeText(requireContext(), "$iterationsCount", Toast.LENGTH_SHORT).show()
                btnStart.isEnabled = true
            }
        }

        return view
    }

    override fun onStop() {
        logThreadInfo("onStop()")
        super.onStop()
        job?.run {
            cancel()
            btnStart.isEnabled = true
        }
        jobCounter?.run {
            cancel()
            txtRemainingTime.text = "done!"
        }
    }

    private suspend fun executeBenchmark(benchmarkDurationSeconds: Int) =
        withContext(Dispatchers.Default) {
            logThreadInfo("benchmark started")

            val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L

            var iterationsCount: Long = 0
            while (System.nanoTime() < stopTimeNano) {
                iterationsCount++
            }

            logThreadInfo("benchmark completed")

            iterationsCount
        }

    private suspend fun updateRemainingTime(remainingTimeSeconds: Int) {
        for (time in remainingTimeSeconds downTo 0) {
            logThreadInfo("updateRemainingTime: $time seconds")
            if (time > 0) {
                txtRemainingTime.text = "$time seconds remaining"
                delay(1000)
            } else {
                txtRemainingTime.text = "done!"
            }
        }
    }

    private fun logThreadInfo(message: String) {
        ThreadInfoLogger.logThreadInfo(message)
    }

    companion object {
        fun newInstance(): Fragment {
            return ConcurrentCoroutinesDemoFragment()
        }
    }
}