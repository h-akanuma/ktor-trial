package com.example.shared.valueobject

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class UserId(val value: Long)