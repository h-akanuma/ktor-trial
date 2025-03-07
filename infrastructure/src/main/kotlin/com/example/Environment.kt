package com.example

import com.example.Environment.values


enum class Environment {
    LOCAL, TEST, STAGING, PRODUCTION;

    companion object {
        fun getBy(value: String) = values().firstOrNull { it.name == value.uppercase() }
            ?: throw IllegalArgumentException()
    }
}