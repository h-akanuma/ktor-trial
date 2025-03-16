package com.example

import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class HelloRouteTest : E2ETestBase() {

    @DisplayName("Hello")
    @Nested
    inner class Hello {
        @Test
        fun `hello success`() {
            test(
                httpMethod = HttpMethod.Get,
                url = "/"
            ) { response ->
                assertThat(response.status).isEqualTo(HttpStatusCode.OK)
                assertThat(response.body<String>()).isEqualTo("Hello World from Ktor with Exposed via Koin!")
            }
        }
    }
}