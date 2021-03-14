package com.corootine.fuzzy.server.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

data class AppInstanceRegistrationRequest(val notificationToken: String)

data class AppInstanceRegistrationResponse(val appInstanceId: String)

fun Route.deviceBinding() {
    post("/devicebinding") {
        call.respond(AppInstanceRegistrationResponse("1234"))
    }
}