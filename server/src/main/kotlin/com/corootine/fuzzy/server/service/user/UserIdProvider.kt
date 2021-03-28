package com.corootine.fuzzy.server.service.user

import kotlin.random.Random

interface UserIdProvider {

    fun provide(userId: UserId? = null): UserId
}

class UserIdProviderLogic : UserIdProvider {

    private val userIdPool = mutableListOf<UserId>()

    override fun provide(userId: UserId?): UserId {
        return if (userId != null && userIdPool.contains(userId)) {
            userId
        } else {
            generateUserId()
        }
    }

    @Synchronized
    private fun generateUserId(): UserId {
        var userId = generateRandom6DigitUserId()

        while (userIdPool.contains(userId)) {
            userId = generateRandom6DigitUserId()
        }

        userIdPool.add(userId)
        return userId
    }

    private fun generateRandom6DigitUserId() = UserId(Random.nextInt(100000, 999999).toString())
}