package com.example.usecases.user

import com.example.domain.user.model.User
import com.example.domain.user.repository.IUserRepository
import com.example.shared.repository.ITransaction
import com.example.shared.valueobject.UserId
import com.github.michaelbull.result.*

interface ShowUserUseCase {
    suspend fun show(userId: UserId): Result<User, Error>
}

class ShowUserUseCaseImpl(
    private val transaction: ITransaction,
    private val userRepository: IUserRepository
) : ShowUserUseCase {
    override suspend fun show(userId: UserId): Result<User, Error> {
        val user = transaction.execute {
            val user = userRepository.getBy(userId)
            Ok(user)
        }.onFailure {
            return Err(it)
        }.unwrap()

        return Ok(user)
    }
}