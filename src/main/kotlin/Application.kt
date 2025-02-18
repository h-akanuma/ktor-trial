package com.example

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.CallLogging

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(CallLogging)
    configureRouting()
}
