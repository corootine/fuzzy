package com.corootine.fuzzy.server

import com.corootine.fuzzy.server.dao.appinstance.AppInstanceDao
import com.corootine.fuzzy.server.route.appinstance.registration
import com.corootine.fuzzy.server.service.appinstance.AppInstanceRegistration
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

    DatabaseFactory.init()

    val appInstanceDao = AppInstanceDao()
    val appInstanceRegistration = AppInstanceRegistration(appInstanceDao)

    install(Routing) {
        registration(appInstanceRegistration)
    }
}

fun main() {
    embeddedServer(Jetty, 25564, module = Application::module).start()
}