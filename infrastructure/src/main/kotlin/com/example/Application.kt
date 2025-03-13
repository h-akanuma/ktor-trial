package com.example

import com.example.exception.ObjectNotFoundException
import com.example.shared.database.DatabaseConfig
import com.example.shared.database.DatabaseFactory
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    val env = Environment.getBy(
        environment.config.propertyOrNull("ktor.deployment.environment")?.getString() ?: "local"
    )
    log.info("Deployment environment: $env")

    install(Koin) {
        modules(Module.modules())
    }
    install(CallLogging)
    install(Resources)
    install(ContentNegotiation) {
        json()
    }

    configureDatabase()
    configureRouting()

    installStatusPages()
}

fun Application.configureDatabase() {
    val config = environment.config

    ConfigurationSetUpper.setUpDatabaseConfig(config)

    DatabaseFactory.initLocal()
}

fun Application.installStatusPages() {
    install(StatusPages) {
        exception<ObjectNotFoundException> { call, cause ->
            this@installStatusPages.log.info(cause.message)
            call.respond(HttpStatusCode.NotFound, cause.message!!)
        }
    }
}