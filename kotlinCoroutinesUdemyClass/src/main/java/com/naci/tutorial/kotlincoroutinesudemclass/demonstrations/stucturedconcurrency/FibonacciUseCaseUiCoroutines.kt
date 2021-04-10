package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.stucturedconcurrency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger

class FibonacciUseCaseUiCoroutines() {

    suspend fun computeFibonacci(index: Int): BigInteger =
        withContext(Dispatchers.Default) {
            when (index) {
                0 -> {
                    BigInteger("0")
                }
                1 -> {
                    BigInteger("1")
                }
                else -> {
                    computeFibonacci(index - 1).add(computeFibonacci(index - 2))
                }
            }
        }
}