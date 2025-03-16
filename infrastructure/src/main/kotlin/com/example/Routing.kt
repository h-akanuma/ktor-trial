package com.example

import com.example.user.route.userRoute
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        helloRoute()
        userRoute()
    }
}