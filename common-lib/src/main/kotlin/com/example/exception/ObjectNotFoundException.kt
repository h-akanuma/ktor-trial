package com.example.exception

private const val DEFAULT_MESSAGE = "NO OBJECT FOUND"

class ObjectNotFoundException(message: String = DEFAULT_MESSAGE) : RuntimeException(message)