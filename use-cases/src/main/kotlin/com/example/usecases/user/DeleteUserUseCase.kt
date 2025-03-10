package com.example.usecases.user

import com.example.domain.user.repository.IUserRepository
import com.example.shared.repository.ITransaction
import com.example.shared.valueobject.UserId
import com.github.michaelbull.result.*

interface DeleteUserUseCase {
    suspend fun delete(userId: UserId): Result<Unit, Error>
}

class DeleteUserUseCaseImpl(
    private val transaction: ITransaction,
    private val userRepository: IUserRepository
) : DeleteUserUseCase {
    override suspend fun delete(userId: UserId): Result<Unit, Error> {
        val unit = transaction.execute {
            userRepository.delete(userId)
            Ok(Unit)
        }.onFailure {
            return Err(it)
        }.unwrap()

        return Ok(unit)
    }
}