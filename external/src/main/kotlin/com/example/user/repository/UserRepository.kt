package com.example.user.repository

import com.example.domain.user.model.User
import com.example.domain.user.repository.IUserRepository
import com.example.exception.ObjectNotFoundException
import com.example.shared.valueobject.UserId
import com.example.user.table.UserEntity

class UserRepository : IUserRepository {
    override fun getBy(id: UserId): User {
        return UserEntity.findById(id.value)?.toModel()
            ?: throw ObjectNotFoundException("No user found given id. id: $id")
    }

    override fun create(name: String, age: Int): User {
        return UserEntity.new {
            this.name = name
            this.age = age
        }.toModel()
    }

    override fun update(id: UserId, user: User) {
        UserEntity.findById(id.value)?.apply {
            name = user.name
            age = user.age
        } ?: throw ObjectNotFoundException("No user found given id. id: $id")
    }

    override fun delete(id: UserId) {
        UserEntity.findById(id.value)?.delete()
            ?: throw ObjectNotFoundException("No user found given id. id: $id")
    }
}