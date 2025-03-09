package com.example.user.table

import com.example.domain.user.model.User
import com.example.shared.table.LongEntityBase
import com.example.shared.table.LongIdTableBase
import com.example.shared.valueobject.UserId
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

object UserTable : LongIdTableBase("users") {
    val name = varchar("name", 255)
    val age = integer("age")
}

class UserEntity(id: EntityID<Long>) : LongEntityBase(id) {
    companion object : LongEntityClass<UserEntity>(UserTable)

    var name by UserTable.name
    var age by UserTable.age

    override var createdAt by UserTable.createdAt
    override var updatedAt by UserTable.updatedAt

    fun toModel(): User {
        return User(
            id = UserId(id.value),
            name = name,
            age = age
        )
    }
}