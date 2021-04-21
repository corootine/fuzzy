package com.corootine.fuzzy.network.retrofit

interface RequestExecutor {

    suspend fun <R> execute(request: suspend () -> R): R
}