package com.example.usecases.user

import com.example.domain.user.model.User
import com.example.domain.user.repository.IUserRepository
import com.example.shared.repository.ITransaction

interface CreateUserUseCase {
    data class Param (
        val name: String,
        val age: Int
    )

    suspend fun create(param: Param): User
}

class CreateUserUseCaseImpl(
    private val transaction: ITransaction,
    private val userRepository: IUserRepository
) : CreateUserUseCase {
    override suspend fun create(param: CreateUserUseCase.Param): User {
        return transaction.execute {
            userRepository.create(param.name, param.age)
        }
    }
}