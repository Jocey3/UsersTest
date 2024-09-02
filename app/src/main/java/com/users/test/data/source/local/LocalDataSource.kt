package com.users.test.data.source.local

import com.users.test.data.model.UserDataModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAll(): Flow<List<UserDataModel>>
    suspend fun insert(user: UserDataModel)
    suspend fun insertAll(users: List<UserDataModel>)
    suspend fun update(user: UserDataModel)
    suspend fun delete(user: UserDataModel)
}