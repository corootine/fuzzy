@file:JvmName("Server")

package com.corootine.fuzzy.server

import com.corootine.fuzzy.server.routing.deviceBinding
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    install(StatusPages) {
        exception<JsonMappingException> {
            call.respondText("Invalid input", ContentType.Application.Json, HttpStatusCode.BadRequest)
        }
        exception<Throwable> {
            call.respondText("Internal error", ContentType.Application.Json, HttpStatusCode.InternalServerError)
        }
    }

    install(Routing) {
        deviceBinding()
    }
}

fun main() {
    val port = System.getenv("PORT") ?: "8080"
    embeddedServer(Jetty, port = port.toInt(), module = Application::module).start()
}