package com.corootine.fuzzy.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.corootine.fuzzy.domain.userId.UserIdProvider
import com.corootine.fuzzy.ui.util.viewModelLaunchSafe

sealed class Result {
    class Pending : Result()
    class Success : Result()
    class Failed : Result()
}

class SplashViewModel @ViewModelInject constructor(
    private val userIdProvider: UserIdProvider
) : ViewModel() {

    val userIdFetchLiveData: MutableLiveData<Result> = MutableLiveData()

    init {
        viewModelLaunchSafe(
            block = {
                userIdProvider.provide()
                userIdFetchLiveData.postValue(Result.Success())
            },
            onFailed = {
                userIdFetchLiveData.postValue(Result.Failed())
            }
        )
    }
}