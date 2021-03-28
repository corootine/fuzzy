package com.corootine.fuzzy.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
) : ViewModel() {

    init {
        viewModelScope.launch {
        }
    }
}