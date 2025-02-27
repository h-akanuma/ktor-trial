package com.example

import com.example.controller.HelloController
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val helloController by inject<HelloController>()

    routing {
        get("/") {
            call.respondText(helloController.greet())
        }
    }
}