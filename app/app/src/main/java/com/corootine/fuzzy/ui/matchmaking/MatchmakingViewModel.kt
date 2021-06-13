package com.corootine.fuzzy.ui.matchmaking

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corootine.fuzzy.domain.di.UIDispatcher
import com.corootine.fuzzy.domain.userId.provide.UserIdProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory

sealed class UserId {
    class Available(val value: String) : UserId()
    object Pending : UserId()
}

class MatchmakingViewModel @ViewModelInject constructor(
    userIdProvider: UserIdProvider,
    @UIDispatcher
    uiDispatcher: CoroutineDispatcher
) : ViewModel() {

    val userId: MutableStateFlow<UserId> = MutableStateFlow(UserId.Pending)
    val allowConnection: MutableLiveData<Boolean> = MutableLiveData(false)

    private val logger = LoggerFactory.getLogger(MatchmakingViewModel::class.java)

    init {
        userIdProvider.userIdFlow
            .onEach {
                logger.debug("Received new user id ${it.id}")
                userId.emit(UserId.Available(it.id))
            }
            .onCompletion { logger.debug("Flow completed.") }
            .flowOn(uiDispatcher)
            .launchIn(viewModelScope)
    }

    fun onPartnerUserIdChanged(userId: String) {
        if (userId.length == 6) {
            allowConnection.postValue(true)
        } else {
            allowConnection.postValue(false)
        }
    }
}