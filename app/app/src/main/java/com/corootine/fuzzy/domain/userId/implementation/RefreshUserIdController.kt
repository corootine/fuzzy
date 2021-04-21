package com.corootine.fuzzy.domain.userId.implementation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshUserIdController {

    @POST("refreshuserid")
    suspend fun refreshUserId(@Body request: Request): Response

    @Serializable
    data class Request(@SerialName("userId") val userId: String? = null) {

        companion object {

            fun empty() = Request()
        }
    }

    @Serializable
    data class Response(@SerialName("userId") val userId: String)
}