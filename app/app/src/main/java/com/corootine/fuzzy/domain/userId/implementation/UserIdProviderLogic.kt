package com.corootine.fuzzy.domain.userId.implementation

import com.corootine.fuzzy.domain.userId.UserId
import com.corootine.fuzzy.domain.userId.UserIdProvider
import com.corootine.fuzzy.domain.userId.implementation.RefreshUserIdController.Request
import com.corootine.fuzzy.network.retrofit.RequestExecutor
import javax.inject.Inject

class UserIdProviderLogic @Inject constructor(
    private val requestExecutor: RequestExecutor,
    private val refreshUserIdController: RefreshUserIdController,
    private val userIdRepository: UserIdRepository,
) : UserIdProvider {

    override suspend fun provide(): UserId {
        val currentUserId = userIdRepository.get()
        val request = currentUserId?.let { Request(it.id) } ?: Request.empty()

        val response = requestExecutor.execute { refreshUserIdController.refreshUserId(request) }
        val refreshedUserId = UserId(response.userId)
        userIdRepository.update(refreshedUserId)
        return refreshedUserId
    }
}