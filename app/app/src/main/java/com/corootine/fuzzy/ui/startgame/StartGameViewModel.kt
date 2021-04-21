package com.corootine.fuzzy.ui.startgame

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.corootine.fuzzy.domain.userId.UserIdProvider
import com.corootine.fuzzy.ui.util.viewModelLaunchSafe

sealed class Result {
    object Pending : Result()
    class Success(val userId: String) : Result()
    object Failed : Result()
}

class StartGameViewModel @ViewModelInject constructor(
    private val userIdProvider: UserIdProvider
): ViewModel() {

    val userId: MutableLiveData<Result> = MutableLiveData()

    init {
        viewModelLaunchSafe(
            block = {
                val userId = userIdProvider.provide()
                this@StartGameViewModel.userId.postValue(Result.Success(userId.id))
            },
            onFailed = {
                userId.postValue(Result.Failed)
            }
        )
    }
}