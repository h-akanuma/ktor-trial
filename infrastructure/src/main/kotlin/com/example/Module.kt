package com.example

import com.example.controller.HelloController
import com.example.domain.user.repository.IUserRepository
import com.example.shared.repository.ITransaction
import com.example.shared.repository.Transaction
import com.example.usecases.user.*
import com.example.user.controller.UserController
import com.example.user.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object Module {
    fun modules() = module {
        singleOf(::HelloController)
        single { UserController(get(), get(), get(), get()) }

        single<ITransaction> { Transaction() }
        single<IUserRepository> { UserRepository() }
        single<ShowUserUseCase> { ShowUserUseCaseImpl(get(), get()) }
        single<CreateUserUseCase> { CreateUserUseCaseImpl(get(), get()) }
        single<UpdateUserUseCase> { UpdateUserUseCaseImpl(get(), get()) }
        single<DeleteUserUseCase> { DeleteUserUseCaseImpl(get(), get()) }
    }
}