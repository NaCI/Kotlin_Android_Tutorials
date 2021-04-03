package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.naci.tutorial.kotlincoroutinesudemclass.R
import com.naci.tutorial.kotlincoroutinesudemclass.common.BaseFragment
import com.naci.tutorial.kotlincoroutinesudemclass.common.ThreadInfoLogger
import com.naci.tutorial.kotlincoroutinesudemclass.home.ScreenReachableFromHome

class ViewModelDemoFragment : BaseFragment() {

    override val screenTitle get() = ScreenReachableFromHome.VIEWMODEL_DEMO.description

    private lateinit var btnTrackTime: Button
    private lateinit var txtElapsedTime: TextView

    private val myViewModel by viewModels<MyViewModel> {
        MyViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_viewmodel_demo, container, false)

        txtElapsedTime = view.findViewById(R.id.txt_elapsed_time)
        btnTrackTime = view.findViewById(R.id.btn_track_time)

        btnTrackTime.setOnClickListener {
            logThreadInfo("button callback")
            myViewModel.toggleTrackElapsedTime()
        }

        myViewModel.elapsedTime.observe(viewLifecycleOwner, Observer { elapsedTime ->
            txtElapsedTime.text = elapsedTime.toString()
        })

        myViewModel.isTrackingTime.observe(viewLifecycleOwner, Observer { isTrackingTime ->
            btnTrackTime.text = if (isTrackingTime) {
                "Stop tracking"
            } else {
                "Start tracking"
            }
        })

        return view
    }

    override fun onStop() {
        logThreadInfo("onStop()")
        super.onStop()
    }

    private fun logThreadInfo(message: String) {
        ThreadInfoLogger.logThreadInfo(message)
    }

    companion object {
        fun newInstance(): Fragment {
            return ViewModelDemoFragment()
        }
    }
}