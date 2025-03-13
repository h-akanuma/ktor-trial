package com.example.user.repository

import com.example.RepositoryTestBase
import com.example.common.fixtures.UsersFixture
import com.example.common.fixtures.extension.default
import com.example.common.fixtures.insert
import com.example.deleteAllTables
import com.example.exception.ObjectNotFoundException
import com.example.objectmother.UserMother
import com.example.resetAutoIncrement
import com.example.shared.valueobject.UserId
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class UserRepositoryTest : RepositoryTestBase() {
    private val userRepository = UserRepository()

    @BeforeEach
    fun beforeEach(){
        db {
            deleteAllTables()
            resetAutoIncrement("users")
        }
    }

    @DisplayName("GetById")
    @Nested
    inner class GetById {
        @Test
        fun `should return user given id`() {
            db {
                insert(UsersFixture().default())
            }

            val got = transaction {
                userRepository.getBy(UserId(1))
            }
            assertThat(got).usingRecursiveComparison().isEqualTo(UserMother.default())
        }

        @Test
        fun `should throw ObjectNotFoundException when user not found`() {
            db {
                insert(UsersFixture().default())
            }

            assertThatThrownBy {
                transaction {
                    userRepository.getBy(UserId(2))
                }
            }.isInstanceOf(ObjectNotFoundException::class.java)
        }

    }

}