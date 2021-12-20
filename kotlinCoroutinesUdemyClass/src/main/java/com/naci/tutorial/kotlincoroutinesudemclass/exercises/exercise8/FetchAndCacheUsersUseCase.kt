package com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise8

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FetchAndCacheUsersUseCase(
    private val getUserEndpoint: GetUserEndpoint,
    private val usersDao: UsersDao
) {

    suspend fun fetchAndCacheUsers(userIds: List<String>) = withContext(Dispatchers.Default) {
        for (userId in userIds) {
            // REMINDER: This launch makes this algorithm parallel. All operations "getUser"
            // and "upsertUserInfo" are runs sequentially but for loop runs asynchronously
            launch {
                val user = getUserEndpoint.getUser(userId)
                // Insert or update -> upsert
                usersDao.upsertUserInfo(user)
            }
        }
    }

}