package com.corootine.fuzzy.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corootine.fuzzy.domain.appinstanceid.AppInstanceIdProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val appInstanceIdProvider: AppInstanceIdProvider,
) : ViewModel() {

    init {
        viewModelScope.launch {
            appInstanceIdProvider.get()
        }
    }
}