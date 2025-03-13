package com.example.common.fixtures.extension

import com.example.common.fixtures.UsersFixture

fun UsersFixture.default(): UsersFixture = this.copy(
    id = 1,
    name = "Alice",
    age = 20
)