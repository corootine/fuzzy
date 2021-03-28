package com.corootine.fuzzy.network.retrofit

import org.slf4j.LoggerFactory
import java.lang.Exception

class RetrofitRequestExecutor : RequestExecutor {

    private val logger = LoggerFactory.getLogger(RetrofitRequestExecutor::class.java)

    override suspend fun <R> execute(request: suspend () -> R): R {
        try {
            return request()
        } catch (exception: Exception) {
            logger.error("Request failed with an exception", exception)
            throw exception
        }
    }
}