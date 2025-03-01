package com.example

import com.typesafe.config.ConfigFactory
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        environment {
            config = HoconApplicationConfig(
                ConfigFactory.systemEnvironment().withFallback(ConfigFactory.load("application.test.conf"))
            )
        }

        client.get("/").apply {
            assertEquals(HttpStatusCode.Companion.OK, status)
        }
    }

}