package com.example.usecases.user

import com.example.domain.user.model.User
import com.example.domain.user.repository.IUserRepository
import com.example.shared.repository.ITransaction
import com.github.michaelbull.result.*

interface CreateUserUseCase {
    data class Param (
        val name: String,
        val age: Int
    )

    suspend fun create(param: Param): Result<User, Error>
}

class CreateUserUseCaseImpl(
    private val transaction: ITransaction,
    private val userRepository: IUserRepository
) : CreateUserUseCase {
    override suspend fun create(param: CreateUserUseCase.Param): Result<User, Error> {
        val user = transaction.execute {
            val user = userRepository.create(param.name, param.age)
            Ok(user)
        }.onFailure {
            return Err(it)
        }.unwrap()

        return Ok(user)
    }
}