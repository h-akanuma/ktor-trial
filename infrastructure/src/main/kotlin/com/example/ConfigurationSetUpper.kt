package com.example

import com.example.shared.database.DatabaseConfig
import io.ktor.server.config.ApplicationConfig

object ConfigurationSetUpper {
    fun setUpDatabaseConfig(config: ApplicationConfig) {
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
    }

}