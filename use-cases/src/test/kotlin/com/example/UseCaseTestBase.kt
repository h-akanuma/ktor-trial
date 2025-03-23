package com.example

import com.example.shared.repository.ITransaction

abstract class UseCaseTestBase {
    val transaction = object : ITransaction {
        override suspend fun <T> execute(block: () -> T): T = block()
        override suspend fun <T> suspendExecute(block: suspend () -> T): T = block()
    }
}