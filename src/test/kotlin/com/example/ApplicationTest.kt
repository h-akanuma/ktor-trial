package com.example

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import org.junit.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }

        client.get("/").apply {
            assertEquals(HttpStatusCode.Companion.OK, status)
        }
    }

}