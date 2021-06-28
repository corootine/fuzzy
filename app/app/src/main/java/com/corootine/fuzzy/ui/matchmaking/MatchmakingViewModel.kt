package com.corootine.fuzzy.ui.matchmaking

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.corootine.fuzzy.domain.userId.provide.UserIdProvider
import com.corootine.fuzzy.domain.userId.provide.UserIdProvider.UserIdState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MatchmakingViewModel @ViewModelInject constructor(
    userIdProvider: UserIdProvider,
) : ViewModel() {

    val userIdStateFlow: StateFlow<UserIdState> by lazy(LazyThreadSafetyMode.NONE) { userIdProvider.userIdStateFlow }
    val enableStart: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun onPartnerUserIdChanged(userId: String) {
        if (userId.length == 6) {
            enableStart.tryEmit(true)
        } else {
            enableStart.tryEmit(false)
        }
    }
}