package com.example.user.controller

import com.example.domain.user.model.User
import com.example.shared.valueobject.UserId
import com.example.usecases.user.CreateUserUseCase
import com.example.usecases.user.DeleteUserUseCase
import com.example.usecases.user.ShowUserUseCase
import com.example.usecases.user.UpdateUserUseCase
import com.example.user.form.CreateUserForm
import com.example.user.form.UpdateUserForm
import com.github.michaelbull.result.mapBoth

class UserController(
    private val showUserUseCase: ShowUserUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) {
    suspend fun showUser(userId: UserId): User {
        return showUserUseCase.show(userId)
            .mapBoth(
                success = { it },
                failure = { throw it }
            )
    }

    suspend fun createUser(form: CreateUserForm): User {
        return createUserUseCase.create(form.validate())
            .mapBoth(
                success = { it },
                failure = { throw it }
            )
    }

    suspend fun updateUser(userId: UserId, form: UpdateUserForm) {
        return updateUserUseCase.update(userId, form.validate())
            .mapBoth(
                success = { it },
                failure = { throw it }
            )
    }

    suspend fun deleteUser(userId: UserId) {
        return deleteUserUseCase.delete(userId)
            .mapBoth(
                success = { it },
                failure = { throw it }
            )
    }
}