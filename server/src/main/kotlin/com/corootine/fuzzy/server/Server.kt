package com.corootine.fuzzy.server

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
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
    install(Routing) {
        get("/devicebinding") {
            println("here")
            call.respond(mapOf("OKE" to true))
        }
    }
}

fun main() {
    embeddedServer(Jetty, 8080, module = Application::module).start()
}