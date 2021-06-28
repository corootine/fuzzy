@file:JvmName("Server")

package com.corootine.fuzzy.server

import Test
import com.corootine.fuzzy.server.routing.refreshUserId
import com.corootine.fuzzy.server.service.user.UserIdProviderLogic
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Server.kt")

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        json()
    }
    install(StatusPages) {
        exception<Throwable> { exception ->
            logger.debug("Request failed with an exception", exception)
            call.respondText("Internal error", ContentType.Application.Json, HttpStatusCode.InternalServerError)
        }
    }

    install(Routing) {
        refreshUserId(UserIdProviderLogic())
    }
}

fun main() {

}
