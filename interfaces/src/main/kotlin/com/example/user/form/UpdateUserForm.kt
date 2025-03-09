package com.example.user.form

import com.example.usecases.user.UpdateUserUseCase
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserForm(
    val name: String,
    val age: Int
) {
    fun validate(): UpdateUserUseCase.Param {
        if (name.isEmpty()) {
            throw IllegalArgumentException("name is empty")
        }
        if (age < 0) {
            throw IllegalArgumentException("age is negative")
        }

        return UpdateUserUseCase.Param(
            name = name,
            age = age
        )
    }
}