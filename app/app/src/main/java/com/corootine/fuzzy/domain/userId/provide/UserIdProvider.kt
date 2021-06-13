package com.corootine.fuzzy.domain.userId.provide

import com.corootine.fuzzy.domain.di.ApplicationScope
import com.corootine.fuzzy.domain.di.IODispatcher
import com.corootine.fuzzy.domain.userId.provide.RefreshUserIdController.Request
import com.corootine.fuzzy.network.retrofit.RequestExecutor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import javax.inject.Inject

interface UserIdProvider {

    val userIdFlow: SharedFlow<UserId>
}

class UserIdProviderLogic @Inject constructor(
    private val requestExecutor: RequestExecutor,
    private val refreshUserIdController: RefreshUserIdController,
    private val userIdRepository: UserIdRepository,
    @ApplicationScope
    private val applicationScope: CoroutineScope,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : UserIdProvider {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val _userIdFlow: MutableSharedFlow<UserId> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    init {
        _userIdFlow.subscriptionCount
            .map { it > 0 }
            .distinctUntilChanged()
            .onEach { isActive ->
                if (isActive) {
                    refreshAndEmit()
                }
            }
            .flowOn(ioDispatcher)
            .launchIn(applicationScope)
    }

    override val userIdFlow: SharedFlow<UserId> = _userIdFlow.asSharedFlow()

    private suspend fun refreshAndEmit() = withContext(ioDispatcher) {
        val currentUserId = userIdRepository.get()
        val request = currentUserId?.let { Request(it.id) } ?: Request.empty()

        val response = requestExecutor.execute { refreshUserIdController.refreshUserId(request) }
        val refreshedUserId = UserId(response.userId)
        logger.debug("Fetched new user id $refreshedUserId. Current user id is $currentUserId")

        userIdRepository.set(refreshedUserId)
        _userIdFlow.emit(refreshedUserId)
    }
}