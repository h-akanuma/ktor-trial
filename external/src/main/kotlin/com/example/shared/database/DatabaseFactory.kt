package com.example.shared.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {

    fun initLocal() {
        Database.connect(
            datasource = hikariLocal(),
            databaseConfig = exposedDatabaseConfig()
        )
    }

    fun hikariLocal(): HikariDataSource {
        val config = hikariConfigCommon()
        config.validate()

        return HikariDataSource(config)
    }

    private fun hikariConfigCommon(): HikariConfig {
        val hikariConfig = HikariConfig()

        hikariConfig.driverClassName = DatabaseConfig.driverClassName
        hikariConfig.jdbcUrl = DatabaseConfig.jdbcUrl
        hikariConfig.username = DatabaseConfig.userName
        hikariConfig.password = DatabaseConfig.password
        hikariConfig.maximumPoolSize = DatabaseConfig.maximumPoolSize
        hikariConfig.isAutoCommit = DatabaseConfig.isAutoCommit
        hikariConfig.transactionIsolation = DatabaseConfig.transactionIsolation
        hikariConfig.maxLifetime = DatabaseConfig.maxLifetime
        hikariConfig.idleTimeout = DatabaseConfig.idleTimeOut
        hikariConfig.minimumIdle = DatabaseConfig.minimumIdle

        return hikariConfig
    }

    private fun exposedDatabaseConfig(): org.jetbrains.exposed.sql.DatabaseConfig {
        return org.jetbrains.exposed.sql.DatabaseConfig.invoke {
            // SQLExceptionが発生した時のリトライを抑制する
            this.defaultMaxAttempts = 1
        }
    }
}