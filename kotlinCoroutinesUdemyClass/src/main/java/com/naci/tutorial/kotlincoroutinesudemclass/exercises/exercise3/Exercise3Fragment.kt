package com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise3

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.naci.tutorial.kotlincoroutinesudemclass.R
import com.naci.tutorial.kotlincoroutinesudemclass.common.BaseFragment
import com.naci.tutorial.kotlincoroutinesudemclass.common.ThreadInfoLogger
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise1.GetReputationEndpoint
import com.naci.tutorial.kotlincoroutinesudemclass.home.ScreenReachableFromHome
import kotlinx.coroutines.*

class Exercise3Fragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override val screenTitle get() = ScreenReachableFromHome.EXERCISE_3.description

    private lateinit var edtUserId: EditText
    private lateinit var btnGetReputation: Button
    private lateinit var txtElapsedTime: TextView


    private lateinit var getReputationEndpoint: GetReputationEndpoint

    private var jobElapsedTime: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getReputationEndpoint = compositionRoot.getReputationEndpoint
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_3, container, false)

        txtElapsedTime = view.findViewById(R.id.txt_elapsed_time)

        edtUserId = view.findViewById(R.id.edt_user_id)
        edtUserId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnGetReputation.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btnGetReputation = view.findViewById(R.id.btn_get_reputation)
        btnGetReputation.setOnClickListener {
            logThreadInfo("button callback")
            jobElapsedTime = coroutineScope.launch {
                updateElapsedTime()
            }
            /*job = */coroutineScope.launch {
                btnGetReputation.isEnabled = false
                val reputation = getReputationForUser(edtUserId.text.toString())
                Toast.makeText(requireContext(), "reputation: $reputation", Toast.LENGTH_SHORT)
                    .show()
                btnGetReputation.isEnabled = true
                jobElapsedTime?.cancel()
            }
            /*coroutineScope.launch {
                val elapsedTime = calculateElapsedTime()
                txtElapsedTime.text = "$elapsedTime MS"
            }*/
        }
        return view
    }

    private suspend fun updateElapsedTime() {
        val startNanoTime = System.nanoTime()
        while (true) {
            delay(100)
            val elapsedTimeNano = System.nanoTime() - startNanoTime
            val elapsedTimeMs = elapsedTimeNano / 1000000
            txtElapsedTime.text = "Elapsed time $elapsedTimeMs ms"
        }
    }

    /*private suspend fun calculateElapsedTime(): Long {
        return withContext(Dispatchers.Default) {
            if (job == null) {
                return@withContext 0
            }
            var elapsedTimeInMillis: Long = 0
            while (job!!.isActive) {
                delay(1)
                elapsedTimeInMillis = elapsedTimeInMillis.plus(1)
            }
            elapsedTimeInMillis
        }
    }*/

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        btnGetReputation.isEnabled = true
    }

    private suspend fun getReputationForUser(userId: String): Int {
        return withContext(Dispatchers.Default) {
            logThreadInfo("getReputationForUser()")
            getReputationEndpoint.getReputation(userId)
        }
    }

    private fun logThreadInfo(message: String) {
        ThreadInfoLogger.logThreadInfo(message)
    }

    companion object {
        fun newInstance(): Fragment {
            return Exercise3Fragment()
        }
    }
}