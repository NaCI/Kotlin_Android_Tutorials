package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.noncancellable

import com.naci.tutorial.kotlincoroutinesudemclass.common.ThreadInfoLogger.logThreadInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PremiumCustomersEndpoint {

    suspend fun makeCustomerPremium(customerId: String): Customer = withContext(Dispatchers.IO) {
        logThreadInfo("changing customer's status to premium on the server")
        delay(2000)
        return@withContext Customer(customerId, true)
    }
}