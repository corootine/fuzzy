package com.corootine.fuzzy.domain.userId.provide

import com.corootine.fuzzy.domain.di.ApplicationScope
import com.corootine.fuzzy.domain.di.IODispatcher
import com.corootine.fuzzy.domain.userId.provide.RefreshUserIdController.Request
import com.corootine.fuzzy.domain.userId.provide.UserIdProvider.UserIdState
import com.corootine.fuzzy.core.network.retrofit.RequestExecutor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

interface UserIdProvider {

    val userIdStateFlow: StateFlow<UserIdState>

    sealed class UserIdState {

        class Available(val userId: UserId) : UserIdState()
        object Pending : UserIdState()
    }
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

    private val _userIdFlow: MutableStateFlow<UserIdState> = MutableStateFlow(value = UserIdState.Pending)

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

    override val userIdStateFlow: StateFlow<UserIdState> = _userIdFlow.asStateFlow()

    private suspend fun refreshAndEmit() {
        val currentUserId = userIdRepository.get()
        val request = currentUserId?.let { Request(it.value) } ?: Request.empty()

        val response = requestExecutor.execute { refreshUserIdController.refreshUserId(request) }
        val refreshedUserId = UserId(response.userId)
        logger.debug("Fetched new user id $refreshedUserId. Current user id is $currentUserId")

        userIdRepository.set(refreshedUserId)
        _userIdFlow.emit(UserIdState.Available(refreshedUserId))
    }
}