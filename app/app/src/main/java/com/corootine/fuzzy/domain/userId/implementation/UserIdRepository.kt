package com.corootine.fuzzy.domain.userId.implementation

import androidx.datastore.core.DataStore
import com.corootine.fuzzy.domain.userId.api.UserId
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences
import javax.inject.Inject

interface UserIdRepository {

    suspend fun get(): UserId?

    suspend fun update(userId: UserId)
}

class UserIdRepositoryLogic @Inject constructor(private val dataStore: DataStore<Preferences>) : UserIdRepository {

    companion object {
        private const val KEY_USER_ID = "USER_ID"
        private const val DEFAULT_USER_ID = ""
    }

    override suspend fun get(): UserId? {
        return dataStore.data.map { preferences ->
            val id = preferences.get(KEY_USER_ID, DEFAULT_USER_ID)
            if (id != DEFAULT_USER_ID) UserId(id) else null
        }.firstOrNull()
    }

    override suspend fun update(userId: UserId) {
        dataStore.updateData { preferences ->
            preferences.put(KEY_USER_ID, userId.id)
            preferences
        }
    }
}