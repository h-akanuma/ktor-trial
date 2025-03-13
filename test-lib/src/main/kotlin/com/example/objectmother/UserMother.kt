package com.example.objectmother

import com.example.domain.user.model.User
import com.example.shared.valueobject.UserId

object UserMother {
    fun default(): User = User(
        id = UserId(value = 1),
        name = "Alice",
        age = 20
    )
}