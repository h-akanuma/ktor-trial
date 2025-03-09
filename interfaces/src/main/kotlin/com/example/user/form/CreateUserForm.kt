package com.example.user.form

import com.example.usecases.user.CreateUserUseCase
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserForm(val name: String, val age: Int) {
    fun validate(): CreateUserUseCase.Param {
        if (name.isEmpty()) {
            throw IllegalArgumentException("name is empty")
        }
        if (age < 0) {
            throw IllegalArgumentException("age is negative")
        }

        return CreateUserUseCase.Param(
            name = name,
            age = age
        )
    }
}