package com.example.shared.repository

import com.example.shared.database.DatabaseConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Transaction : ITransaction, Closeable {
    private val executorService: ExecutorService = Executors.newFixedThreadPool(DatabaseConfig.maximumPoolSize)
    private val dispatcher: CoroutineDispatcher = executorService.asCoroutineDispatcher()

    override fun close() {
        executorService.shutdown()
    }

    override suspend fun <T> execute(block: () -> T): T =
        withContext(dispatcher) {
            transaction {
                block()
            }
        }

    override suspend fun <T> suspendExecute(block: suspend () -> T): T =
        withContext(dispatcher) {
            newSuspendedTransaction {
                block()
            }
        }

}