package com.example.usecases.user

import com.example.domain.user.repository.IUserRepository
import com.example.shared.repository.ITransaction
import com.example.shared.valueobject.UserId

interface UpdateUserUseCase {
    data class Param (
        val name: String,
        val age: Int
    )

    suspend fun update(id: UserId, param: Param)
}

class UpdateUserUseCaseImpl(
    private val transaction: ITransaction,
    private val userRepository: IUserRepository
) : UpdateUserUseCase {
    override suspend fun update(userId: UserId, param: UpdateUserUseCase.Param) {
        return transaction.execute {
            val user = userRepository.getBy(userId)
            userRepository.update(userId, user.copy(name = param.name, age = param.age))
        }
    }
}