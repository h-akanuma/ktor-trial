package com.example.domain.user.model

import com.example.shared.valueobject.UserId
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: UserId,
    val name: String,
    val age: Int
) {
}