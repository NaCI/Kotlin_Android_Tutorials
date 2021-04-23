package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.noncancellable

import com.naci.tutorial.kotlincoroutinesudemclass.common.ThreadInfoLogger.logThreadInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CustomersDao {

    suspend fun updateCustomer(customer: Customer) = withContext(Dispatchers.IO) {
        logThreadInfo("updating local customer's data")
        delay(2000)
    }
}