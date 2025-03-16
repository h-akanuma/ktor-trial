package com.example.user.route

import com.example.E2ETestBase
import com.example.common.fixtures.UsersFixture
import com.example.common.fixtures.extension.default
import com.example.common.fixtures.insert
import com.example.deleteAllTables
import com.example.domain.user.model.User
import com.example.objectmother.UserMother
import com.example.resetAutoIncrement
import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UserRouteTest : E2ETestBase() {

    @BeforeEach
    fun beforeEach() {
        db {
            deleteAllTables()
            resetAutoIncrement("users")
        }
    }

    @DisplayName("ShowUser")
    @Nested
    inner class ShowUser {
        @BeforeEach
        fun beforeEach() {
            db {
                insert(UsersFixture().default())
            }
        }

        @Test
        fun `show success`() {
            test(
                httpMethod = HttpMethod.Get,
                url = "/users/1"
            ) { response ->
                val res = response.body<User>()
                assertThat(response.status).isEqualTo(HttpStatusCode.OK)

                val expected = UserMother.default()
                assertThat(res).isEqualTo(expected)
            }
        }
    }

}