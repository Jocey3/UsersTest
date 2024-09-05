package com.users.test

import com.users.test.domain.model.UserDomainModel
import com.users.test.domain.repository.RepositoryUsers
import com.users.test.domain.use_case.UpdateUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify


@ExperimentalCoroutinesApi
class UpdateUserUseCaseTest {

    private lateinit var updateUserUseCase: UpdateUserUseCase
    private val repositoryUsers: RepositoryUsers = mock()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        updateUserUseCase = UpdateUserUseCase(repositoryUsers)
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `invoke should call deleteUser if user name and description are empty`() =
        testScope.runTest {
            // Arrange
            val user = UserDomainModel(id = 1, name = "", description = "")

            // Act
            updateUserUseCase(user)

            // Assert
            verify(repositoryUsers).deleteUser(user)
            verify(repositoryUsers, never()).updateUser(user)
        }

    @Test
    fun `invoke should call updateUser if user name or description are not empty`() =
        testScope.runTest {
            // Arrange
            val user = UserDomainModel(id = 1, name = "John", description = "")

            // Act
            updateUserUseCase(user)

            // Assert
            verify(repositoryUsers).updateUser(user)
            verify(repositoryUsers, never()).deleteUser(user)
        }
}