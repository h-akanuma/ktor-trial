package com.example.domain.user.repository

import com.example.domain.user.model.User
import com.example.shared.valueobject.UserId

interface IUserRepository {
    fun getBy(id: UserId): User
    fun create(name: String, age: Int): User
    fun update(id: UserId, user: User)
    fun delete(id: UserId)
}