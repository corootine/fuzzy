package com.corootine.fuzzy.server.service.user

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UserIdProviderLogicTest {

    private val userIdProvider = UserIdProviderLogic()

    @Test
    fun `generateUserId - generate new id - when none is passed`() {
        val userId = userIdProvider.provide(null)

        assertNotNull(userId)
    }

    @Test
    fun `generateUserId - generate new id - when passed one is invalid`() {
        val invalidUserId = "11111"
        val userId = userIdProvider.provide(UserId(invalidUserId))

        assertNotNull(userId)
        assertNotEquals(invalidUserId, userId)
    }

    @Test
    fun `generateUserId - do not generate new id - when passed one is valid`() {
        val validUserId = userIdProvider.provide(null)
        val userId = userIdProvider.provide(validUserId)

        assertNotNull(validUserId)
        assertNotNull(userId)
        assertEquals(validUserId, userId)
    }
}