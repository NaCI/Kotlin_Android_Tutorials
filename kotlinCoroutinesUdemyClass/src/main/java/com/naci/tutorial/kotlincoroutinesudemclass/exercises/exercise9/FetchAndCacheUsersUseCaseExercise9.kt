package com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise9

import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise8.GetUserEndpoint
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise8.User
import com.naci.tutorial.kotlincoroutinesudemclass.exercises.exercise8.UsersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class FetchAndCacheUsersUseCaseExercise9(
    private val getUserEndpoint: GetUserEndpoint,
    private val usersDao: UsersDao
) {

    suspend fun fetchAndCacheUsers(userIds: List<String>): List<User> = withContext(Dispatchers.Default) {
        userIds.map { userId ->
            async {
                val user = getUserEndpoint.getUser(userId)
                usersDao.upsertUserInfo(user)
                user
            }
        }.awaitAll()
    }

}