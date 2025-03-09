package com.example.shared.repository

interface ITransaction {
    suspend fun <T> execute(block: () -> T): T
    suspend fun <T> suspendExecute(block: suspend () -> T): T
}