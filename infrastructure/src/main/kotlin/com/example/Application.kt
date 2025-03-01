package com.example

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    val deployEnv = environment.config.propertyOrNull("ktor.deployment.environment")?.getString()
    log.info("Deployment environment: $deployEnv")

    install(Koin) {
        modules(Module.modules())
    }
    install(CallLogging)
    install(ContentNegotiation) {
        json()
    }
    configureDatabases()
    configureRouting()
}