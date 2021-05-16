package com.naci.tutorial.kotlinflowsbasics

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

private const val TAG = "RaywenderlichActivity"
private const val DELAY = 500L

class RaywenderlichActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val namesFlow = flowOf("Jody", "Steve", "Lance", "Mike", "Joe", "Pal", "Hugo")
    private val bufferSampleFlow = namesFlow
        .transform { name ->
            if (name.length < 5) {
                delay(DELAY)
                emit(Car(name, Color.BLACK, name.length, 4, false))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raywenderlich)

        buttonClick()
    }

    private fun buttonClick() {
        findViewById<AppCompatButton>(R.id.button).setOnClickListener {
            processValues()
            flowOperatorSample()
            bufferSample()
            catchSample()
        }
    }

    private fun catchSample() {
        coroutineScope.launch {
            namesFlow
                .onEach { name ->
                    check(name.length < 5) {
                        "Unwanted result : $name"
                    }
                    Log.d(TAG, "catchSample: $name")
                }
                .catch { e -> Log.d(TAG, "catchSample: Error: $e") }
                .collect()
        }
    }

    private fun bufferSample() {
        coroutineScope.launch {
            var time = measureTimeMillis {
                bufferSampleFlow
                    .collect { car ->
                        delay(DELAY * 3)
                        Log.d(TAG, "bufferSample: car: $car")
                    }
            }
            Log.d(TAG, "bufferSample: collected without buffer in $time ms")

            time = measureTimeMillis {
                bufferSampleFlow
                    .buffer()
                    .collect { car ->
                        delay(DELAY * 3)
                        Log.d(TAG, "bufferSample: car: $car")
                    }
            }

            Log.d(TAG, "bufferSample: collected WITH buffer in $time ms")

            bufferSampleFlow.collectLatest { car ->
                delay(DELAY * 3)
                Log.d(TAG, "collectLatestSample: car: $car")
            }
        }
    }

    private fun flowOperatorSample() {
        coroutineScope.launch {
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
        coroutineScope.launch {
            val values = getValues()
            for (value in values) {
                Log.d(TAG, "processValues: $value")
            }
        }
    }
}