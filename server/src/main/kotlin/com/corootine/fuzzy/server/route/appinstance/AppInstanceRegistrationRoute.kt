package com.corootine.fuzzy.server.route.appinstance

import com.corootine.fuzzy.server.service.appinstance.AppInstanceRegistration
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

data class AppInstanceRegistrationRequest(val notificationToken: String)

data class AppInstanceRegistrationResponse(val appInstanceId: String)

fun Route.registration(appInstanceRegistration: AppInstanceRegistration) {
    post("/registration") {
        val request = call.receive<AppInstanceRegistrationRequest>()
        val appInstanceId = appInstanceRegistration.register(request.notificationToken)
        call.respond(AppInstanceRegistrationResponse(appInstanceId))
    }
}