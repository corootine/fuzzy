@file:JvmName("Server")

package com.corootine.fuzzy.server

import com.corootine.fuzzy.server.routing.refreshUserId
import com.corootine.fuzzy.server.service.user.UserIdProviderLogic
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*
import org.slf4j.LoggerFactory
import java.io.File
import java.security.KeyStore
import java.time.Duration

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
    install(WebSockets) {
        timeout = Duration.ofSeconds(30)
    }

    install(Routing) {
        refreshUserId(UserIdProviderLogic())
        webSocket("/gamesession") {
            for (frame in incoming) {
                logger.debug("$frame")

                when (frame) {
                    is Frame.Binary -> TODO()
                    is Frame.Text -> println(frame.readText())
                    is Frame.Close -> TODO()
                    is Frame.Ping -> TODO()
                    is Frame.Pong -> TODO()
                }

                outgoing.send(Frame.Text("Hello"))
            }
        }
    }
}

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun main() {
    val keyStoreFilePath = File(Thread.currentThread().contextClassLoader.getResource("test2.jks").file)
    val keyStore = KeyStore.getInstance(keyStoreFilePath, "123456".toCharArray())

    embeddedServer(Netty, applicationEngineEnvironment {
        sslConnector(
            keyStore = keyStore,
            keyAlias = "testkey",
            keyStorePassword = { "123456".toCharArray() },
            privateKeyPassword = { "123456".toCharArray() },
        ) {
            keyStorePath = keyStoreFilePath
            port = 8443
            module(Application::module)
        }
    }).start()
}