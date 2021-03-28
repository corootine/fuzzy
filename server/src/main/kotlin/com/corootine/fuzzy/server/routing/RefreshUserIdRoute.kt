@file:JvmName("RefreshUserIdRoute")

package com.corootine.fuzzy.server.routing

import com.corootine.fuzzy.server.service.user.UserId
import com.corootine.fuzzy.server.service.user.UserIdProvider
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory

@Serializable
data class RefreshUserIdRequest(val userId: String? = null)

@Serializable
data class RefreshUserIdResponse(val userId: String)

fun Route.refreshUserId(userIdProvider: UserIdProvider) {
    post("/refresh") {
        val request = call.receive<RefreshUserIdRequest>()
        val userId = request.userId?.let { UserId(it) }

        val refreshedUserId = userIdProvider.provide(userId)

        call.respond(RefreshUserIdResponse(refreshedUserId.id))
    }
}