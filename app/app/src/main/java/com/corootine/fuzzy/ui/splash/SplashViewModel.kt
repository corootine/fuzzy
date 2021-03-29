package com.corootine.fuzzy.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.corootine.fuzzy.domain.userId.UserId
import com.corootine.fuzzy.domain.userId.UserIdProvider
import com.corootine.fuzzy.ui.util.viewModelLaunchSafe

class SplashViewModel @ViewModelInject constructor(
    private val userIdProvider: UserIdProvider
) : ViewModel() {

    val userIdLiveData: MutableLiveData<UserId> = MutableLiveData()

    init {
        viewModelLaunchSafe {
            val userId = userIdProvider.provide()
            userIdLiveData.postValue(userId)
        }
    }
}