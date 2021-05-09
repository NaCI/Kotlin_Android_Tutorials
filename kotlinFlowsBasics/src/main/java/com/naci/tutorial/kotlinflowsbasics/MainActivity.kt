package com.naci.tutorial.kotlinflowsbasics

import android.content.Intent
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

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var myFlow: Flow<Int>
    private lateinit var carFlow: Flow<Car>
    private lateinit var anotherFlow: Flow<Int>
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
        }.flowOn(Dispatchers.Default)
    }

    private fun setupZipOperator() {
        firstFlow = flowOf("Fener", "Urfa", "Gazi", "Çanak").flowOn(Dispatchers.Default)
        secondFlow = flowOf("bahçe", "spor", "antep").flowOn(Dispatchers.Default)
    }

    private fun setupClicks() {
        findViewById<AppCompatButton>(R.id.button).setOnClickListener {
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
                firstFlow.zip(secondFlow) { firstData, secondData ->
                    "$firstData $secondData"
                }.collect {
                    Log.d(TAG, "zipped flow data: $it")
                }
            }
        }
    }
}