package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.stucturedconcurrency

import kotlinx.coroutines.*
import java.math.BigInteger

class FibonacciUseCaseAsyncUiCoroutines(private val bgCoroutineDispatcher: CoroutineDispatcher) {
    interface Callback {
        fun onFibonacciComputed(result: BigInteger?)
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun computeFibonacci(index: Int, callback: Callback) {
        coroutineScope.launch {
            val result = computeFibonacciBg(index)
            callback.onFibonacciComputed(result)
        }
    }

    private suspend fun computeFibonacciBg(index: Int): BigInteger =
        withContext(bgCoroutineDispatcher) {
            when (index) {
                0 -> {
                    BigInteger("0")
                }
                1 -> {
                    BigInteger("1")
                }
                else -> {
                    computeFibonacciBg(index - 1).add(computeFibonacciBg(index - 2))
                }
            }
        }
}