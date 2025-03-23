package com.example.usecases.user

import com.example.UseCaseTestBase
import com.example.domain.user.repository.IUserRepository
import com.example.shared.valueobject.UserId
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.Test
import com.example.objectmother.UserMother
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking

class ShowUserUseCaseImplTest : UseCaseTestBase() {
    private val userRepository = mockkClass(IUserRepository::class)
    private val target = ShowUserUseCaseImpl(
        transaction = transaction,
        userRepository = userRepository
    )

    @Test
    fun show() {
        val userId = UserId(1)
        every { userRepository.getBy(id = userId) } returns UserMother.default()

        runBlocking {
            target.show(userId = userId)
        }

        coVerify { userRepository.getBy(id = userId) }
    }
}