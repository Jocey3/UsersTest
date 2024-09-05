package com.users.test

import androidx.paging.PagingData
import com.users.test.domain.model.UserDomainModel
import com.users.test.domain.repository.RepositoryUsers
import com.users.test.domain.use_case.GetUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class GetUsersUseCaseTest {

    private lateinit var getUsersUseCase: GetUsersUseCase
    private val repositoryUsers: RepositoryUsers = mock()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        getUsersUseCase = GetUsersUseCase(repositoryUsers)
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `invoke should return PagingData of users`() = testScope.runTest {
        // Arrange
        val pagingData = PagingData.from(
            listOf(
                UserDomainModel(
                    id = 1,
                    name = "John",
                    description = "Some text"
                )
            )
        )
        whenever(repositoryUsers.getUsers()).thenReturn(flow { emit(pagingData) })

        // Act
        val result = getUsersUseCase().toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(pagingData, result[0])
    }
}