package com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise1

import com.naci.tutorial.kotlincoroutinesudemclass.common.ThreadInfoLogger
import kotlin.random.Random

class GetReputationEndpoint {
    fun getReputation(userId: String): Int {
        ThreadInfoLogger.logThreadInfo("GetReputationEndpoint#getReputation(): called")
        Thread.sleep(3000)
        ThreadInfoLogger.logThreadInfo("GetReputationEndpoint#getReputation(): return data")
        return Random.nextInt(0, 100)
    }
}