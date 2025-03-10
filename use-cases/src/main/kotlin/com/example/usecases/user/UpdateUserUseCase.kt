package com.example.usecases.user

import com.example.domain.user.repository.IUserRepository
import com.example.shared.repository.ITransaction
import com.example.shared.valueobject.UserId
import com.github.michaelbull.result.*

interface UpdateUserUseCase {
    data class Param (
        val name: String,
        val age: Int
    )

    suspend fun update(id: UserId, param: Param): Result<Unit, Error>
}

class UpdateUserUseCaseImpl(
    private val transaction: ITransaction,
    private val userRepository: IUserRepository
) : UpdateUserUseCase {
    override suspend fun update(userId: UserId, param: UpdateUserUseCase.Param): Result<Unit, Error> {
        val unit = transaction.execute {
            val user = userRepository.getBy(userId)
            userRepository.update(userId, user.copy(name = param.name, age = param.age))
            Ok(Unit)
        }.onFailure {
            return Err(it)
        }.unwrap()

        return Ok(unit)
    }
}