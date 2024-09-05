package com.users.test

import com.users.test.domain.model.UserDomainModel
import com.users.test.domain.repository.RepositoryUsers
import com.users.test.domain.use_case.GetUserByIdUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
class GetUserByIdUseCaseTest {

    private lateinit var getUserByIdUseCase: GetUserByIdUseCase
    private val repositoryUsers: RepositoryUsers = mock()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        getUserByIdUseCase = GetUserByIdUseCase(repositoryUsers)
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `invoke should call getUserById with correct userId and return user`() = testScope.runTest {
        // Arrange
        val userId = 1
        val expectedUser = UserDomainModel(id = userId, name = "John", "Some text")
        whenever(repositoryUsers.getUserById(userId)).thenReturn(expectedUser)

        // Act
        val result = getUserByIdUseCase(userId)

        // Assert
        assertEquals(expectedUser, result)
        verify(repositoryUsers).getUserById(userId)
    }
}