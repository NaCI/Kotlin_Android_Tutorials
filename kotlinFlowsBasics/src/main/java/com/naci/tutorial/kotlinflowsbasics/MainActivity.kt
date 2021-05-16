package com.naci.tutorial.kotlinflowsbasics

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var myFlow: Flow<Int>
    private lateinit var carFlow: Flow<Car>
    private lateinit var anotherFlow: Flow<Car>
    private lateinit var firstFlow: Flow<String>
    private lateinit var secondFlow: Flow<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFlow()
        setupFlowOf()
        setupAsFlow()
        setupZipOperator()
        setupClicks()

        findViewById<AppCompatButton>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, RaywenderlichActivity::class.java))
        }
    }

    private fun setupFlow() {
        myFlow = flow {
            Log.d(TAG, "Start Flow")
            (0..10).forEach {
                // Emit items with 500 milliseconds delay
                delay(300)
                Log.d(TAG, "Emitting $it")
                emit(it)
            }
        }.map {
            it * it
        }.flowOn(Dispatchers.Default)
    }

    private suspend fun setupReduceFlow() {
        val result = (1..5).asFlow()
            .map { it.toString() }
            .reduce { a, b ->
                "$a plus $b : ${a.split(" ").last().toInt().plus(b.toInt())}"
            }
        Log.d(TAG, "setupReduceFlow: $result")
    }

    private suspend fun setupAnyFlow() {
        flowOf(
            1, "asd", Car("honda", Color.BLUE, 6, 2, false), true
        ).flowOn(Dispatchers.IO)
            .collect {
                Log.d(TAG, "setupAnyFlow: $it")
            }
    }

    private fun setupFlowOf() {
        carFlow = flowOf(
            Car("honda", Color.BLUE, 6, 2, false),
            Car("mazda", Color.RED, 5, 4, false),
            Car("audi", Color.GRAY, 4, 4, true)
        ).onEach {
            delay(300)
        }.map {
            it.apply {
                this.brand = it.brand.plus(" wlulul")
            }
        }.flowOn(Dispatchers.Main)
    }

    private fun setupAsFlow() {
        anotherFlow = (1..20).asFlow().onEach {
            delay(100)
        }.onCompletion {
            Log.d(TAG, "setupAsFlow: completed")
        }.onEmpty {
            Log.d(TAG, "setupAsFlow: empty")
        }.onStart {
            Log.d(TAG, "setupAsFlow: started")
        }.map { number ->
            Car("toyota", Color.GRAY, number, (number % 6).plus(1), true)
        }.flowOn(Dispatchers.Default)
    }

    private fun setupZipOperator() {
        firstFlow = flowOf("Fener", "Urfa", "Gazi", "Çanak").flowOn(Dispatchers.Default)
        secondFlow = flowOf("bahçe", "spor", "antep").flowOn(Dispatchers.Default)
    }

    @ExperimentalCoroutinesApi
    private fun setupClicks() {
        findViewById<AppCompatButton>(R.id.button).clicks()
            .throttleFirst(5000)
            .onEach {
                CoroutineScope(Dispatchers.Main).launch {
                    myFlow.collect {
                        Log.d(TAG, "myFlow collect: $it")
                    }
                    carFlow.collect {
                        Log.d(TAG, "carFlow collect: $it")
                    }
                    anotherFlow.collect {
                        Log.d(TAG, "anotherFlow collect: $it")
                    }
                    // Flow zip operator example
                    firstFlow.zip(secondFlow) { firstData, secondData ->
                        "$firstData $secondData"
                    }.collect {
                        Log.d(TAG, "zipped flow data: $it")
                    }
                    setupAnyFlow()
                    // Flow cancel example
                    withTimeoutOrNull(1000) {
                        myFlow.collect {
                            Log.d(TAG, "myFlow collect withTimeout: $it")
                        }
                    }
                    setupReduceFlow()
                }
            }.launchIn(GlobalScope)
    }
}