package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.noncancellable

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

class MakeCustomerPremiumUseCase(
    private val premiumCustomersEndpoint: PremiumCustomersEndpoint,
    private val customersDao: CustomersDao
) {

    /**
     * Give the customer premium status
     * @return updated information about the customer
     */
    suspend fun makeCustomerPremium(customerId: String): Customer {
        // withContext(Dispatchers.Default + NonCancellable) => demonstration for use together
        return withContext(Dispatchers.Default) {
            withContext(NonCancellable) {
                val updatedCustomer = premiumCustomersEndpoint.makeCustomerPremium(customerId)
                customersDao.updateCustomer(updatedCustomer)
                updatedCustomer
            }
        }
    }

}