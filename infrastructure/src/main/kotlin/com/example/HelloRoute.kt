package com.example

import com.example.controller.HelloController
import io.ktor.resources.Resource
import io.ktor.server.routing.Route
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import org.koin.ktor.ext.inject

fun Route.helloRoute() {
    val helloController by inject<HelloController>()

    @Resource("/")
    class Hello
    get<Hello> {
        call.respondText(helloController.greet())
    }
}