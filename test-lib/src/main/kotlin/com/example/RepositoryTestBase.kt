package com.example

import com.example.shared.database.DatabaseFactory
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.dbSetup
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeAll
import org.koin.test.KoinTest
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class RepositoryTestBase : KoinTest {
    init {
        dbSetup(to = destination!!) { sql("SET FOREIGN_KEY_CHECKS=0") }.launch()
    }

    fun db(configure: DbSetupBuilder.() -> Unit) {
        dbSetup(to = destination!!, configure = configure).launch()
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(RepositoryTestBase::class.java)

        var db: Database? = null
        var destination: DataSourceDestination? = null

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            if (db == null) {
                val config = HoconApplicationConfig(
                    ConfigFactory.systemEnvironment().withFallback(ConfigFactory.load("application.test.conf"))
                )
                val env = Environment.getBy(
                    config.propertyOrNull("ktor.deployment.environment")?.getString() ?: "local"
                )
                logger.info("Deployment environment: $env")
                ConfigurationSetUpper.setUpDatabaseConfig(config)

                db = DatabaseFactory.initForTest()
                destination = DataSourceDestination(DatabaseFactory.hikariLocal())
            }
        }
    }

}