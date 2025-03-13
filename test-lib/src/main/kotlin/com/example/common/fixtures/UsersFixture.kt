package com.example.common.fixtures

import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues
import java.time.LocalDateTime

data class UsersFixture(
    val id: Long = 0,
    val name: String = "",
    val age: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

fun DbSetupBuilder.insertUsersFixture(f: UsersFixture) {
    insertInto("users") {
        mappedValues(
            "id" to f.id,
            "name" to f.name,
            "age" to f.age,
            "created_at" to f.createdAt,
            "updated_at" to f.updatedAt
        )
    }
}

fun DbSetupBuilder.insert(f: UsersFixture) {
    insertUsersFixture(f)
}