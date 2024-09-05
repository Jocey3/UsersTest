package com.users.test

import com.users.test.domain.model.UserDomainModel
import com.users.test.domain.repository.RepositoryUsers
import com.users.test.domain.use_case.AddUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class AddUserUseCaseTest {

    private lateinit var addUserUseCase: AddUserUseCase
    private val repositoryUsers: RepositoryUsers = mock()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        addUserUseCase = AddUserUseCase(repositoryUsers)
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `invoke should call addUser with correct parameters`() = testScope.runTest {
        // Arrange
        val user = UserDomainModel(id = 1, name = "John", description = "Some text")

        // Act
        addUserUseCase(user)

        // Assert
        verify(repositoryUsers).addUser(user)
    }
}