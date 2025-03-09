package com.example

import com.example.controller.HelloController
import com.example.user.route.userRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val helloController by inject<HelloController>()

    routing {
        get("/") {
            call.respondText(helloController.greet())
        }

        userRoute()
    }
}