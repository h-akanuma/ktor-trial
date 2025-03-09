package com.example.user.controller

import com.example.domain.user.model.User
import com.example.shared.valueobject.UserId
import com.example.usecases.user.CreateUserUseCase
import com.example.usecases.user.DeleteUserUseCase
import com.example.usecases.user.ShowUserUseCase
import com.example.usecases.user.UpdateUserUseCase
import com.example.user.form.CreateUserForm
import com.example.user.form.UpdateUserForm

class UserController(
    private val showUserUseCase: ShowUserUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) {
    suspend fun showUser(userId: UserId): User {
        return showUserUseCase.show(userId)
    }

    suspend fun createUser(form: CreateUserForm): User {
        return createUserUseCase.create(form.validate())
    }

    suspend fun updateUser(userId: UserId, form: UpdateUserForm) {
        updateUserUseCase.update(userId, form.validate())
    }

    suspend fun deleteUser(userId: UserId) {
        deleteUserUseCase.delete(userId)
    }
}