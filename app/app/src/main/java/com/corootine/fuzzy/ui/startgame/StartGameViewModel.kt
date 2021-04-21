package com.corootine.fuzzy.ui.startgame

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.corootine.fuzzy.domain.userId.UserIdProvider
import com.corootine.fuzzy.ui.util.viewModelLaunchSafe

class StartGameViewModel @ViewModelInject constructor(
    private val userIdProvider: UserIdProvider
): ViewModel() {

    val userId: MutableLiveData<String> = MutableLiveData("")
    val allowConnection: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        viewModelLaunchSafe(
            block = {
                val userId = userIdProvider.provide()
                this@StartGameViewModel.userId.postValue(userId.id)
            }
        )
    }

    fun onPartnerUserIdChanged(userId: String) {
        if (userId.length == 6) {
            allowConnection.postValue(true)
        } else {
            allowConnection.postValue(false)
        }
    }
}