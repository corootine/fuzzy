package com.corootine.fuzzy.core.error

sealed class CoreError {

    class NoInternetConnection : CoreError()
}