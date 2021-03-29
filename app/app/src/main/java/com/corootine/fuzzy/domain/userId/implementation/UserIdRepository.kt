package com.corootine.fuzzy.domain.userId.implementation

import android.content.SharedPreferences
import com.corootine.fuzzy.domain.userId.UserId
import javax.inject.Inject

interface UserIdRepository {

    suspend fun get(): UserId?

    suspend fun update(userId: UserId)
}

class UserIdRepositoryLogic @Inject constructor(private val preferences: SharedPreferences) : UserIdRepository {

    companion object {
        private const val KEY_USER_ID = "USER_ID"
        private const val DEFAULT_USER_ID = ""
    }

    override suspend fun get(): UserId? {
        val userIdOrDefault = preferences.getString(KEY_USER_ID, DEFAULT_USER_ID)
        return if (userIdOrDefault != null && userIdOrDefault != DEFAULT_USER_ID) UserId(userIdOrDefault) else null
    }

    override suspend fun update(userId: UserId) {
        preferences.edit()
            .putString(KEY_USER_ID, userId.id)
            .apply()
    }
}