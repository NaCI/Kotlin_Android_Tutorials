package com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise6

/**
 * Existing class which you can't change
 */
class PostBenchmarkResultsEndpoint {
    fun postBenchmarkResults(timeSeconds: Int, iterations: Long) {
        Thread.sleep(500)
    }
}