package com.example.usecases.user

import com.example.domain.user.repository.IUserRepository
import com.example.shared.repository.ITransaction
import com.example.shared.valueobject.UserId

interface DeleteUserUseCase {
    suspend fun delete(userId: UserId): Unit
}

class DeleteUserUseCaseImpl(
    private val transaction: ITransaction,
    private val userRepository: IUserRepository
) : DeleteUserUseCase {
    override suspend fun delete(userId: UserId) {
        transaction.execute {
            userRepository.delete(userId)
        }
    }
}