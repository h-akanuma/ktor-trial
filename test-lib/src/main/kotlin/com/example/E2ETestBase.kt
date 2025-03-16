package com.example

import com.typesafe.config.ConfigFactory
import io.ktor.client.plugins.DefaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.jackson.jackson
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.testing.TestApplication
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.core.context.loadKoinModules
import java.util.TimeZone
import org.koin.core.module.Module

abstract class E2ETestBase : RepositoryTestBase() {

    fun test(
        url: String,
        httpMethod: HttpMethod,
        koinModule: Module? =null,
        body: Any? = null,
        assertBlock: suspend (response: HttpResponse) -> Unit,
    ) {
        val testApp = testApplication(koinModule)
        try {
            val testClient = httpClient(testApp = testApp)
            val response = runBlocking {
                testClient.request(urlString = url) {
                    method = httpMethod
                    setBody(body)
                }
            }
            runBlocking {
                newSuspendedTransaction {
                    assertBlock(response)
                }
            }
        } finally {
            testApp.stop()
        }
    }

    fun testApplication(koinModule: Module?) = TestApplication {
        environment {
            config = HoconApplicationConfig(
                ConfigFactory.systemEnvironment().withFallback(ConfigFactory.load("application.test.conf"))
            )
        }
        application {
            koinModule?.let { loadKoinModules(it) }
        }
    }

    private fun httpClient(testApp: TestApplication) = testApp.createClient {
        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            jackson {
                ObjectMapperBuilder.build(this)

                // デフォルトのタイムゾーンを設定
                setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"))
            }
        }
    }
}