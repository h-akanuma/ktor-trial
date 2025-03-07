package com.example

import com.example.shared.database.DatabaseConfig
import com.example.shared.database.DatabaseFactory
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
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
    install(ContentNegotiation) {
        json()
    }

    configureDatabase()
    configureRouting()
}

fun Application.configureDatabase() {
    val config = environment.config

    DatabaseConfig.driverClassName = config.property("trial.database.driverClassName").getString()
    DatabaseConfig.jdbcUrl = config.property("trial.database.jdbcUrl").getString()
    DatabaseConfig.userName = config.property("trial.database.userName").getString()
    DatabaseConfig.password = config.property("trial.database.password").getString()
    DatabaseConfig.maximumPoolSize = config.property("trial.database.maximumPoolSize").getString().toInt()
    DatabaseConfig.isAutoCommit = config.property("trial.database.isAutoCommit").getString().toBoolean()
    DatabaseConfig.transactionIsolation = config.property("trial.database.transactionIsolation").getString()
    DatabaseConfig.maxLifetime = config.property("trial.database.maxLifetime").getString().toLong()
    DatabaseConfig.idleTimeOut = config.property("trial.database.idleTimeOut").getString().toLong()
    DatabaseConfig.minimumIdle = config.property("trial.database.minimumIdle").getString().toInt()

    DatabaseFactory.initLocal()
}