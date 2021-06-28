package com.corootine.fuzzy.domain.userId.provide

import android.content.SharedPreferences
import com.corootine.fuzzy.domain.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UserIdRepository {

    suspend fun get(): UserId?

    fun set(userId: UserId)
}

class UserIdRepositoryLogic @Inject constructor(
    private val preferences: SharedPreferences,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : UserIdRepository {

    companion object {
        private const val KEY_USER_ID = "USER_ID"
    }

    override suspend fun get(): UserId? = withContext(ioDispatcher) {
        val userIdOrDefault = preferences.getString(KEY_USER_ID, null)
        return@withContext userIdOrDefault?.let { UserId(it) }
    }

    override fun set(userId: UserId) {
        preferences.edit()
            .putString(KEY_USER_ID, userId.value)
            .apply()
    }
}