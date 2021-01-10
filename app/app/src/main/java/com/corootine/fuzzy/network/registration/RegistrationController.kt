package com.corootine.fuzzy.network.registration

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationController {

    @POST("registration")
    suspend fun register(@Body request: Request): Response

    @Serializable
    data class Request(@SerialName("notificationToken") val notificationToken: String)

    @Serializable
    data class Response(@SerialName("appInstanceId") val appInstanceId: String)
}