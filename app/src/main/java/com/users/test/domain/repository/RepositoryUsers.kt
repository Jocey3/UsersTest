package com.users.test.domain.repository

import androidx.paging.PagingData
import com.users.test.domain.model.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface RepositoryUsers {
    suspend fun addUser(user: UserDomainModel)
    fun getUsers(): Flow<PagingData<UserDomainModel>>
    suspend fun getUserById(id: Int): UserDomainModel
    suspend fun updateUser(user: UserDomainModel)
    suspend fun deleteUser(user: UserDomainModel)
}