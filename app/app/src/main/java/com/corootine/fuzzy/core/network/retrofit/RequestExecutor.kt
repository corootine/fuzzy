package com.corootine.fuzzy.core.network.retrofit

interface RequestExecutor {

    suspend fun <R> execute(request: suspend () -> R): R
}