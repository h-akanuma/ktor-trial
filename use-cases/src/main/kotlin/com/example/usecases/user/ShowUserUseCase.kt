package com.example.usecases.user

import com.example.domain.user.model.User
import com.example.domain.user.repository.IUserRepository
import com.example.shared.repository.ITransaction
import com.example.shared.valueobject.UserId

interface ShowUserUseCase {
    suspend fun show(userId: UserId): User
}

class ShowUserUseCaseImpl(
    private val transaction: ITransaction,
    private val userRepository: IUserRepository
) : ShowUserUseCase {
    override suspend fun show(userId: UserId): User {
        return transaction.execute {
            userRepository.getBy(userId)
        }
    }
}