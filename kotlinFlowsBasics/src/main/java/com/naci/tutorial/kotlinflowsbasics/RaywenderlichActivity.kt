package com.naci.tutorial.kotlinflowsbasics

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val TAG = "RaywenderlichActivity"

class RaywenderlichActivity : AppCompatActivity() {

    val namesFlow = flowOf("Jody", "Steve", "Lance", "Joe")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raywenderlich)

        buttonClick()
    }

    private fun buttonClick() {
        findViewById<AppCompatButton>(R.id.button).setOnClickListener {
            processValues()
            flowOperatorSample()
        }
    }

    private fun flowOperatorSample() {
        CoroutineScope(Dispatchers.Default).launch {
            namesFlow.map { name ->
                name.length
            }.filter { length ->
                length < 5
            }.collect {
                Log.d(TAG, "flowOperatorSample: $it")
            }

        }
    }

    private suspend fun getValues(): Sequence<Int> = sequence {
        Thread.sleep(250)
        yield(1)
        Thread.sleep(500)
        yield(2)
        Thread.sleep(250)
        yield(3)
    }

    private fun processValues() {
        CoroutineScope(Dispatchers.Default).launch {
            val values = getValues()
            for (value in values) {
                Log.d(TAG, "processValues: $value")
            }
        }
    }
}