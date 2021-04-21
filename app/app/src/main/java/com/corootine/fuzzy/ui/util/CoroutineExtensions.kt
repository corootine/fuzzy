@file:JvmName("CoroutineExtensions")

package com.corootine.fuzzy.ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("CoroutineExtensions")

fun ViewModel.viewModelLaunchSafe(
    onFailed: () -> Unit = {},
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch {
        try {
            block()
        } catch (exception: Exception) {
            logger.error("Coroutine failed with an exception", exception)
            onFailed()
        }
    }
}